package de.agentlv.dynamicholograms.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.nms.NMSManager;
import de.agentlv.dynamicholograms.objects.HoloItem;
import de.agentlv.dynamicholograms.objects.Hologram;
import de.agentlv.dynamicholograms.objects.PlayerSkullData;

public class PlayerToggleSneakListener implements Listener {
	
	public static Hologram hologram = null;
	//HoloItem holoItem = null;
	
	public PlayerToggleSneakListener(DynamicHolograms plugin, NMSManager nmsManager) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
	
		Player p = e.getPlayer();
		
		if (hologram == null) {
			hologram = new Hologram(p.getLocation(), "§aTEST");
			hologram.show(p);
		}
		
		if (!e.isSneaking()) {
			hologram.setHoloItem(new HoloItem("skullitem", hologram.getLocation()).setPlayerSkullData(new PlayerSkullData(p.getUniqueId(), p.getName())));
		} else {
			hologram.setHoloItem(new HoloItem("apple", hologram.getLocation()));
		}
	}	
}
