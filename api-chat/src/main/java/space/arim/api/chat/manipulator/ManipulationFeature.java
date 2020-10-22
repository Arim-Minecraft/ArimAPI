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
package space.arim.api.chat.manipulator;

import space.arim.api.chat.SendableMessage;
import space.arim.api.chat.manipulator.SendableMessageManipulator.TextGoal;

class ManipulationFeature {

	private final SendableMessageManipulator manipulator;
	
	ManipulationFeature(SendableMessageManipulator manipulator) {
		this.manipulator = manipulator;
	}
	
	boolean isNoOp() {
		return message().isEmpty() || manipulator.getGoals().isEmpty();
	}
	
	SendableMessage message() {
		return manipulator.getMessage();
	}
	
	boolean hasGoal(TextGoal goal) {
		return manipulator.getGoals().contains(goal);
	}
	
}