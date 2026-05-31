package com.ytgld.chest_curio_items_add.item.things.yilezi;

import com.ytgld.chest_curio_items_add.effect.ADDEffects;
import com.ytgld.chest_curio_items_add.item.InItems;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 震钟
 * <p>
 * 每5秒激活一次，以玩家为中心，向外6格呈现圆的区域内，为生物施加一次不稳定
 * <p>
 * 不稳定叠加至多5层
 * <p>
 * 叠加满时再次叠加即可产生震爆并清除所有不稳定层数，每层不稳定固定造成4点真实伤害
 */
public class Bell extends ItemBase {
    public Bell(Properties properties) {
        super(properties);
    }


    public static void tick(ItemStackTickEvent event) {
        Player player = event.player;
        if (Handler.has(player, InItems.Bell_.asItem())) {
            Vec3 playerPos = player.position();
            int range = rage(player);
            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - (double) range,
                    playerPos.y - (double) range, playerPos.z - (double) range,
                    playerPos.x + (double) range, playerPos.y + (double) range,
                    playerPos.z + (double) range));

            for (LivingEntity living : entities) {
                if (!living.is(player)) {
                    if (living.tickCount % time(player) == 0) {
                        living.addEffect(new MobEffectInstance(ADDEffects.Instability_, 30 * 20, 0));
                        @Nullable MobEffectInstance mobEffectInstance = living.getEffect(ADDEffects.Instability_);
                        if (mobEffectInstance != null) {
                            if (mobEffectInstance.getAmplifier() < maxLevel(player)) {
                                living.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),
                                        30 * 20,
                                        mobEffectInstance.getAmplifier() + addLevel(player)));
                            } else {
                                living.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),
                                        30 * 20,
                                        maxLevel(player) + 1));
                            }

                            if (mobEffectInstance.getAmplifier() > boomLevel(player)) {
                                if (Handler.has(player, InItems.DevilRinging_.asItem())) {
                                    living.addEffect(new MobEffectInstance(ADDEffects.ArmorDown_, 2000, boomLevel(player)));
                                }

                                living.hurt(living.damageSources().wither(), mobEffectInstance.getAmplifier() * 5f);
                                living.removeEffect(ADDEffects.Instability_);

                            }
                        }
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
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.bell.string.1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.bell.string.2").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.bell.string.3").withStyle(ChatFormatting.GOLD));
        }else  {
            tooltipComponents.add(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.YELLOW));
        }
    }
    public static int time (Player player){
        int time = 20*5;
        if (Handler.has(player,InItems.DevilRinging_.asItem())){
            time -= 20*2;
        }

        return time;

    }
    public static int rage (Player player){
        int rage = 6;
        if (Handler.has(player,InItems.ThunderDrum_.asItem())){
            rage += 6;
        }
        return rage;
    }
    public static int maxLevel (Player player){
        int boomLevel = 4;
        if (Handler.has(player,InItems.ThunderDrum_.asItem())){
            boomLevel += 2;
        }
        if (Handler.has(player,InItems.DevilRinging_.asItem())){
            boomLevel += 2;
        }
        return boomLevel;
    }
    public static int addLevel (Player player){
        int level = 1;
        if (Handler.has(player,InItems.ThunderDrum_.asItem())){
            level += 1;
        }
        return level;
    }
    public static int boomLevel (Player player){
        int boomLevel = 4;
        if (Handler.has(player,InItems.ThunderDrum_.asItem())){
            boomLevel += 2;
        }
        if (Handler.has(player,InItems.DevilRinging_.asItem())){
            boomLevel += 2;
        }
        return boomLevel;
    }
}
