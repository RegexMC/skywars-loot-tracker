/*
 * Hytilities - Hypixel focused Quality of Life mod.
 * Copyright (C) 2020  Sk1er LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.regexmc.skywarsloottracker.utils.locraw;

import com.google.gson.Gson;
import me.regexmc.skywarsloottracker.handlers.GameType;
import me.regexmc.skywarsloottracker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LocrawUtil {

    private final Gson gson = new Gson();
    private LocrawInformation locrawInformation;
    private boolean listening;
    private int tick;
    private boolean playerSentCommand = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        boolean onHypixel = Utils.onHypixel(Minecraft.getMinecraft());
        if (event.phase != TickEvent.Phase.START || Minecraft.getMinecraft().thePlayer == null || !onHypixel || this.tick >= 20) {
            return;
        }

        this.tick++;
        if (this.tick == 20) {
            this.listening = true;
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        tick = 0;
    }

    @SubscribeEvent
    public void onMessageReceived(ClientChatReceivedEvent event) {
        try {
            final String msg = event.message.getUnformattedTextForChat();
            if (msg.startsWith("{")) {
                this.locrawInformation = gson.fromJson(msg, LocrawInformation.class);
                if (locrawInformation != null) {
                    this.locrawInformation.setGameType(GameType.getFromLocraw(locrawInformation.getRawGameType()));

                    if (!this.playerSentCommand) {
                        event.setCanceled(true);
                    }

                    this.playerSentCommand = false;
                    this.listening = false;
                }
            }
        } catch (Exception ignored) {
        }
    }

    public boolean isEnabled() {
        return listening;
    }

    public LocrawInformation getLocrawInformation() {
        return this.locrawInformation;
    }
}