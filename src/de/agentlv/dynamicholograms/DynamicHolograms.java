package de.agentlv.dynamicholograms;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.agentlv.dynamicholograms.listeners.PlayerMoveListener;
import de.agentlv.dynamicholograms.listeners.PlayerToggleSneakListener;
import de.agentlv.dynamicholograms.nms.NMSHoloItem;
import de.agentlv.dynamicholograms.nms.NMSHologram;

public final class DynamicHolograms extends JavaPlugin {
	
	//Config variables
	private static boolean API_ONLY;
	public static boolean PLAYER_HEAD;
	public static List<String> TEXT;
	
	private static NMSHologram nmsHologram;
	private static NMSHoloItem nmsHoloItem;
	
	@Override
	public void onEnable() {
		
		//Config stuff
		this.saveDefaultConfig();
		API_ONLY = getConfig().getBoolean("API_ONLY", false);
		
		//Setup NMS and check if version is compatible
		if (!setupNMS()) {
			getLogger().severe("Failed to intialize NMS! DynamicHolograms supports 1.8 - 1.8.8!");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		//Listener
		if (!API_ONLY) {
			new PlayerMoveListener(this);
			new PlayerToggleSneakListener(this);
		}
	}
	
	private boolean setupNMS() {

        String version = getNMSVersion();

        if (version.equals("v1_8_R1")) {
        	nmsHologram = new de.agentlv.dynamicholograms.nms.v1_8_R1.NmsHologramImpl();
        	nmsHoloItem = new de.agentlv.dynamicholograms.nms.v1_8_R1.NmsHoloItemImpl();
        } else if (version.equals("v1_8_R2")) {
        	nmsHologram = new de.agentlv.dynamicholograms.nms.v1_8_R2.NmsHologramImpl();
        	nmsHoloItem = new de.agentlv.dynamicholograms.nms.v1_8_R2.NmsHoloItemImpl();
        } else if (version.equals("v1_8_R3")) {
        	nmsHologram = new de.agentlv.dynamicholograms.nms.v1_8_R3.NmsHologramImpl();
        	nmsHoloItem = new de.agentlv.dynamicholograms.nms.v1_8_R3.NmsHoloItemImpl();
        }
        return nmsHologram != null && nmsHoloItem != null;
    }
	
	public static NMSHologram getNMSHologram() {
		return nmsHologram;
	}
	
	public static NMSHoloItem getNMSHoloItem() {
		return nmsHoloItem;
	}
	
	public static String getNMSVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
	}
	
}
