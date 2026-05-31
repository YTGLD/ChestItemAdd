package com.ytgld.chest_curio_items_add.effect;

import com.ytgld.chest_curio_items_add.ChestAddMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ADDEffects {
    public static final DeferredRegister<MobEffect> EFFECT_DEFERRED_REGISTER = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, ChestAddMod.MODID);
    public static final DeferredHolder<MobEffect, MobEffect> Instability_ = EFFECT_DEFERRED_REGISTER.register("instability",
            Instability::new);;
    public static final DeferredHolder<MobEffect, MobEffect> ArmorDown_ = EFFECT_DEFERRED_REGISTER.register("armor_down",
            ArmorDown::new);
}
