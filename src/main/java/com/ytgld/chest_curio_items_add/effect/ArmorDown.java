package com.ytgld.chest_curio_items_add.effect;

import com.ytgld.chest_curio_items_add.ChestAddMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorDown extends MobEffect {
    public ArmorDown() {
        super(MobEffectCategory.BENEFICIAL, 0xffffff);
        this.addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(ChestAddMod.MODID,
                "armor_down"),-1, AttributeModifier.Operation.ADD_VALUE);
    }


}
