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
package space.arim.api.env;

import space.arim.omnibus.resourcer.ResourceHook;
import space.arim.omnibus.resourcer.Resourcer;
import space.arim.omnibus.util.concurrent.EnhancedExecutor;
import space.arim.omnibus.util.concurrent.FactoryOfTheFuture;

import space.arim.api.chat.SendableMessage;
import space.arim.api.env.annote.PlatformCommandSender;
import space.arim.api.env.annote.PlatformPlayer;
import space.arim.api.env.chat.PlatformMessageAdapter;

/**
 * An interface for working with platform-specific details, especially those not easily
 * abstracted otherwise.
 * 
 * @author A248
 *
 */
public interface PlatformHandle {
	
	/**
	 * Gets a resource hook for a specific resource based on this platform, using the specified {@link Resourcer}. <br>
	 * This will use the value of {@link Resourcer#hookUsage(Class, java.util.function.Supplier)} <br>
	 * <br>
	 * The supported resource types: <br>
	 * * {@link FactoryOfTheFuture} <br>
	 * * {@link EnhancedExecutor} <br>
	 * <br>
	 * Specifying a resource class other than the ones listed will result in a {@code IllegalArgumentException}.
	 * 
	 * @param <T> the resource type
	 * @param resourcer the {@link Resourcer} which is to manage the resource
	 * @param resourceClass the resource class, must be one of those supported
	 * @return the result of {@link Resourcer#hookUsage(Class, java.util.function.Supplier)}
	 * @throws IllegalArgumentException if the resource type is not supported
	 */
	<T> ResourceHook<T> hookPlatformResource(Resourcer resourcer, Class<T> resourceClass);
	
	/**
	 * Sends a {@link SendableMessage} to a command sender based on this platform. <br>
	 * <br>
	 * <b>This method is not thread safe on all platforms.</b> <br>
	 * <br>
	 * Implementations may be more efficient than using the platform's {@link PlatformMessageAdapter} to convert
	 * to a platform specific type.
	 * 
	 * @param recipient the recipient
	 * @param message the message
	 */
	void sendMessage(@PlatformCommandSender Object recipient, SendableMessage message);
	
	/**
	 * Disconnects (kicks) a player based on this platform with the reason provided {@link SendableMessage}. <br>
	 * <br>
	 * <b>This method is not thread safe on all platforms.</b> <br>
	 * <br>
	 * Implementations may be more efficient than using the platform's {@link PlatformMessageAdapter} to convert
	 * to a platform specific type.
	 * 
	 * @param user the user to kick
	 * @param reason the kick message
	 */
	void disconnectUser(@PlatformPlayer Object user, SendableMessage reason);
	
	/**
	 * Gets the platform type detected
	 * 
	 * @return the platform type detected
	 */
	PlatformType getPlatformType();
	
	/**
	 * Gets the plugin info which is used for platform-specific API methods
	 * requiring platform-specific plugin information
	 * 
	 * @return the platform plugin info used
	 */
	PlatformPluginInfo getImplementingPluginInfo();
	
}
