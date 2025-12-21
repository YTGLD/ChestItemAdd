package com.ytgld.chest_curio_items_add;

import com.mojang.logging.LogUtils;
import com.ytgld.chest_curio_items_add.effect.ADDEffects;
import com.ytgld.chest_curio_items_add.event.CIAEvent;
import com.ytgld.chest_curio_items_add.item.InItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ChestAddMod.MODID)
public class ChestAddMod {
    public static final String MODID = "chest_curio_items_add";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChestAddMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, TheConfig.SPEC);
        NeoForge.EVENT_BUS.register(new CIAEvent());
        InItems.ITEMS.register(modEventBus);
        InItems.CREATIVE_MODE_TABS.register(modEventBus);
        ADDEffects.EFFECT_DEFERRED_REGISTER.register(modEventBus);
    }
}
