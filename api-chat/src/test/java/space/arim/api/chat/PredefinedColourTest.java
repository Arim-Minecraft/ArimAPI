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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PredefinedColourTest {

	@Test
	public void testNearestFinderIdentity() {
		for (PredefinedColour entry : PredefinedColour.values()) {
			assertEquals(entry, PredefinedColour.getNearestTo(entry.getColour()));
		}
	}
	
	@Test
	public void testByCharIdentity() {
		for (PredefinedColour entry : PredefinedColour.values()) {
			char codeChar = entry.getCodeChar();
			assertEquals(entry, PredefinedColour.getByChar(codeChar));
			assertEquals(entry, PredefinedColour.getByChar(Character.toUpperCase(codeChar)));
		}
	}
	
}
