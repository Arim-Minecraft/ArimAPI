/* 
 * ArimAPI-plugin
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-plugin. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.server.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.plugin.Plugin;

import space.arim.universal.registry.Registrable;

/**
 * A helper class for resources registered on Bukkit. <br>
 * Subclassing BukkitRegistrable provides useful default implementations of {@link Registrable#getName()}, {@link Registrable#getAuthor()}, and {@link Registrable#getVersion()}
 * based on the corresponding {@link JavaPlugin}. See {@link #getName()}, {@link #getAuthor()}, and {@link #getVersion()} for details. <br>
 * <br>
 * <b>Usage:</b> <br>
 * 1. The service type must be an interface to use BukkitRegistrable. <br>
 * 2. Let your registered implementation <code>extend</code> BukkitRegistrable. <br>
 * 4. Provide super constructor {@link #BukkitRegistrable(JavaPlugin)} with your plugin main. <br>
 * <br>
 * <b>Example:</b> <br>
 * You want your resource called 'SuperChat' to be registered as a ChatProvider (Note that ChatProvider must be an interface which extends Registrable). <br>
 * Your SuperChat class should look this:
 * <pre><code>
 * public class SuperChat extends BukkitRegistrable implements ChatProvider {
 *   public SuperChat(JavaPlugin plugin) {
 *     super(plugin);
 *   }
 *   public byte getPriority() {
 *     return RegistryPriority.NORMAL;
 *   }
 * }
 * </code></pre>
 * Your main plugin class should look like this:
 * <pre><code>
 * public class SuperChatPlugin extends JavaPlugin {
 *   {@code @Override}
 *   public void onEnable() {
 *     // Creating your SuperChat chat provider
 *     SuperChat chat = new SuperChat(this);
 *     // Registering your chat provider
 *     UniversalRegistry.get().register(ChatProvider.class, chat);
 *   }
 * }
 * </code></pre>
 * Good, now you don't have to worry about overriding #getName, #getAuthor, and #getVersion in your SuperChat resource.
 * BukkitRegistrable automatically takes care of all of that.
 */
public abstract class BukkitRegistrable implements Registrable {
	
	private final JavaPlugin plugin;
	
	/**
	 * Initialises the abstract class from a provided {@link Plugin}. 
	 * The plugin should be your own plugin main, of course!.
	 * 
	 * @param plugin the plugin main
	 */
	public BukkitRegistrable(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Returns the value of <code>plugin.getDescription().getName()</code> where <i>plugin</i> was supplied to the constructor.
	 * 
	 * @return the plugin name
	 */
	@Override
	public String getName() {
		return plugin.getDescription().getName();
	}
	
	/**
	 * Returns the plugin's author as defined in plugin.yml / bungee.yml
	 * 
	 * @return the author's name
	 */
	@Override
	public String getAuthor() {
		return plugin.getDescription().getAuthors().get(0);
	}
	
	/**
	 * Returns the plugin's version as defined in plugin.yml / bungee.yml
	 * 
	 * @return the version
	 */
	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}
	
	/**
	 * Gets the plugin main used to construct this BukkitRegistrable for convenient use.
	 * 
	 * @return the plugin
	 */
	protected final JavaPlugin getPlugin() {
		return plugin;
	}
	
}