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
package space.arim.api.platform.spigot;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * A simple helper class for cancellable Spigot events. <br>
 * <br>
 * <b>Advantage</b>: No need to ever create a <code>cancelled</code> boolean field again! <br>
 * <b>Usage</b>: Let your Spigot events extend this class.
 * 
 * @author A248
 *
 */
public abstract class CancellableEvent extends Event implements Cancellable {
	
	private boolean cancelled = false;
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
