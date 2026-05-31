package com.ytgld.chest_curio_items_add.item.things.yilezi;

import com.ytgld.chest_curio_items_add.effect.ADDEffects;
import com.ytgld.chest_curio_items_add.item.InItems;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 雷鼓
 * <p>
 * 	增加6点震钟范围，施加不稳定使额外施加一层
 * <p>
 * 	使不稳定层数上限增加2层
 * <p>
 * 	根据叠加的层数增加目标每层5％的受伤倍率
 *
 *
 */
public class ThunderDrum extends ItemBlackShadow {
    public ThunderDrum(Properties properties) {
        super(properties);
    }

    public static void attack(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InItems.ThunderDrum_.asItem())){
                if (event.getEntity() instanceof LivingEntity living) {
                    @Nullable MobEffectInstance mobEffectInstance = living.getEffect(ADDEffects.Instability_);
                    if (mobEffectInstance != null){
                        event.setNewDamage(event.getNewDamage() * (1 + (mobEffectInstance.getAmplifier() * 0.05f)));
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        
        tooltipComponents.add(Component.translatable("item.chest_curio_items_add.owner")
                .append(Component.literal("“伊乐子”"))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.thunder_drum.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(-2140513587))));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.thunder_drum.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(-2140513587))));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.thunder_drum.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(-2140513587))));
        }else  {
            tooltipComponents.add(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.YELLOW));
        }
    }
}
