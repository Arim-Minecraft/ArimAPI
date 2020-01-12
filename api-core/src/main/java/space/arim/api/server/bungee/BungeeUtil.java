/* 
 * ArimAPI, a minecraft plugin library and framework.
 * Copyright © 2019 Anand Beh <https://www.arim.space>
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
package space.arim.api.server.bungee;

import java.util.HashSet;
import java.util.Set;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;

import space.arim.api.annotation.Platform;
import space.arim.api.server.ChatUtil;

/**
 * Basic BungeeCord utility class with more methods added via contribution.
 * 
 * @author A248
 *
 */
@Platform(Platform.Type.BUNGEE)
public final class BungeeUtil {

	private BungeeUtil() {}
	
	/**
	 * Iterates across online players, finds players matching the first argument in the args parameter, and returns applicable player names.
	 * 
	 * @param args command arguments to tab complete
	 * @param proxy the proxy server, use {@link net.md_5.bungee.api.plugin.Plugin#getProxy() plugin.getProxy()} for this parameter
	 * @return tab completable set
	 */
	public static Set<String> getPlayerNameTabComplete(String[] args, ProxyServer proxy) {
		Set<String> playerNames = new HashSet<String>();
		if (args.length == 0) {
			proxy.getPlayers().forEach((player) -> {
				playerNames.add(player.getName());
			});
		} else if (args.length == 1) {
			proxy.getPlayers().forEach((player) -> {
				if (player.getName().toLowerCase().startsWith(args[0])) {
					playerNames.add(player.getName());
				}
			});
		}
		return playerNames;
	}
	
	/**
	 * Adds color to a message according to '&' color codes. <br>
	 * See {@link ChatUtil#colorBungee(String)} for more information.
	 * 
	 * @param colorable the input string
	 * @return a colored BaseComponent array
	 */
	public static BaseComponent[] color(String colorable) {
		return ChatUtil.colorBungee(colorable);
	}
	
	/**
	 * Removes color from a message according to '&' color codes. <br>
	 * See {@link ChatUtil#stripColor(String)} for more information.
	 * 
	 * @param colorable the input string
	 * @return an uncoloured string
	 */
	public static String stripColor(String colorable) {
		return ChatUtil.stripColor(colorable);
	}
	
	/**
	 * Parses Json messages based on RezzedUp's json.sk format. <br>
	 * <br>
	 * <b>Example usage:</b> <br>
	 * <pre>
	 * <code>
	 * public void sendJsonMessage(ProxiedPlayer player, String json) {
	 *   // Woohoo! Now we can send json messages!
	 *   player.sendMessage(BungeeUtil.parseJson(json));
	 * }
	 * </code>
	 * </pre>
	 * See {@link ChatUtil#parseJson(String)} for more information.
	 * 
	 * @param jsonable the input string
	 * @return a BaseComponent array
	 */
	public static BaseComponent[] parseJson(String jsonable) {
		return ChatUtil.parseJson(jsonable);
	}
	
	/**
	 * Removes json formatting from an input string. <br>
	 * Useful for sending messages to the console with formatting removed. <br>
	 * See {@link ChatUtil#stripJson(String)} for more information.
	 * 
	 * @param json the input string
	 * @return a string stripped of all json tags
	 */
	public static String stripJson(String json) {
		return ChatUtil.stripJson(json);
	}
	
}