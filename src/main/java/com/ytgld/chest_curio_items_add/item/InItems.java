package com.ytgld.chest_curio_items_add.item;

import com.ytgld.chest_curio_items_add.ChestAddMod;
import com.ytgld.chest_curio_items_add.item.things.FissionEmblem;
import com.ytgld.chest_curio_items_add.item.things.days.LifeTree;
import com.ytgld.chest_curio_items_add.item.things.days.OldHatred;
import com.ytgld.chest_curio_items_add.item.things.days.TheRemnantsOfTheHunt;
import com.ytgld.chest_curio_items_add.item.things.weiyu.TheEctopicDivineBlood;
import com.ytgld.chest_curio_items_add.item.things.yilezi.Bell;
import com.ytgld.chest_curio_items_add.item.things.yilezi.DevilRinging;
import com.ytgld.chest_curio_items_add.item.things.yilezi.ThunderDrum;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class InItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChestAddMod.MODID);
    public static final DeferredItem<Item> FissionEmblem_ = register("fission_emblem", (resourceLocation) -> {
        return new FissionEmblem((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredItem<Item> LifeTree_ = register("life_tree", (resourceLocation) -> {
        return new LifeTree((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredItem<Item> OldHatred_ = register("old_hatred", (resourceLocation) -> {
        return new OldHatred((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredItem<Item> Bell_ = register("bell", (resourceLocation) -> {
        return new Bell((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredItem<Item> ThunderDrum_ = register("thunder_drum", (resourceLocation) -> {
        return new ThunderDrum((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredItem<Item> DevilRinging_ = register("devil_ringing", (resourceLocation) -> {
        return new DevilRinging((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredItem<Item> TheRemnantsOfTheHunt_ = register("the_remnants_of_the_hunt", (resourceLocation) -> {
        return new TheRemnantsOfTheHunt((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredItem<Item> TheEctopicDivineBlood_ = register("ectopic_blood", (resourceLocation) -> {
        return new TheEctopicDivineBlood((new Item.Properties()).stacksTo(1));
    });
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChestAddMod.MODID);;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> tab = CREATIVE_MODE_TABS.register(ChestAddMod.MODID, () -> {
        return CreativeModeTab.builder().title(Component.translatable("itemGroup.chest_curio_items_add"))
                .icon(Items.ENDER_CHEST::getDefaultInstance).displayItems((parameters, output) -> {

                    output.accept(OldHatred_);

                    output.accept(FissionEmblem_);
                    output.accept(LifeTree_);
                    output.accept(Bell_);
                    output.accept(ThunderDrum_);
                    output.accept(DevilRinging_);
                    output.accept(TheRemnantsOfTheHunt_);
                    output.accept(TheEctopicDivineBlood_);




        }).build();
    });


    public static DeferredItem<Item> register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }
}
