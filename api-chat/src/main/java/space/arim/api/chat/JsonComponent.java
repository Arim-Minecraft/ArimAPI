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

/**
 * A specific kind of {@link Component} with JSON support. <br>
 * <br>
 * JsonComponent is immutable; {@link JsonComponentBuilder} should be used for construction.
 * 
 * @author A248
 *
 */
public class JsonComponent extends Component implements JsonComponentFramework {

	private final Message ttp;
	private final String url;
	private final String cmd;
	private final String sgt;
	private final String ins;
	
	JsonComponent(String text, Colour colour, Style[] styles, Message ttp, String url, String cmd, String sgt, String ins) {
		super(text, colour, styles);
		this.ttp = ttp;
		this.url = url;
		this.cmd = cmd;
		this.sgt = sgt;
		this.ins = ins;
	}
	
	@Override
	public Message getTooltip() {
		return ttp;
	}
	
	@Override
	public String getUrl() {
		return url;
	}
	
	@Override
	public String getCommand() {
		return cmd;
	}
	
	@Override
	public String getSuggestion() {
		return sgt;
	}
	
	@Override
	public String getInsertion() {
		return ins;
	}
	
	/**
	 * Creates a new Component with all colour formatting removed. <br>
	 * JSON formatting is carried over.
	 * 
	 * @return a fresh Component with colour removed
	 */
	@Override
	public JsonComponent stripColour() {
		return new JsonComponent(getText(), null, getStyles(), getTooltip(), getUrl(), getCommand(), getSuggestion(), getInsertion());
	}
	
	/**
	 * Creates a new Component with all styles formatting removed. <br>
	 * JSON formatting is carried over.
	 * 
	 * @return a fresh Component with styles removed
	 */
	@Override
	public JsonComponent stripStyles() {
		return new JsonComponent(getText(), getColour(), null, getTooltip(), getUrl(), getCommand(), getSuggestion(), getInsertion());
	}
	
	/**
	 * Creates a new Component with all JSON formatting removed
	 * 
	 * @return a fresh Component with JSON removed
	 */
	public Component stripJson() {
		return new Component(getText(), getColour(), getStyles());
	}
	
	/**
	 * Creates a new Component with all colour, styles, and JSON formatting removed. <br>
	 * 
	 * @return a fresh Component with colour, styles, and JSON removed
	 */
	@Override
	public Component stripAll() {
		return stripJson().stripAll();
	}
	
	/**
	 * Identical to {@link JsonComponentBuilder}
	 * 
	 * @author A248
	 *
	 */
	public class Builder extends JsonComponentBuilder {
		
	}
	
	@Override
	public String toString() {
		return toStringMe();
	}

}
