package com.userofbricks.ectwilightforestplugin.config;

import com.userofbricks.expanded_combat.config.ConfigName;
import com.userofbricks.expanded_combat.config.ECConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.resources.ResourceLocation;

import static com.userofbricks.ectwilightforestplugin.ECTwilightForestPlugin.MODID;
import static com.userofbricks.ectwilightforestplugin.util.ModIDs.TwilightForestMOD_ID;

@Config(name = MODID)
public class ECTFConfig implements ConfigData {

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Naga Scale Settings")
    public ECConfig.MaterialConfig nagaScale = new ECConfig.MaterialConfig.Builder()
            .toolDurability((int)(512 * 1.05)).gauntletArmorAmount(3).gauntletAttackDamage(2.1f).armorToughness(0.5f)
            .addedShieldDurability(260).baseProtectionAmmount(4).afterBasePercentReduction(0.65f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "naga_scale"))
            .defenseEnchantability(15)
            .mendingBonus(0.1f)
            .build();

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Arctic Settings")
    public ECConfig.MaterialConfig arctic = new ECConfig.MaterialConfig.Builder()
            .toolDurability(131).gauntletArmorAmount(2).gauntletAttackDamage(2).armorToughness(2)
            .addedShieldDurability(130).baseProtectionAmmount(3).afterBasePercentReduction(0.55f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "arctic_fur"))
            .defenseEnchantability(8)
            .build();

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Arctic Settings")
    public ECConfig.MaterialConfig yeti = new ECConfig.MaterialConfig.Builder()
            .toolDurability(512).gauntletArmorAmount(3).gauntletAttackDamage(2.5f).armorToughness(3)
            .addedShieldDurability(250).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.65f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "alpha_yeti_fur"))
            .defenseEnchantability(15)
            .build();

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Ironwood Settings")
    public ECConfig.MaterialConfig ironwood = new ECConfig.MaterialConfig.Builder()
            .toolDurability(512).gauntletArmorAmount(2).gauntletAttackDamage(2f)
            .addedShieldDurability(250).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.6f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "ironwood_ingot"))
            .offenseEnchantability(25).defenseEnchantability(15)
            .mendingBonus(1.5f)
            .build();

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Fiery Settings")
    public ECConfig.MaterialConfig fiery = new ECConfig.MaterialConfig.Builder()
            .toolDurability(1024).gauntletArmorAmount(4).gauntletAttackDamage(4f).armorToughness(1.5)
            .addedShieldDurability(275).baseProtectionAmmount(4.5f).afterBasePercentReduction(0.7f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "fiery_ingot"))
            .offenseEnchantability(10).defenseEnchantability(10)
            .flaming()
            .build();

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Steeleaf Settings")
    public ECConfig.MaterialConfig steeleaf = new ECConfig.MaterialConfig.Builder()
            .toolDurability(131).gauntletArmorAmount(3).gauntletAttackDamage(3f)
            .addedShieldDurability(180).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.6f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "steeleaf_ingot"))
            .offenseEnchantability(9).defenseEnchantability(9)
            .build();

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Knightmetal Settings")
    public ECConfig.MaterialConfig knightmetal = new ECConfig.MaterialConfig.Builder()
            .toolDurability(512).gauntletArmorAmount(3).gauntletAttackDamage(3f).armorToughness(1)
            .addedShieldDurability(250).baseProtectionAmmount(4).afterBasePercentReduction(0.6f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "knightmetal_ingot"))
            .offenseEnchantability(8).defenseEnchantability(8)
            .equipSound(new ResourceLocation(TwilightForestMOD_ID, "item.twilightforest.armor.equip_knightmetal"))
            .build();
}
