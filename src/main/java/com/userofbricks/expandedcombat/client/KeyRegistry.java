/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.userofbricks.expandedcombat.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyRegistry {

  public static KeyBinding openQuiver;
  public static KeyBinding cycleQuiverRight;
  public static KeyBinding cycleQuiverLeft;

  public static void registerKeys() {
    openQuiver = registerKeybinding(new KeyBinding("key.expanded_combat.open_quiver", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, 86, "key.expanded_combat.category"));
    cycleQuiverRight = registerKeybinding(new KeyBinding("key.expanded_combat.cycle_quiver_right", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, 88, "key.expanded_combat.category"));
    cycleQuiverLeft = registerKeybinding(new KeyBinding("key.expanded_combat.cycle_quiver_left", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, 90, "key.expanded_combat.category"));
  }

  private static KeyBinding registerKeybinding(KeyBinding key) {
    ClientRegistry.registerKeyBinding(key);
    return key;
  }
}
