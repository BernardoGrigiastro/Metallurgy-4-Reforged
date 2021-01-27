/*==============================================================================
 = Class: AstralSilverWeaponEffect
 = This class is part of Metallurgy 4: Reforged
 = Complete source code is available at https://github.com/Davoleo/Metallurgy-4-Reforged
 = This code is licensed under GNU GPLv3
 = Authors: Davoleo, ItHurtsLikeHell, PierKnight100
 = Copyright (c) 2018-2020.
 =============================================================================*/

package it.hurts.metallurgy_reforged.effect.weapon;

import it.hurts.metallurgy_reforged.effect.BaseMetallurgyEffect;
import it.hurts.metallurgy_reforged.effect.EnumEffectCategory;
import it.hurts.metallurgy_reforged.material.ModMetals;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class AstralSilverWeaponEffect extends BaseMetallurgyEffect {

    public AstralSilverWeaponEffect()
    {
        super(ModMetals.ASTRAL_SILVER);
    }

    @Nonnull
    @Override
    public EnumEffectCategory getCategory()
    {
        return EnumEffectCategory.WEAPON;
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

    @SubscribeEvent
    public void onMobAttacked(LivingHurtEvent event)
    {
        if (!canBeApplied(getEquipUserFromEvent(event)))
            return;

        float originalAMount = event.getAmount();
        World world = event.getEntity().world;
        if (world.provider.getDimension() != 0)
        {
            event.setAmount(originalAMount * 1.45F);

            if (!world.isRemote)
                for (int i = 0; i < 10; i++)
                    spawnParticle(event.getEntity(), 1.5f, 10);
        }
    }

}