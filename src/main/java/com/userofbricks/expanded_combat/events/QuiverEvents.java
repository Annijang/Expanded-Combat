package com.userofbricks.expanded_combat.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

public class QuiverEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onArrowItemPickup(EntityItemPickupEvent evt) {
        Player player = evt.getEntity();
        ItemStack toPickup = evt.getItem().getItem();
        SlotResult slotResult = CuriosApi.getCuriosHelper().findFirstCurio(player, item -> item.getItem() instanceof ECQuiverItem).orElse(null);
        if(toPickup.is(ItemTags.ARROWS) && slotResult != null && slotResult.stack().getItem() instanceof ECQuiverItem quiverItem) {
             CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(curios -> {
                 IDynamicStackHandler arrowStackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                 int slots = arrowStackHandler.getSlots();

                 for (int s = 0; s < slots; s++) {
                     ItemStack currentStack = arrowStackHandler.getStackInSlot(s);
                     ItemStack rem = toPickup.copy();
                     int itemsRemaining = toPickup.getCount();
                     if ((currentStack.getItem() == toPickup.getItem() || currentStack.isEmpty()) && quiverItem.providedSlots > s) {
                         rem = arrowStackHandler.insertItem(s, rem, false);
                         if (rem.getCount() < itemsRemaining) {
                             //arrowStackHandler.getStackInSlot(s).setPopTime(5);
                             //TODO: make arrows picked up make pop sound
                             player.awardStat(Stats.ITEM_PICKED_UP.get(toPickup.getItem()), itemsRemaining - rem.getCount());
                         }
                     }
                     toPickup.setCount(rem.getCount());
                 }
             });
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onInventoryGuiInit(ContainerScreenEvent.Render.Background evt) {
        AbstractContainerScreen<?> screen = evt.getContainerScreen();
        if (screen instanceof CuriosScreen curiosScreen) {
            RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/container/quiver.png"));
            int left = curiosScreen.getGuiLeft();
            int top = curiosScreen.getGuiTop();
            GuiComponent.blit(evt.getPoseStack(), left + 76, top + 43, 45, 18, 18, 18);

            CuriosApi.getCuriosHelper().getCuriosHandler(curiosScreen.getMenu().player).ifPresent(curios -> {
                Item quiverItem = curios.getCurios().get(QUIVER_CURIOS_IDENTIFIER).getStacks().getStackInSlot(0).getItem();
                int curiosSlots = 0;
                if (quiverItem instanceof ECQuiverItem ecQuiverItem) curiosSlots = ecQuiverItem.providedSlots;
                if (curiosSlots > 0){
                    GuiComponent.blit(evt.getPoseStack(), left + 175, top + 4, 0, 0, 2, 158);
                    for (int column = 0; column < roundToNearest8(curiosSlots) / 8; column++) {
                        if ((column - (roundToNearest8(curiosSlots) / 8)) == -1) GuiComponent.blit(evt.getPoseStack(), left + 177 + (column * 18), top + 4, 20, 0, 25, 158);
                        else GuiComponent.blit(evt.getPoseStack(), left + 177 + (column * 18), top + 4, 2, 0, 18, 158);
                    }
                    int x = 176 + 1;
                    int y = 11;
                    int row = 1;
                    for (int slot = 0; slot < roundToNearest8(curiosSlots); slot++, row++) {
                        if (!(slot < curiosSlots)) {
                            GuiComponent.blit(evt.getPoseStack(), left + x, top + y, 45, 0, 18, 18);
                        }
                        y += 18;
                        if (row == 8) {
                            row = 0;
                            y = 11;
                            x += 18;
                        }
                    }
                }
            });
        }
    }

    public static int roundToNearest8(int original) {
        int modulus = original % 8;
        if (modulus != 0) {
            return original + (8 - modulus);
        }
        return original;
    }
}
