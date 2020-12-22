/*==============================================================================
 = Class: AmordrineWeaponEffect
 = This class is part of Metallurgy 4: Reforged
 = Complete source code is available at https://github.com/Davoleo/Metallurgy-4-Reforged
 = This code is licensed under GNU GPLv3
 = Authors: Davoleo, ItHurtsLikeHell, PierKnight100
 = Copyright (c) 2018-2020.
 =============================================================================*/

package it.hurts.metallurgy_reforged.effect.weapon;

import it.hurts.metallurgy_reforged.effect.BaseMetallurgyEffect;
import it.hurts.metallurgy_reforged.effect.EnumEffectCategory;
import it.hurts.metallurgy_reforged.handler.EventHandler;
import it.hurts.metallurgy_reforged.material.ModMetals;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public class AmordrineWeaponEffect extends BaseMetallurgyEffect {


    private EventHandler<LivingHurtEvent> ATTACK_MOB = new EventHandler<>(this::onMobAttacked, LivingHurtEvent.class);

    public AmordrineWeaponEffect() {
        super(ModMetals.AMORDRINE);
    }

    @Nonnull
    @Override
    public EnumEffectCategory getCategory() {
        return EnumEffectCategory.WEAPON;
    }

    @Override
    public EventHandler<? extends LivingEvent>[] getLivingEvents() {
        return new EventHandler[]{ATTACK_MOB};
    }

	/**
	 * in this case of LivingHurtEvent, the attacker should have the armor, not the mob which is attacked
	 */
	@Override
	public EntityLivingBase getEquipUserFromEvent(Event event)
	{
		if (event instanceof LivingHurtEvent)
		{
			LivingHurtEvent hurtEvent = (LivingHurtEvent) event;
			Entity attacker = hurtEvent.getSource().getImmediateSource();
			if (attacker instanceof EntityLivingBase)
			{

				return (EntityLivingBase) attacker;
			}
		}
		return super.getEquipUserFromEvent(event);
	}

	private void onMobAttacked(LivingHurtEvent event)
	{
		float percentage = 1F - event.getEntityLiving().getHealth() / event.getEntityLiving().getMaxHealth();
		float originalAMount = event.getAmount();
		event.setAmount(originalAMount + originalAMount * percentage * 2);
	}

}