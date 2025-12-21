package com.ytgld.chest_curio_items_add.event;

import com.ytgld.chest_curio_items_add.item.things.FissionEmblem;
import com.ytgld.chest_curio_items_add.item.things.days.LifeTree;
import com.ytgld.chest_curio_items_add.item.things.days.OldHatred;
import com.ytgld.chest_curio_items_add.item.things.days.TheRemnantsOfTheHunt;
import com.ytgld.chest_curio_items_add.item.things.weiyu.TheEctopicDivineBlood;
import com.ytgld.chest_curio_items_add.item.things.yilezi.Bell;
import com.ytgld.chest_curio_items_add.item.things.yilezi.DevilRinging;
import com.ytgld.chest_curio_items_add.item.things.yilezi.ThunderDrum;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

public class CIAEvent {
    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent.Pre evt){
        FissionEmblem.attack(evt);
        FissionEmblem.hurt(evt);
        FissionEmblem.scAttack(evt);
        OldHatred.attack(evt);
        ThunderDrum.attack(evt);
        DevilRinging.attack(evt);
        TheEctopicDivineBlood.hurt(evt);
    }
    @SubscribeEvent
    public void ItemStackTickEvent(ItemStackTickEvent evt){
        FissionEmblem.tick(evt);
        LifeTree.tick(evt);
        OldHatred.tick(evt);
        Bell.tick(evt);
        TheRemnantsOfTheHunt.tick(evt);
        TheRemnantsOfTheHunt.tickAttrib(evt);
        TheEctopicDivineBlood.tick(evt);
    }
    @SubscribeEvent
    public void LivingUseTotemEvent(LivingUseTotemEvent evt){
        TheEctopicDivineBlood.iLivingUseTotemEvent(evt);
    }
    @SubscribeEvent
    public void LivingDropsEvent(LivingDropsEvent evt){
        OldHatred.attack(evt);
    }
    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent evt){
        TheRemnantsOfTheHunt.attack(evt);

    }
}
