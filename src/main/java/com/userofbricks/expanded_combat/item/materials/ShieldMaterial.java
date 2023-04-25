package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.config.ECConfig.ShieldMaterialConfig;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;
import static com.userofbricks.expanded_combat.util.LangStrings.SHIELD_MATERIAL_LANG_START;

public class ShieldMaterial {
    @NotNull
    public final String name;
    @NotNull
    public final ShieldMaterialConfig shieldConfig;
    @NotNull
    public final RegistryEntry<Item> ULModel;
    @NotNull
    public final RegistryEntry<Item> URModel;
    @NotNull
    public final RegistryEntry<Item> DLModel;
    @NotNull
    public final RegistryEntry<Item> DRModel;
    @NotNull
    public final RegistryEntry<Item> MModel;

    public ShieldMaterial(@NotNull String name, @NotNull ShieldMaterialConfig shieldConfig, List<ShieldMaterial> shieldMaterials) {
        this.name = name;
        this.shieldConfig = shieldConfig;

        //register items used for models
        ULModel = createModelItem(name, "ul");
        URModel = createModelItem(name, "ur");
        DLModel = createModelItem(name, "dl");
        DRModel = createModelItem(name, "dr");
        MModel = createModelItem(name, "m");

        if(name.equals("Naga")) {
            REGISTRATE.get().addRawLang(SHIELD_MATERIAL_LANG_START + name.toLowerCase(Locale.ROOT), "Naga Scale");
        } else {
            REGISTRATE.get().addRawLang(SHIELD_MATERIAL_LANG_START + name.toLowerCase(Locale.ROOT), name);
        }

        shieldMaterials.add(this);
    }

    private RegistryEntry<Item> createModelItem(String name, String suffix) {
        return ExpandedCombat.REGISTRATE.get().item("shield_model/" + name.toLowerCase(Locale.ROOT) + "_" + suffix, Item::new)
                .model((ctx, prov) -> prov.withExistingParent("item/" + ctx.getName(), prov.modLoc("item/bases/shield_" + suffix))
                        .texture("base", prov.modLoc("item/shields/" + name.toLowerCase(Locale.ROOT))))
                .register();
    }

    public double getAfterBasePercentReduction() {
        return this.shieldConfig.afterBasePercentReduction;
    }

    public Ingredient getIngotOrMaterial() {
        return IngredientUtil.getIngrediantFromItemString(this.shieldConfig.ingotOrMaterial);
    }

    public double getMendingBonus() {
        return this.shieldConfig.mendingBonus;
    }

    public double getBaseProtectionAmmount() {
        return this.shieldConfig.baseProtectionAmmount;
    }

    public int getAddedDurability() {
        return this.shieldConfig.addedDurability;
    }

    public boolean isEmpty() {
        return this.name.toLowerCase(Locale.ROOT).equals("empty");
    }

    public boolean isSingleAddition() {
        return this.shieldConfig.isSingleAddition;
    }

    public boolean notSatifyingbeforeRequirement(String shieldMaterialName) {
        if (this.shieldConfig.requiredBeforeResource.isEmpty()) return false;
        for (String name :
                this.shieldConfig.requiredBeforeResource) {
            if (name.equals(shieldMaterialName)) return false;
        }
        return true;
    }

    /**
     * used for single additions only
     */
    public boolean satifiesOnlyReplaceRequirement(String shieldMaterialName) {
        if (this.shieldConfig.onlyReplaceResource.isEmpty()) return true;
        for (String name :
                this.shieldConfig.onlyReplaceResource) {
            if (name.equals(shieldMaterialName)) return true;
        }
        return false;
    }

    public boolean getFireResistant() {
        return this.shieldConfig.fireResistant;
    }

    public @NotNull String getName() {
        return name.toLowerCase(Locale.ROOT);
    }

    public static ShieldMaterial getFromName(String name) {
        for (ShieldMaterial material :
                MaterialInit.shieldMaterials) {
            if (material.getName().equals(name)) {
                return material;
            }
        }
        return MaterialInit.VANILLA_SHIELD;
    }

    public static ShieldMaterial getFromItemStack(ItemStack itemStack) {
        for (ShieldMaterial material :
                MaterialInit.shieldMaterials) {
            if (material.getIngotOrMaterial().test(itemStack)) {
                return material;
            }
        }
        return MaterialInit.VANILLA_SHIELD;
    }
}
