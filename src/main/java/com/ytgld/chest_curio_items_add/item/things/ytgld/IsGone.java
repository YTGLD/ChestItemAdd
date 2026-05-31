package com.ytgld.chest_curio_items_add.item.things.ytgld;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_curio_items_add.ChestAddMod;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.items.memory.MemoryAttreg;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 咒界无存
 * <p>
 * +1 最大信仰
 * <p>
 * +1 时运
 * <p>
 * +1 抢夺
 * <p>
 * 无法取下
 */
public class IsGone extends TheImprintOfTheSoul {
    public IsGone(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation resourceLocation() {
        return ResourceLocation.fromNamespaceAndPath(ChestAddMod.MODID, "textures/item/air.png");
    }

    @Override
    public int soulColor() {
        return Light.ARGB.color(1,1,1,1);
    }

    @Override
    public void text(ItemStack stack, List<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.add(Component.translatable("item.chest_curio_items_add.owner")
                .append(Component.literal("“Ytgld”"))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_curio_items_add.is_gone.string.1").withStyle(Style.EMPTY.withColor(color(stack))));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(MemoryAttreg.maxMemory, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID + this.getDescriptionId()),
                1, AttributeModifier.Operation.ADD_VALUE));
        int i = player.getData(TheMemoryDataHandler.mStringSetData).size();

        modifiers.put(AttReg.looting, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID + this.getDescriptionId()),
                i, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.fortune, new AttributeModifier(ResourceLocation.parse(ChestAddMod.MODID + this.getDescriptionId()),
                i, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
}
