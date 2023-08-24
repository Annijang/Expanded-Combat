package com.userofbricks.ectwilightforestplugin.util;

import com.userofbricks.ectwilightforestplugin.config.ECTFConfig;
import com.userofbricks.expanded_combat.ExpandedCombat;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.userofbricks.ectwilightforestplugin.ECTwilightForestPlugin.REGISTRATE;
import static com.userofbricks.expanded_combat.util.LangStrings.getOrCreateCategoryForField;
import static com.userofbricks.expanded_combat.util.LangStrings.ifNotExcludedRegisterLangs;

public class LangStrings {
    public static final String FIERY_WEAPON_TOOLTIP = "tooltip.expanded_combat.fiery.weapon";
    public static final String KNIGHTMETAL_ARMORED_WEAPON_TOOLTIP = "tooltip.expanded_combat.knightly.weapon_armored";
    public static final String KNIGHTMETAL_UNARMORED_WEAPON_TOOLTIP = "tooltip.expanded_combat.knightly.weapon_unarmored";

    private static final Supplier<String> configLangStartGetter = () -> "text.autoconfig." + ECTFConfig.class.getAnnotation(Config.class).name();
    public static void registerLang() {
        List<String> alreadyAddedStrings = new ArrayList<>();

        REGISTRATE.get().addRawLang(FIERY_WEAPON_TOOLTIP, "Burns targets");
        REGISTRATE.get().addRawLang(KNIGHTMETAL_ARMORED_WEAPON_TOOLTIP, "Extra damage to armored targets");
        REGISTRATE.get().addRawLang(KNIGHTMETAL_UNARMORED_WEAPON_TOOLTIP, "Extra damage to unarmored targets");

        //Config
        String configLangStart = configLangStartGetter.get();
        ExpandedCombat.REGISTRATE.get().addRawLang(configLangStart + ".title", "Expanded Combat Settings");
        Arrays.stream(ECTFConfig.class.getDeclaredFields()).collect(
                        Collectors.groupingBy((field) -> getOrCreateCategoryForField(field, alreadyAddedStrings, configLangStart), LinkedHashMap::new, Collectors.toList()))
                .forEach((key, value) -> value.forEach((field) -> ifNotExcludedRegisterLangs(field, configLangStart, alreadyAddedStrings)));
    }
}
