/* 
 * ArimAPI, a minecraft plugin library and framework.
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.concurrent;

import java.util.concurrent.TimeUnit;

import space.arim.universal.util.concurrent.EnhancedExecutor;
import space.arim.universal.util.concurrent.Scheduler;

import space.arim.api.annotation.Blocking;

/**
 * An optionally implemented interface for {@link EnhancedExecutor}s and/or {@link Scheduler}s
 * to shutdown any thread pools or resources used in implementations.
 * 
 * @author A248
 *
 */
public interface Shutdownable {

	/**
	 * Shuts down the executor in an orderly fashion.
	 * 
	 */
	void shutdown();
	
	/**
	 * Shuts down the executor in an orderly fashion AND
	 * blocks until all threads have completed execution.
	 * 
	 * @param timeout the timeout for waiting
	 * @param units the time units of the timeout
	 */
	void shutdown(long timeout, TimeUnit units);
	
}
