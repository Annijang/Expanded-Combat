package com.userofbricks.ectwilightforestplugin.item;

import com.tterrag.registrate.providers.ProviderType;
import com.userofbricks.expanded_combat.item.ECItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import twilightforest.init.TFItems;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;
import static com.userofbricks.expanded_combat.item.ECItemTags.bindForgeSword;

public class ECTFItemTags {

    public static final TagKey<Item> IRONWOOD_SWORD = bindForgeSword("ironwood");
    public static final TagKey<Item> FIERY_SWORD = bindForgeSword("fiery");
    public static final TagKey<Item> STEELEAF_SWORD = bindForgeSword("steeleaf");
    public static final TagKey<Item> KNIGHT_METAL_SWORD = bindForgeSword("knight_metal");

    public static void loadTags() {
        REGISTRATE.get().addDataGenerator(ProviderType.ITEM_TAGS, tagsProvider -> {
            tagsProvider.addTag(ECItemTags.SHIELDS).add(TFItems.KNIGHTMETAL_SHIELD.get());
            tagsProvider.addTag(IRONWOOD_SWORD).add(TFItems.IRONWOOD_SWORD.get());
            tagsProvider.addTag(FIERY_SWORD).add(TFItems.FIERY_SWORD.get());
            tagsProvider.addTag(STEELEAF_SWORD).add(TFItems.STEELEAF_SWORD.get());
            tagsProvider.addTag(KNIGHT_METAL_SWORD).add(TFItems.KNIGHTMETAL_SWORD.get());
        });
    }
}
