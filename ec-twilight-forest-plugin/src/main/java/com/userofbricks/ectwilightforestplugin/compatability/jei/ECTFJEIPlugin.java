package com.userofbricks.ectwilightforestplugin.compatability.jei;

import com.userofbricks.ectwilightforestplugin.compatability.jei.recipes.ECShieldSmithingRecipeMaker;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.ectwilightforestplugin.ECTwilightForestPlugin.MODID;
import static com.userofbricks.expanded_combat.compatability.jei.recipe_category.ShieldSmithingRecipeCategory.SHIELD_SMITHING;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECTFJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IStackHelper stackHelper = registration.getJeiHelpers().getStackHelper();
        registration.addRecipes(SHIELD_SMITHING, ECShieldSmithingRecipeMaker.createShieldSmithingRecipes(stackHelper));
    }
}
