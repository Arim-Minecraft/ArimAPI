/* 
 * ArimAPI-chat
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-chat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-chat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-chat. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.chat;

import java.util.List;

/**
 * An action which will show a tooltip when a player hovers over the chat message.
 * 
 * @author A248
 *
 */
public final class JsonHover extends JsonAction {

	private final List<ChatComponent> contents;
	
	private JsonHover(List<ChatComponent> contents) {
		this.contents = List.copyOf(contents);
	}
	
	/**
	 * Creates from a tooltip to show when a player hovers over the main message
	 * 
	 * @param contents the tooltip to display
	 * @return the hover action
	 * @throws NullPointerException if {@code contents} or an element in it is null
	 */
	public static JsonHover create(List<ChatComponent> contents) {
		return new JsonHover(contents);
	}
	
	/**
	 * Gets the contents which will be shown to players when they hover on the chat message.
	 * 
	 * @return the tooltip which will be shown, never {@code null}
	 */
	public List<ChatComponent> getContents() {
		return contents;
	}

	@Override
	public String toString() {
		return "JsonHover [contents=" + contents + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + EmptyableEqualsAndHash.hashCode(contents);
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof JsonHover)) {
			return false;
		}
		JsonHover other = (JsonHover) object;
		return EmptyableEqualsAndHash.equals(contents, other.contents);
	}
	
}
