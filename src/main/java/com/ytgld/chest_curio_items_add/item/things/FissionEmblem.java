package com.ytgld.chest_curio_items_add.item.things;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_curio_items_add.ChestAddMod;
import com.ytgld.chest_curio_items_add.item.InItems;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FissionEmblem extends ItemBase {
    public static final String rageTime = "RageTime";
    public static final int maxRageTime = 20 * 20;

    public static final String damageTime = "damageTime";
    public static final int maxDamageTime = 10 * 20;
    public FissionEmblem(Properties properties) {
        super(properties);
    }

    public static void scAttack(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory !=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); ++i) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InItems.FissionEmblem_)) {
                            if (event.getEntity().position().distanceTo(player.position()) <= 10) {
                                event.setNewDamage(event.getNewDamage() * 1.1f);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    public static void hurt(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory !=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); ++i) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InItems.FissionEmblem_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag != null) {
                                if (event.getNewDamage() > player.getMaxHealth() * 0.25F) {
                                    compoundTag.putInt(rageTime, maxRageTime);
                                    player.level().playSound(null,player.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.AMBIENT,1,1);
                                }
                                if (event.getNewDamage() > player.getMaxHealth() * 0.5f) {
                                    compoundTag.putInt(damageTime, maxDamageTime);
                                    player.level().playSound(null,player.blockPosition(), SoundEvents.RAVAGER_ROAR, SoundSource.AMBIENT,1,1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void attack(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory !=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); ++i) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InItems.FissionEmblem_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag != null) {
                                if (compoundTag.getInt(damageTime) > 0) {
                                    event.setNewDamage(event.getNewDamage() * 2);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void tick(ItemStackTickEvent event) {
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for(int i = 0; i < chestInventory.getContainerSize(); ++i) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InItems.FissionEmblem_)) {
                    CompoundTag compoundTag = stack.get(DataReg.tag);
                    if (compoundTag != null) {
                        if (compoundTag.getInt(rageTime) > 0) {
                            compoundTag.putInt(rageTime,compoundTag.getInt(rageTime)-1);
                        }
                        if (compoundTag.getInt(damageTime) > 0) {
                            compoundTag.putInt(damageTime,compoundTag.getInt(damageTime)-1);
                        }
                    }else {
                        stack.set(DataReg.tag,new CompoundTag());
                    }
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap(stack));
                    break;
                }
                player.getAttributes().removeAttributeModifiers(attributeModifierMultimap(stack));
            }
        }

    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float damage = 2;
        float armor = 0.05f;
        float maxH = 0.1f;
        float speed = -0.15f;
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            if (compoundTag.getInt(rageTime) > 0) {
                damage *= 2.05F;
                armor *= 2.05F;
                maxH *= 2.05F;
                speed *= 2.05F;
            }
        }else {
            stack.set(DataReg.tag,new CompoundTag());
        }

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.FissionEmblem_.asItem().getDescriptionId()), damage, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(Attributes.ARMOR, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.FissionEmblem_.asItem().getDescriptionId()), armor, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.FissionEmblem_.asItem().getDescriptionId()), maxH, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.FissionEmblem_.asItem().getDescriptionId()), speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        
        tooltipComponents.add(Component.translatable("item.chest_curio_items_add.owner")
                .append(Component.literal("“呃子”"))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.fission_emblem.string.1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.fission_emblem.string.2").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.fission_emblem.string.3").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.fission_emblem.string.4").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.fission_emblem.string.5").withStyle(ChatFormatting.GOLD));
        }else  {
            tooltipComponents.add(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.YELLOW));
        }
    }
    public @Nullable Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return attributeModifierMultimap(stack);
    }
}
