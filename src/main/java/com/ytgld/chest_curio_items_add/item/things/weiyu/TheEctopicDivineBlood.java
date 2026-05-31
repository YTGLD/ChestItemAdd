package com.ytgld.chest_curio_items_add.item.things.weiyu;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_curio_items_add.ChestAddMod;
import com.ytgld.chest_curio_items_add.item.InItems;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

import java.util.List;

/**
 * 未语
 * <p>
 * <p>
 *     异位神血
 * <p>
 * <p>
 * 异界神明赐下的精血，一但喝下虽然能强化自身
 * <p>
 * 但也会被自己所属的世界排斥打上异端的标签......
 * <p>
 * <p>
 * 增加10%最大生命值，增加15%护甲值，增加5%所有伤害
 * <p>
 * 对物理伤害有10%的抗性，魔法有80%抗性，但额外受到50%虚空伤害
 * <p>
 * <p>
 * 不死图腾的规避死亡无法对你生效
 * <p>
 *
 */
public class TheEctopicDivineBlood extends ItemBase {
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.chest_curio_items_add.owner")
                .append(Component.literal("“未语”"))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltipComponents.add(Component.literal(""));
        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.ectopic_blood.string.2").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.ectopic_blood.string.3").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.ectopic_blood.string.4").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal(""));

        }else  {
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.ectopic_blood.string.1")
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xff804d))));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.YELLOW));
        }
    }
    public TheEctopicDivineBlood(Properties properties) {
        super(properties);
    }
    public static void iLivingUseTotemEvent(LivingUseTotemEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InItems.TheEctopicDivineBlood_.asItem())){
                event.setCanceled(true);
            }
        }
    }
    public static void hurt(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InItems.TheEctopicDivineBlood_.asItem())) {
                event.setNewDamage(event.getNewDamage() * 1.05f);
            }
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InItems.TheEctopicDivineBlood_.asItem())) {
                if (event.getSource().is(DamageTypes.MAGIC)) {
                    event.setNewDamage(event.getNewDamage() * 0.2F);
                }else if (event.getSource().is(DamageTypes.MOB_ATTACK)) {
                    event.setNewDamage(event.getNewDamage() * 0.9F);
                }else if (event.getSource().is(DamageTypes.OUTSIDE_BORDER)){
                    event.setNewDamage(event.getNewDamage() * 1.5F);
                }
            }
        }
    }
    public static void tick(ItemStackTickEvent event) {

    }

    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.TheEctopicDivineBlood_.asItem().getDescriptionId()), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ARMOR, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID +
                InItems.TheEctopicDivineBlood_.asItem().getDescriptionId()), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }
}
