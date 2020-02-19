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
package space.arim.api.chat;

import java.util.function.UnaryOperator;

import space.arim.universal.util.collections.ArraysUtil;

/**
 * A sendable message comprised of an array of {@link Component} or {@link JsonComponent} objects
 * 
 * @author A248
 *
 */
public class Message {

	private final Component[] components;
	
	public Message(Component...components) {
		this.components = components;
	}
	
	private Message convertEach(UnaryOperator<Component> converter) {
		Component[] converted = new Component[components.length - 1];
		for (int n = 0; n < components.length; n++) {
			converted[n] = converter.apply(components[n]);
		}
		return new Message(converted);
	}
	
	/**
	 * Gets all the components which comprise this message. <br>
	 * Some or all the components may be json components if the message is a JSON message.
	 * 
	 * @return the array of components
	 */
	public Component[] getComponents() {
		return components;
	}
	
	/**
	 * Creates a new Message with all colour formatting removed
	 * 
	 * @return a fresh Message with colour removed
	 */
	public Message stripColour() {
		return convertEach((component) -> component.stripColour());
	}
	
	/**
	 * Creates a new Message with all styles formatting removed
	 * 
	 * @return a fresh Message with styles removed
	 */
	public Message stripStyles() {
		return convertEach((component) -> component.stripStyles());
	}
	
	/**
	 * Creates a new Message with all JSON formatting removed
	 * 
	 * @return a fresh Message with JSON removed
	 */
	public Message stripJson() {
		return convertEach((component) -> (component instanceof JsonComponent) ? ((JsonComponent) component).stripJson() : component);
	}
	
	/**
	 * Creates a new Message with all colour, styles, and JSON formatting removed. <br>
	 * 
	 * @return a fresh Message with colour, styles, and JSON removed
	 */
	public Message stripAll() {
		return convertEach((component) -> component.stripAll());
	}
	
	/**
	 * Identical to {@link MessageBuilder}
	 * 
	 * @author A248
	 *
	 */
	public class Builder extends MessageBuilder {
		
	}
	
	/**
	 * Identical to {@link MessageJsonBuilder}
	 * 
	 * @author A248
	 *
	 */
	public class JsonBuilder extends MessageJsonBuilder {
		
	}
	
	@Override
	public String toString() {
		return ArraysUtil.toString(components);
	}
	
}