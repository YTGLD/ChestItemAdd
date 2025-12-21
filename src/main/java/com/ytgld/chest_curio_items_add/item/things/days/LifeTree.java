package com.ytgld.chest_curio_items_add.item.things.days;

import com.ytgld.chest_curio_items_add.item.InItems;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.condensebone.ItemBone;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 群友：Days
 * <p>
 * <p>
 * 生命之树
 * <p>
 * <p>
 *持续吸周围敌对生物血液
 * <p>
 * <p>
 * 玩家生命值上限越高，吸取的血液的越多
 * <p>
 * 玩家当前生命值越低，吸血能力越强
 * <p>
 * <p>
 * 对友好生物则是回血而并非是吸取
 */
public class LifeTree extends ItemBone {
    public LifeTree(Properties properties) {
        super(properties);
    }

    public static void tick(ItemStackTickEvent event) {
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for(int i = 0; i < chestInventory.getContainerSize(); ++i) {
                ItemStack stack = chestInventory.getItem(i);
                if (player.tickCount % 20 == 1) {
                    if (stack.is(InItems.LifeTree_)) {
                        Vec3 playerPos = player.position();
                        int range = 8;
                        List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - (double) range,
                                playerPos.y - (double) range, playerPos.z - (double) range,
                                playerPos.x + (double) range, playerPos.y + (double) range,
                                playerPos.z + (double) range));
                        float modify = player.getMaxHealth() * 0.05f;
                        float health = player.getHealth();
                        float maxHealth = player.getMaxHealth();
                        float s = 1 + (1 -  health / maxHealth);
                        modify *= s;
                        for (LivingEntity living : entities) {
                            if (!living.is(player)) {
                                if (living.isAlive()) {
                                    if (living instanceof Targeting targeting) {
                                        LivingEntity entity = targeting.getTarget();
                                        if (entity != null) {
                                            if (entity.is(player)) {
                                                if (living.getMaxHealth() > 1 + modify) {
                                                    living.hurt(living.damageSources().genericKill(), modify);
                                                    player.heal(modify / entities.size());
                                                }
                                            } else {
                                                if (!entity.is(player)) {
                                                    living.heal(modify / entities.size());
                                                }
                                            }
                                        }
                                    }
                                    if (living instanceof OwnableEntity ownableEntity) {
                                        LivingEntity entity = ownableEntity.getOwner();
                                        if (entity != null) {
                                            if (entity.is(player)) {
                                                float h = modify / entities.size();
                                                if (h < 1) {
                                                    h = 1;
                                                }
                                                living.heal(h);
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
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        
        tooltipComponents.add(Component.translatable("item.chest_curio_items_add.owner")
                .append(Component.literal("“Days”"))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltipComponents.add(Component.literal(""));
        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.life_tree.string.1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.life_tree.string.2").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.life_tree.string.3").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.life_tree.string.4").withStyle(ChatFormatting.GOLD));
        }else  {
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.life_tree.string.0")
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xff804d))));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.YELLOW));
        }
    }
}
