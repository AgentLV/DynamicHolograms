package de.agentlv.dynamicholograms.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.objects.HoloItem;
import de.agentlv.dynamicholograms.objects.Hologram;
import de.agentlv.dynamicholograms.objects.PlayerSkullData;

public class PlayerToggleSneakListener implements Listener {
	
	public static Map<Player, Hologram> sneakMap = new HashMap<Player, Hologram>();
	
	public PlayerToggleSneakListener(DynamicHolograms plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
	
		Player p = e.getPlayer();
		
		if (e.isSneaking()) {
			
			Location loc = p.getLocation().toVector().add(p.getLocation().getDirection().multiply(3)).toLocation(p.getWorld());
			
			sneakMap.put(p, new Hologram(loc, DynamicHolograms.DISTANCE, DynamicHolograms.TEXT.toArray(new String[DynamicHolograms.TEXT.size()]))
			.setItem(new HoloItem(loc, new PlayerSkullData(p.getUniqueId(), p.getName())).getItem()).show(p).addMessage("hi"));
			
		} else {
			sneakMap.get(p).delete();
			sneakMap.remove(p);
		}
	}	
}
