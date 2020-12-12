/*==============================================================================
 = Class: ItemPickaxeBase
 = This class is part of Metallurgy 4: Reforged
 = Complete source code is available at https://github.com/Davoleo/Metallurgy-4-Reforged
 = This code is licensed under GNU GPLv3
 = Authors: Davoleo, ItHurtsLikeHell, PierKnight100
 = Copyright (c) 2018-2020.
 =============================================================================*/

package it.hurts.metallurgy_reforged.item.tool;

import com.google.common.collect.Multimap;
import it.hurts.metallurgy_reforged.config.GeneralConfig;
import it.hurts.metallurgy_reforged.effect.BaseMetallurgyEffect;
import it.hurts.metallurgy_reforged.material.Metal;
import it.hurts.metallurgy_reforged.material.MetalStats;
import it.hurts.metallurgy_reforged.material.ModMetals;
import it.hurts.metallurgy_reforged.model.EnumTools;
import it.hurts.metallurgy_reforged.util.ItemUtils;
import it.hurts.metallurgy_reforged.util.MetallurgyTabs;
import it.hurts.metallurgy_reforged.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemPickaxeBase extends ItemPickaxe implements IToolEffect {

	private BaseMetallurgyEffect effect;
	private Enchantment enchantment = null;
	private int enchantmentLevel = -1;

	private final MetalStats metalStats;

	public ItemPickaxeBase(ToolMaterial material, MetalStats metalStats)
	{
		super(material);
		this.metalStats = metalStats;
		ItemUtils.initItem(this, metalStats.getName() + "_pickaxe", MetallurgyTabs.tabTool);
	}

	@Override
	public EnumTools getToolClass()
	{
		return EnumTools.PICKAXE;
	}

	@Override
	public MetalStats getMetalStats()
	{
		return metalStats;
	}

	@Override
	public void setEffect(BaseMetallurgyEffect effect)
	{
		this.effect = effect;
	}

	public void setEnchanted(Enchantment enchantment, int enchantmentLevel)
	{
		this.enchantment = enchantment;
		this.enchantmentLevel = enchantmentLevel;
	}

	@Override
	public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair)
	{
		Metal metal = ModMetals.metalMap.get(metalStats.getName());
		return (GeneralConfig.enableAnvilToolRepair && ItemUtils.equalsWildcard(new ItemStack(metal.getIngot()), repair))
				|| super.getIsRepairable(toRepair, repair);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (this.effect != null && effect.isEnabled()) {
            tooltip.add(this.effect.getTooltip().getLeft());

            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
                tooltip.add(this.effect.getTooltip().getRight());
            else
                tooltip.add(Utils.localize("tooltip.metallurgy.press_ctrl"));
        }
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
		{
			ItemStack enchantedPA = new ItemStack(this);
			if (enchantment != null)
			{
				enchantedPA.addEnchantment(enchantment, enchantmentLevel);
			}
			items.add(enchantedPA);
		}
	}

	@Nonnull
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		ItemUtils.setToolAttributes(equipmentSlot, multimap, metalStats);
		return multimap;
	}

}
