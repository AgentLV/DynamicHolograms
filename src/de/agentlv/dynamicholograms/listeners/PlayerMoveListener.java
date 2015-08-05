package de.agentlv.dynamicholograms.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.nms.NMSManager;
import de.agentlv.dynamicholograms.objects.Hologram;

public class PlayerMoveListener implements Listener {
	
	Hologram hologram;
	
	public PlayerMoveListener(DynamicHolograms plugin, NMSManager nmsManager) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
	
		//Player p = e.getPlayer();
		
		/*if (hologram != null) {
			hologram.setLocation(e.getTo());
		} else {
			hologram = new Hologram(p.getLocation(), "§elel");
			hologram.show(p);
		} */
	}
}
