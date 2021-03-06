/*
 * ArimAPI
 * Copyright © 2021 Anand Beh
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

package space.arim.api.env.velocity;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.audience.Audience;
import space.arim.api.env.AudienceRepresenter;

import java.util.Objects;

/**
 * Simple, transparent implementation of {@code AudienceRepresenter} for Velocity. <br>
 * <br>
 * See also {@link AudienceRepresenter#identity()}
 *
 */
public final class VelocityAudienceRepresenter implements AudienceRepresenter<CommandSource> {

    @Override
    public Audience toAudience(CommandSource commandSender) {
        Objects.requireNonNull(commandSender, "commandSender");
        return commandSender;
    }
}
