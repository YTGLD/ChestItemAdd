package com.ytgld.chest_curio_items_add.item.things.days;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_curio_items_add.ChestAddMod;
import com.ytgld.chest_curio_items_add.item.InItems;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 【残息追猎】
 * <p>
 * 使用时检测半径20m内所有实体（发光），满血时探测8s，若不是则探测15s（冷却20秒）
 * <p>
 * 附近存在被探测到的目标时增加自己的移速，攻速和攻击伤害
 * <p>
 * 每击杀1个目标恢复等同于最后一击造成伤害50%的生命值
 */
public class TheRemnantsOfTheHunt extends ItemBase {
    public static final String size = "NumberTarget";
    public TheRemnantsOfTheHunt(Properties properties) {
        super(properties);
    }

    public static void tick(ItemStackTickEvent event) {
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            s: for(int i = 0; i < chestInventory.getContainerSize(); ++i) {
                ItemStack stack = chestInventory.getItem(i);
                if (player.tickCount % 400 == 1) {
                    if (stack.is(InItems.TheRemnantsOfTheHunt_)) {
                        CompoundTag compoundTag = stack.get(DataReg.tag);

                        Vec3 playerPos = player.position();
                        int range = 20;
                        List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - (double) range,
                                playerPos.y - (double) range, playerPos.z - (double) range,
                                playerPos.x + (double) range, playerPos.y + (double) range,
                                playerPos.z + (double) range));
                        if (compoundTag == null) {
                            stack.set(DataReg.tag,new CompoundTag());
                        }

                        List<Integer> integers = new ArrayList<>();
                        for (LivingEntity living : entities) {
                            if (!living.is(player) && living.isAlive()) {
                                if (living.getHealth() >= living.getMaxHealth()) {
                                    living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 8 * 20));
                                } else {
                                    living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 15 * 20));
                                }
                                integers.add(1);
                            }
                        }
                        if (compoundTag != null) {
                            compoundTag.putInt(size,integers.size());
                        }
                    }
                }
            }
        }
    }
    public static void attack(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory !=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); ++i) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InItems.TheRemnantsOfTheHunt_)) {
                            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                                if (event.getEntity() instanceof LivingEntity living) {
                                    if (living.hasEffect(MobEffects.GLOWING)) {
                                        player.heal((float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f));
                                        player.getCooldowns().addCooldown(stack.getItem(), 10);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void tickAttrib(ItemStackTickEvent event) {
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for(int i = 0; i < chestInventory.getContainerSize(); ++i) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InItems.TheRemnantsOfTheHunt_)) {
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap(stack));
                    break;
                }

                player.getAttributes().removeAttributeModifiers(attributeModifierMultimap(stack));
            }
        }

    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float a = 0;
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            float modify = 0.02f;
            modify*=compoundTag.getInt(size);
            a += modify;
        }

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.TheRemnantsOfTheHunt_.asItem().getDescriptionId()),
                a, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.TheRemnantsOfTheHunt_.asItem().getDescriptionId()),
                a, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.TheRemnantsOfTheHunt_.asItem().getDescriptionId()),
                a, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));



        return modifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.chest_curio_items_add.owner")
                .append(Component.literal("“Days”"))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.the_remnants_of_the_hunt.string.1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.the_remnants_of_the_hunt.string.2").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.the_remnants_of_the_hunt.string.3").withStyle(ChatFormatting.GOLD));
        }else  {
            tooltipComponents.add(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.YELLOW));
        }
        float a = 0;
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            float modify = 0.02f;
            modify*=compoundTag.getInt(size);
            a += modify;
        }
        tooltipComponents.add(Component.literal(""));

        tooltipComponents.add(Component.translatable("attribute.name.generic.attack_damage").append(": ").append(String.valueOf(a*100)).append("%").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("attribute.name.generic.movement_speed").append(": ").append(String.valueOf(a*100)).append("%").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("attribute.name.generic.attack_speed").append(": ").append(String.valueOf(a*100)).append("%").withStyle(ChatFormatting.GOLD));
    }
}
