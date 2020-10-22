/* 
 * ArimAPI-env-core
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-env-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-env-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-env-core. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.env.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import space.arim.omnibus.util.concurrent.CentralisedFuture;
import space.arim.omnibus.util.concurrent.impl.BaseCentralisedFuture;

class DeadlockFreeFuture<T> extends BaseCentralisedFuture<T> {

	private final DeadlockFreeFutureFactory factory;
	
	private static final Object ABSENT_VALUE = new Object();
	
	private DeadlockFreeFuture(DeadlockFreeFutureFactory factory) {
		super(factory);
		this.factory = factory;
	}
	
	@Override
	public <U> CentralisedFuture<U> newIncompleteFuture() {
		return new DeadlockFreeFuture<>(factory);
	}
	
	/*
	 * 
	 * Managed wait implementation
	 * 
	 */
	
	// Used to avoid double volatile reads
	
	/**
	 * Gets the completed value, or {@code ABSENT_VALUE} if not completed. <br>
	 * <br>
	 * If completed exceptionally, throws in accordance with {@link CompletableFuture#join()}
	 * 
	 * @return the completed value or {@code ABSENT_VALUE}
	 * @throws CancellationException if the computation was cancelled
	 * @throws CompletionException if this future completed exceptionally or a completion computation threw an exception
	 */
	@SuppressWarnings("unchecked")
	private T getNowJoin() {
		return getNow((T) ABSENT_VALUE);
	}
	
	/**
	 * Gets the completed value, or {@code ABSENT_VALUE} if not completed. <br>
	 * <br>
	 * If completed exceptionally, throws in accordance with {@link CompletableFuture#get()}
	 * 
	 * @return the completed value or {@code ABSENT_VALUE}
	 * @throws CancellationException if the computation was cancelled
	 * @throws ExecutionException if this future completed exceptionally
	 */
	private T getNowGet() throws ExecutionException {
		try {
			return getNowJoin();
		} catch (CompletionException ex) {
			Throwable cause = ex.getCause();
			throw new ExecutionException((cause == null) ? ex : cause);
		}
	}
	
	// Managed waits
	
	@Override
	public T join() {
		if (!factory.isPrimaryThread()) {
			return super.join();
		}

		T result;
		if ((result = getNowJoin()) != ABSENT_VALUE) {	// if (isDone()) {
			return result;								// return super.join(); }
		}
		for (;;) {
			factory.unleashSyncTasks();
			factory.completionLock.lock();
			try {
				if ((result = getNowJoin()) != ABSENT_VALUE) {	// if (isDone()) {
					return result;								// return super.join(); }
				}
				factory.completionCondition.awaitUninterruptibly();
			} finally {
				factory.completionLock.unlock();
			}
			if ((result = getNowJoin()) != ABSENT_VALUE) {	// if (isDone()) {
				return result;								// return super.join(); }
			}
		}
	}
	
	@Override
	public T get() throws InterruptedException, ExecutionException {
		if (!factory.isPrimaryThread()) {
			return super.get();
		}

		T result;
		if ((result = getNowGet()) != ABSENT_VALUE) {	// if (isDone()) {
			return result;								// return super.get(); }
		}
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
		for (;;) {
			factory.unleashSyncTasks();
			factory.completionLock.lock();
			try {
				if ((result = getNowGet()) != ABSENT_VALUE) {	// if (isDone()) {
					return result;								// return super.get(); }
				}
				factory.completionCondition.await();
			} finally {
				factory.completionLock.unlock();
			}
			if ((result = getNowGet()) != ABSENT_VALUE) {	// if (isDone()) {
				return result;								// return super.get(); }
			}
		}
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (!factory.isPrimaryThread()) {
			return super.get(timeout, unit);
		}

		T result;
		if ((result = getNowGet()) != ABSENT_VALUE) {	// if (isDone()) {
			return result;								// return super.get(); }
		}
		if (timeout <= 0L) {
			throw new TimeoutException();
		}
		long deadline = System.nanoTime() + unit.toNanos(timeout);
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
		for (;;) {
			factory.unleashSyncTasks(deadline);
			factory.completionLock.lock();
			try {
				if ((result = getNowGet()) != ABSENT_VALUE) {	// if (isDone()) {
					return result;								// return super.get(); }
				}
				factory.completionCondition.awaitNanos(deadline - System.nanoTime());
			} finally {
				factory.completionLock.unlock();
			}
			if (System.nanoTime() - deadline >= 0) {
				throw new TimeoutException();
			}
			if ((result = getNowGet()) != ABSENT_VALUE) {	// if (isDone()) {
				return result;								// return super.get(); }
			}
		}
	}

	/*
	 * Completion signalling
	 */

	private void whenCompleteSignal() {
		super.whenComplete((ignore1, ignore2) -> {
			factory.signal();
		});
	}

	private static <U> CentralisedFuture<U> applySignal(CentralisedFuture<U> future) {
		((DeadlockFreeFuture<U>) future).whenCompleteSignal();
		return future;
	}

	// Instantiation

	static <U> CentralisedFuture<U> create(DeadlockFreeFutureFactory factory) {
		return applySignal(new DeadlockFreeFuture<U>(factory));
	}

	// CompletableFuture/CentralisedFuture methods

	@Override
	public CentralisedFuture<T> copy() {
		return applySignal(super.copy());
	}

	// CompletionStage/ReactionStage methods

	@Override
	public <U> CentralisedFuture<U> thenApply(Function<? super T, ? extends U> fn) {
		return applySignal(super.thenApply(fn));
	}

	@Override
	public <U> CentralisedFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
		return applySignal(super.thenApplyAsync(fn));
	}

	@Override
	public <U> CentralisedFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
		return applySignal(super.thenApplyAsync(fn, executor));
	}

	@Override
	public CentralisedFuture<Void> thenAccept(Consumer<? super T> action) {
		return applySignal(super.thenAccept(action));
	}

	@Override
	public CentralisedFuture<Void> thenAcceptAsync(Consumer<? super T> action) {
		return applySignal(super.thenAcceptAsync(action));
	}

	@Override
	public CentralisedFuture<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
		return applySignal(super.thenAcceptAsync(action, executor));
	}

	@Override
	public CentralisedFuture<Void> thenRun(Runnable action) {
		return applySignal(super.thenRun(action));
	}

	@Override
	public CentralisedFuture<Void> thenRunAsync(Runnable action) {
		return applySignal(super.thenRunAsync(action));
	}

	@Override
	public CentralisedFuture<Void> thenRunAsync(Runnable action, Executor executor) {
		return applySignal(super.thenRunAsync(action, executor));
	}

	@Override
	public <U, V> CentralisedFuture<V> thenCombine(CompletionStage<? extends U> other,
			BiFunction<? super T, ? super U, ? extends V> fn) {
		return applySignal(super.thenCombine(other, fn));
	}

	@Override
	public <U, V> CentralisedFuture<V> thenCombineAsync(CompletionStage<? extends U> other,
			BiFunction<? super T, ? super U, ? extends V> fn) {
		return applySignal(super.thenCombineAsync(other, fn));
	}

	@Override
	public <U, V> CentralisedFuture<V> thenCombineAsync(CompletionStage<? extends U> other,
			BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
		return applySignal(super.thenCombineAsync(other, fn, executor));
	}

	@Override
	public <U> CentralisedFuture<Void> thenAcceptBoth(CompletionStage<? extends U> other,
			BiConsumer<? super T, ? super U> action) {
		return applySignal(super.thenAcceptBoth(other, action));
	}

	@Override
	public <U> CentralisedFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,
			BiConsumer<? super T, ? super U> action) {
		return applySignal(super.thenAcceptBothAsync(other, action));
	}

	@Override
	public <U> CentralisedFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,
			BiConsumer<? super T, ? super U> action, Executor executor) {
		return applySignal(super.thenAcceptBothAsync(other, action, executor));
	}

	@Override
	public CentralisedFuture<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
		return applySignal(super.runAfterBoth(other, action));
	}

	@Override
	public CentralisedFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
		return applySignal(super.runAfterBothAsync(other, action));
	}

	@Override
	public CentralisedFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
		return applySignal(super.runAfterBothAsync(other, action, executor));
	}

	@Override
	public <U> CentralisedFuture<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
		return applySignal(super.applyToEither(other, fn));
	}

	@Override
	public <U> CentralisedFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
		return applySignal(super.applyToEitherAsync(other, fn));
	}

	@Override
	public <U> CentralisedFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn,
			Executor executor) {
		return applySignal(super.applyToEitherAsync(other, fn, executor));
	}

	@Override
	public CentralisedFuture<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
		return applySignal(super.acceptEither(other, action));
	}

	@Override
	public CentralisedFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
		return applySignal(super.acceptEitherAsync(other, action));
	}

	@Override
	public CentralisedFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action,
			Executor executor) {
		return applySignal(super.acceptEitherAsync(other, action, executor));
	}

	@Override
	public CentralisedFuture<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
		return applySignal(super.runAfterEither(other, action));
	}

	@Override
	public CentralisedFuture<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
		return applySignal(super.runAfterEitherAsync(other, action));
	}

	@Override
	public CentralisedFuture<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
		return applySignal(super.runAfterEitherAsync(other, action, executor));
	}

	@Override
	public <U> CentralisedFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
		return applySignal(super.thenCompose(fn));
	}

	@Override
	public <U> CentralisedFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
		return applySignal(super.thenComposeAsync(fn));
	}

	@Override
	public <U> CentralisedFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn,
			Executor executor) {
		return applySignal(super.thenComposeAsync(fn, executor));
	}

	@Override
	public <U> CentralisedFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
		return applySignal(super.handle(fn));
	}

	@Override
	public <U> CentralisedFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
		return applySignal(super.handleAsync(fn));
	}

	@Override
	public <U> CentralisedFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
		return applySignal(super.handleAsync(fn, executor));
	}

	@Override
	public CentralisedFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
		return applySignal(super.whenComplete(action));
	}

	@Override
	public CentralisedFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
		return applySignal(super.whenCompleteAsync(action));
	}

	@Override
	public CentralisedFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
		return applySignal(super.whenCompleteAsync(action, executor));
	}

	@Override
	public CentralisedFuture<T> exceptionally(Function<Throwable, ? extends T> fn) {
		return applySignal(super.exceptionally(fn));
	}

}