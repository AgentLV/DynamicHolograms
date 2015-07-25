package de.agentlv.sneakholo.listeners;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.agentlv.sneakholo.PlayerHologramInfo;
import de.agentlv.sneakholo.SneakHolo;

public class PlayerToggleSneakListener implements Listener {
	
	public PlayerToggleSneakListener(SneakHolo plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
		
		Player p = e.getPlayer();
		Location loc = p.getLocation().toVector().add(p.getLocation().getDirection().multiply(3)).toLocation(p.getWorld());
		PlayerHologramInfo phl = null;
		EntityArmorStand as = null;
	
		if (e.isSneaking()) {			
			
			phl = new PlayerHologramInfo();
			
			as = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
			as.setInvisible(true);
			as.setCustomName(p.getName());
			as.setCustomNameVisible(true);
			as.setSmall(true);
			as.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
			
			phl.setArmorStand(as);
			
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(as);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			
			phl.setLocation(loc);
			PlayerMoveListener.players.put(p, phl);
			
		} else {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(PlayerMoveListener.players.get(p).getArmorStand().getId());
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			PlayerMoveListener.players.remove(p);
		}
		
	}
	
}
