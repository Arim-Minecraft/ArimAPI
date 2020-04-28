/* 
 * ArimAPI-platform
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-platform is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-platform. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.platform.bungee;

import space.arim.api.util.LazySingleton;

/**
 * BungeeCord platform specific utilities. Use {@link #get()} to get the instance.
 * 
 * @author A248
 *
 */
public class BungeePlatform {

	private static final LazySingleton<BungeePlatform> INST = new LazySingleton<BungeePlatform>(BungeePlatform::new);
	
	protected BungeePlatform() {}
	
	/**
	 * Gets the main instance
	 * 
	 * @return the instance
	 */
	public static BungeePlatform get() {
		return INST.get();
	}
	
	/**
	 * Gets the messages utility
	 * 
	 * @return the messages utility instance
	 */
	public BungeeMessages messages() {
		return BungeeMessages.get();
	}
	
	/**
	 * Gets the commands utility
	 * 
	 * @return the commans utility instance
	 */
	public BungeeCommands commands() {
		return BungeeCommands.get();
	}
	
}
