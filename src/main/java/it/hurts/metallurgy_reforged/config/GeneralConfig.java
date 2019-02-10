package it.hurts.metallurgy_reforged.config;

import it.hurts.metallurgy_reforged.Metallurgy;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*************************************************
 * Author: Davoleo
 * Date / Hour: 06/12/2018 / 21:30
 * Class: GeneralConfig
 * Project: Metallurgy 4 Reforged
 * Copyright - � - Davoleo - 2018
 **************************************************/

@Config.LangKey("config.metallurgy.category.general")
@Config(modid = Metallurgy.MODID, name = "metallurgy_reforged/general")
public class GeneralConfig {
	
	@Config.Name("Enable Alpha Warning Message")
	@Config.Comment("When set to true it shows the alpha warning message when joining the world")
	@Config.RequiresMcRestart
	public static boolean alphaWarning = true;
	
	@Config.Name("Enable/Disable all tools")
	@Config.Comment("When set to false it disable all tool")
	@Config.RequiresMcRestart
	public static boolean disableAllTool = true;
	
	@Config.Name("Enable/Disable all armor set")
	@Config.Comment("When set to false it disable all armor set")
	@Config.RequiresMcRestart
	public static boolean disableAllArmor = true;
	
	@Config.Name("Road Speed")
	@Config.Comment("Set the road speed")
	public static double roadSpeed = 1.80D;
	
	@Config.Name("Set the value of common Rarity")
	@Config.Comment("Don't change if you aren't a dev")
	@Config.RequiresMcRestart
	public static int commonRarity = 15;
	
	@Config.Name("Set the value of uncommon Rarity")
	@Config.Comment("Don't change if you aren't a dev")
	@Config.RequiresMcRestart
	public static int uncommonRarity = 10;
	
	@Config.Name("Set the value of rare Rarity")
	@Config.Comment("Don't change if you aren't a dev")
	@Config.RequiresMcRestart
	public static int rareRarity = 5;
	
	@Config.Name("Set the value of ultra rare Rarity")
	@Config.Comment("Don't change if you aren't a dev")
	@Config.RequiresMcRestart
	public static int ultraRareRarity = 2;

	public static class ChangeListener {
		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
			if(eventArgs.getModID().equals(Metallurgy.MODID)) {
				ConfigManager.sync(Metallurgy.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
