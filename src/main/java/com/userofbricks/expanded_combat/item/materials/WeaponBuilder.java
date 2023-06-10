package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.Map;

public class WeaponBuilder extends MaterialBuilder{
    public static RegistryEntry<ECWeaponItem> generateWeapon(Registrate registrate, String locationName, String name, WeaponMaterial weapon, Material material, Material craftedFrom) {
        ItemBuilder<ECWeaponItem, Registrate> itemBuilder = registrate.item(locationName + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem(material, weapon, p));
        if (weapon.dyeable() && weapon.potionDippable()) itemBuilder = registrate.item(locationName + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem.HasPotionAndIsDyeable(material, weapon, p));
        else if (weapon.dyeable()) itemBuilder = registrate.item(locationName + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem.Dyeable(material, weapon, p));
        else if (weapon.potionDippable()) itemBuilder = registrate.item(locationName + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem.HasPotion(material, weapon, p));

        if (weapon.potionDippable()) itemBuilder.tag(ECItemTags.POTION_WEAPONS);

        //MODEL
        itemBuilder.model((ctx, prov) -> {
            ItemModelBuilder modelbuilder;
            if (weapon.isBlockWeapon()) {
                modelbuilder = getItemBaseModel(ctx, prov, weapon, "").texture("head", getWeaponTexture(prov, weapon.getLocationName(), locationName));
            } else {
                if (weapon.dyeable() || weapon.potionDippable()) modelbuilder = prov.generated(ctx, getWeaponDyeTexture(prov, weapon.getLocationName()), getWeaponTexture(prov, weapon.getLocationName(), locationName));
                else modelbuilder = prov.generated(ctx, getWeaponTexture(prov, weapon.getLocationName(), locationName));
            }

            if (weapon.hasLargeModel() && !weapon.isBlockWeapon()) {
                ItemModelBuilder largeModelbuilder = getItemBaseModel(ctx, prov, weapon, "_large");
                if (weapon.dyeable()) {
                    largeModelbuilder.texture("layer0", prov.modLoc("item_large/" + weapon.getLocationName() + "/" + "dye"));
                    largeModelbuilder.texture("layer1", prov.modLoc("item_large/" + weapon.getLocationName() + "/" + locationName));
                }
                else largeModelbuilder.texture("layer0", prov.modLoc("item_large/" + weapon.getLocationName() + "/" + locationName));

                modelbuilder.override().predicate(new ResourceLocation(ExpandedCombat.MODID, "large"), 1).model(largeModelbuilder).end();
            }
        });
        itemBuilder.recipe((ctx, prov) -> {
            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("weapon");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> ingredientMap = new RecipeIngredientMapBuilder().put('i', material.getConfig().crafting.repairItem).build();
                if (weapon.recipeIngredients() != null) {
                    if (!weapon.recipeContains("i")) ingredientMap.remove('i');
                    ingredientMap.putAll(weapon.recipeIngredients().get().build());
                    if(ingredientMap.get('p') == null && weapon.recipeContains("p")) {
                        Ingredient prev = weapon.craftedFrom() == null ? IngredientUtil.getTagedIngredientOrEmpty("forge", "tools/swords/" + material.getLocationName()) : Ingredient.of(material.getWeaponEntry(weapon.craftedFrom().name()).get());
                        ingredientMap.put('p', prev);
                    }
                    if(ingredientMap.get('b') == null && weapon.recipeContains("b")) ingredientMap.put('b', IngredientUtil.getTagedIngredientOrEmpty("forge", "storage_blocks/" + material.getLocationName()));

                    conditionalShapedRecipe(ctx, prov, weapon.recipe(), ingredientMap, 1, new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, triggerInstance, "");
                }
                if (craftedFrom != null){
                    conditionalLegacySmithingRecipe(ctx, prov, IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                            Ingredient.of(craftedFrom.getWeaponEntry(weapon.name()).get()),
                            new ICondition[]{enableArrows, isSingleAddition}, triggerInstance, "");
                    conditionalSmithing120Recipe(ctx, prov, material, Ingredient.of(craftedFrom.getWeaponEntry(weapon.name()).get()), new ICondition[]{enableArrows, isSingleAddition}, "");
                }
            }
        });
        if (weapon.dyeable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack));
        } else if (weapon.potionDippable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : PotionUtils.getColor(stack));
        }

        return itemBuilder.register();
    }

    private static ItemModelBuilder getItemBaseModel(DataGenContext<Item, ECWeaponItem> ctx, RegistrateItemModelProvider prov, WeaponMaterial weapon, String suffix) {
        return prov.withExistingParent("item/" + ctx.getName() + suffix, prov.modLoc("item/bases/" + weapon.getLocationName()));
    }

    private static ResourceLocation getWeaponTexture(RegistrateItemModelProvider prov, String weaponLocation, String textureName) {
        return prov.modLoc(("item/") + weaponLocation + "/" + textureName);
    }

    private static ResourceLocation getWeaponDyeTexture(RegistrateItemModelProvider prov, String weaponLocation) {
        return getWeaponTexture(prov, weaponLocation, "dye");
    }

}
