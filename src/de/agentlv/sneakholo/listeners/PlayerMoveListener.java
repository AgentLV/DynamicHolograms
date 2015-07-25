package de.agentlv.sneakholo.listeners;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.agentlv.sneakholo.PlayerHologramInfo;
import de.agentlv.sneakholo.SneakHolo;

public class PlayerMoveListener implements Listener {
	
	public static Map<Player, PlayerHologramInfo> players = new HashMap<Player, PlayerHologramInfo>();
	
	public PlayerMoveListener(SneakHolo plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if (players.containsKey(p)) {
		
			PlayerHologramInfo phl = players.get(p);
			Location loc = p.getLocation().toVector().add(p.getLocation().getDirection().multiply(3)).toLocation(p.getWorld());
			
			int oldX = MathHelper.floor(phl.getLocation().getX() * 32.0D);
            int oldY = MathHelper.floor(phl.getLocation().getY() * 32.0D);
            int oldZ = MathHelper.floor(phl.getLocation().getZ() * 32.0D);
			
			int newX = MathHelper.floor(loc.getX() * 32.0D);
            int newY = MathHelper.floor(loc.getY() * 32.0D);
            int newZ = MathHelper.floor(loc.getZ() * 32.0D);
            
            int dx = newX - oldX;
            int dy = newY - oldY;
            int dz = newZ - oldZ;
            
            if (dx >= -128 && dx < 128 && dy >= -128 && dy < 128 && dz >= -128 && dz < 128) {
               
				PacketPlayOutRelEntityMove packet = new PacketPlayOutRelEntityMove(phl.getArmorStand().getId(), (byte) dx, (byte) dy, 
						(byte) dz, false);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			
            } else {
            	
            	PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(phl.getArmorStand().getId(), newX, newY, newZ, (
            			byte) loc.getYaw(), (byte) loc.getPitch(), false);
            	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
            
            phl.setLocation(loc);
			players.put(p, phl);
		}
	}
}
