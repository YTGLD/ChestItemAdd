package com.ytgld.chest_curio_items_add.item.things.days;

import com.ytgld.chest_curio_items_add.ChestAddMod;
import com.ytgld.chest_curio_items_add.item.InItems;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.items.black.TheSoul;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.List;

/**
 * 群友：Days
 * <p>
 * <p>
 * 旧仇恨
 * <p>
 * <p>
 *以你为敌的怪物每隔3秒受到一次凋零伤害
 * <p>
 * <p>
 * 攻击你的生物会失明15秒，同时你获得力量2 15秒
 * <p>
 * 玩家当前生命值越低，吸血能力越强
 * <p>
 * 你杀死的生物概率掉落铜/铁/金
 */
public class OldHatred extends TheImprintOfTheSoul {
    public OldHatred(Properties properties) {
        super(properties);
    }
    public static void attack(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory !=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); ++i) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InItems.OldHatred_)) {
                            if (event.getSource().getEntity() instanceof LivingEntity living) {
                                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,15*20,1),player);
                                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,15*20,1));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void attack(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory !=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); ++i) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InItems.OldHatred_)) {
                            if (Mth.nextInt(RandomSource.create(),1,100) < 30) {
                                event.getDrops().add(new ItemEntity(player.level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                        new ItemStack(Items.COPPER_INGOT)));
                                break;
                            }
                            if (Mth.nextInt(RandomSource.create(),1,100) < 20) {
                                event.getDrops().add(new ItemEntity(player.level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                        new ItemStack(Items.IRON_INGOT)));
                                break;
                            }
                            if (Mth.nextInt(RandomSource.create(),1,100) < 10) {
                                event.getDrops().add(new ItemEntity(player.level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                        new ItemStack(Items.GOLD_INGOT)));
                                break;
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
           s: for(int i = 0; i < chestInventory.getContainerSize(); ++i) {
                ItemStack stack = chestInventory.getItem(i);
                if (player.tickCount % 60    == 1) {
                    if (stack.is(InItems.OldHatred_)) {
                        Vec3 playerPos = player.position();
                        int range = 8;
                        List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - (double) range,
                                playerPos.y - (double) range, playerPos.z - (double) range,
                                playerPos.x + (double) range, playerPos.y + (double) range,
                                playerPos.z + (double) range));

                        for (LivingEntity living : entities) {
                            if (!living.is(player)) {
                                if (living.isAlive()) {
                                    if (living instanceof Targeting targeting) {
                                        LivingEntity entity = targeting.getTarget();
                                        if (entity != null) {
                                            if (entity.is(player)) {
                                                living.hurt(living.damageSources().wither(),5);
                                                break s;
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
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.old_hatred.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(-2140513587))));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.old_hatred.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(-2140513587))));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.old_hatred.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(-2140513587))));
            tooltipComponents.add(Component.translatable("item.chest_curio_items_add.old_hatred.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(-2140513587))));
            tooltipComponents.add(Component.literal(""));
        }else  {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.YELLOW));
        }
    }

    @Override
    public ResourceLocation resourceLocation() {
        return ResourceLocation.fromNamespaceAndPath(ChestAddMod.MODID,"textures/soul/old_hatred.png");
    }

    @Override
    public boolean canRemove(ItemStack stack) {
        return true;
    }

    @Override
    public int soulColor() {
        float a = EventMain.time;
        return Light.ARGB.color(255,255, (int) (50), (int) (50  ));
    }
}
