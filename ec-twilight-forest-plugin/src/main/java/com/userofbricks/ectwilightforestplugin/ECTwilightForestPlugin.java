package com.userofbricks.ectwilightforestplugin;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.ectwilightforestplugin.config.ECTFConfig;
import com.userofbricks.ectwilightforestplugin.util.LangStrings;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
@Mod(ECTwilightForestPlugin.MODID)
public class ECTwilightForestPlugin {
    public static final String MODID = "ec_twilight_forest_plugin";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    public static ECTFConfig CONFIG;
    public ECTwilightForestPlugin() {
        AutoConfig.register(ECTFConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ECTFConfig.class).getConfig();
        LangStrings.registerLang();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
