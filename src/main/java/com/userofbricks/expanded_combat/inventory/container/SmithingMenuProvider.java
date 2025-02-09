package com.userofbricks.expanded_combat.inventory.container;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.LegacySmithingMenu;
import net.minecraft.world.inventory.SmithingMenu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SmithingMenuProvider implements MenuProvider {
    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.upgrade");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        return playerEntity.level.enabledFeatures().contains(FeatureFlags.UPDATE_1_20) ? new SmithingMenu(i, playerInventory, ContainerLevelAccess.NULL) : new LegacySmithingMenu(i, playerInventory, ContainerLevelAccess.NULL);
    }
}
