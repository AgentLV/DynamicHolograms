package de.agentlv.dynamicholograms.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.objects.Hologram;

public class PlayerMoveListener implements Listener {
	
	Hologram hologram;
	
	public PlayerMoveListener(DynamicHolograms plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
	
		Player p = e.getPlayer();
		
		if (PlayerToggleSneakListener.sneakMap.containsKey(p))
			PlayerToggleSneakListener.sneakMap.get(p).move(e.getTo().toVector().add(e.getTo().getDirection().multiply(3)).toLocation(e.getTo().getWorld()));
		
	}
}
