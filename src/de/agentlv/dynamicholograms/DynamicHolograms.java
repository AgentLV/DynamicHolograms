package de.agentlv.dynamicholograms;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.agentlv.dynamicholograms.listeners.PlayerMoveListener;
import de.agentlv.dynamicholograms.listeners.PlayerToggleSneakListener;
import de.agentlv.dynamicholograms.nms.NMSManager;
import de.agentlv.dynamicholograms.nms.NmsManagerImpl_v1_8_R1;
import de.agentlv.dynamicholograms.nms.NmsManagerImpl_v1_8_R2;
import de.agentlv.dynamicholograms.nms.NmsManagerImpl_v1_8_R3;

public final class DynamicHolograms extends JavaPlugin {
	
	//Config variables
	private static boolean API_ONLY;
	public static boolean PLAYER_HEAD;
	public static List<String> TEXT;
	
	private static NMSManager nmsManager;
	
	@Override
	public void onEnable() {
		
		//Config stuff
		this.saveDefaultConfig();
		API_ONLY = getConfig().getBoolean("API_ONLY", false);
		
		//Setup NMS and check if version is compatible
		if (!setupNMSManager()) {
			getLogger().severe("Failed to intialize NMS! SneakHolo supports 1.8 - 1.8.8!");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		//Listener
		if (!API_ONLY) {
			new PlayerMoveListener(this, nmsManager);
			new PlayerToggleSneakListener(this, nmsManager);
		}
	}
	
	private boolean setupNMSManager() {

        String version = getNMSVersion();

        if (version.equals("v1_8_R1")) {
        	nmsManager = new NmsManagerImpl_v1_8_R1();
        } else if (version.equals("v1_8_R2")) {
        	nmsManager = new NmsManagerImpl_v1_8_R2();
        } else if (version.equals("v1_8_R3")) {
        	nmsManager = new NmsManagerImpl_v1_8_R3();
        }
        return nmsManager != null;
    }
	
	public static NMSManager getNMSManager() {
		return nmsManager;
	}
	
	public static String getNMSVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
	}
	
}
