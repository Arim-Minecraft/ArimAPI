/* 
 * ArimAPI-configure
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-configure is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-configure is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-configure. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.configure.yaml;

/**
 * Indicates an error parsing yaml syntax.
 * 
 * @author A248
 *
 * @deprecated See deprecation of {@link space.arim.api.configure} (this entire framework is deprecated)
 */
@Deprecated(forRemoval = true)
public class YamlSyntaxException extends Exception {

	/**
	 * Serial version uid
	 */
	private static final long serialVersionUID = -1124642875692283525L;

	/**
	 * Creates an exception with the given cause
	 * 
	 * @param cause the cause
	 */
	public YamlSyntaxException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Creates an exception with the given message and cause
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public YamlSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Creates an exception with the given message
	 * 
	 * @param message the message
	 */
	public YamlSyntaxException(String message) {
		super(message);
	}
	
	/**
	 * Creates an exception with no message
	 * 
	 */
	public YamlSyntaxException() {
		
	}
	
}
