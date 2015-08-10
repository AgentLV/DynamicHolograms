package de.agentlv.dynamicholograms.nms.v1_8_R3;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.nms.NMSHologram;
import de.agentlv.dynamicholograms.objects.Hologram;

public class NmsHologramImpl implements NMSHologram {
	
	@Override
	public Object create(Hologram hologram) {
		
		EntityArmorStand as = (EntityArmorStand) addMessage(hologram, hologram.getMessage(0));
			
		if (hologram.hasItem())
			setItem(hologram, hologram.getItem());
		
		return as;
	}
	
	@Override
	public void showPlayer(Hologram hologram, Player player) {
		
		PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
		
		for (Object as : hologram.getArmorStands()) {
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityArmorStand) as);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
		
		if (hologram.hasItem()) {
			
			EntityItem item = (EntityItem) hologram.getItem();
			PacketPlayOutSpawnEntity itemPacket = new PacketPlayOutSpawnEntity(item, 2, 0);
			playerConnection.sendPacket(itemPacket);
			
			PacketPlayOutEntityMetadata itemMetaDataPacket = new PacketPlayOutEntityMetadata(item.getId(), item.getDataWatcher(), true);
			playerConnection.sendPacket(itemMetaDataPacket);
			
			PacketPlayOutAttachEntity attachEntityPacket = new PacketPlayOutAttachEntity(0, item, (EntityArmorStand) hologram.getArmorStand(0));
			playerConnection.sendPacket(attachEntityPacket);
		}
			
	}
	
	@Override
	public void hidePlayer(Hologram hologram, Player player) {
		
		PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
		
		if (hologram.hasItem()) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(((EntityItem) hologram.getItem()).getId());
			playerConnection.sendPacket(packet);
		}
		
		for (Object as : hologram.getArmorStands()) {
    		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(((EntityArmorStand) as).getId());
    		playerConnection.sendPacket(packet);
    	}
    	
	}
	
	@Override
	public void move(Hologram hologram, Location newLocation) {
		
		List<EntityArmorStand> armorStands = new ArrayList<EntityArmorStand>();
		
		for (Object as : hologram.getArmorStands())
			armorStands.add((EntityArmorStand) as);
		
		int newX = MathHelper.floor(newLocation.getX() * 32.0D);
        int newZ = MathHelper.floor(newLocation.getZ() * 32.0D);
        
    	for (Player p : hologram.getPlayers()) {
        	for (int i = 0; i < armorStands.size(); ++i) {
	        		
        		EntityArmorStand as = armorStands.get(i);
        		double distance = i * hologram.getDistance();
        		int newY = MathHelper.floor((newLocation.getY() - distance) * 32.0D);
        		
	        	PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(as.getId(), newX, newY, newZ, (
	        			byte) newLocation.getYaw(), (byte) newLocation.getPitch(), false);
	        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		        
        		as.setLocation(newLocation.getX(), newLocation.getY() - distance, newLocation.getZ(),
        				newLocation.getYaw(), newLocation.getPitch());
        		
        	}
        }
	}
	
	@Override
	public Object addMessage(Hologram hologram, String message) {
		
		EntityArmorStand lastAs = hologram.getArmorStands().size() == 0 ? null : (EntityArmorStand) hologram.getArmorStand(hologram.getArmorStands().size() - 1);
		EntityArmorStand as;
		
		if (lastAs != null) {
			as = new EntityArmorStand(lastAs.world);
			as.setInvisible(true);
			as.setCustomName(message);
			as.setCustomNameVisible(true);
			as.setSmall(true);
			as.setGravity(false);
			as.setLocation(lastAs.locX, lastAs.locY - hologram.getDistance(), lastAs.locZ, lastAs.yaw, lastAs.pitch);
			
		} else {
			Location loc = hologram.getLocation();
	
			as = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
			as.setInvisible(true);
			as.setCustomName(message);
			as.setCustomNameVisible(true);
			as.setSmall(true);
			as.setGravity(false);
			as.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		}
		
		for (Player p : hologram.getPlayers()) {
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(as);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		
		return as;
	}
	
	@Override
	public Object setMessage(Hologram hologram, int index, String message) {
		
		if (index >= hologram.getArmorStands().size())
			throw new IndexOutOfBoundsException();
		
		EntityArmorStand as = (EntityArmorStand) hologram.getArmorStands().get(index);
		
		as.setCustomName(message);
		
		for (Player p : hologram.getPlayers()) {
			PacketPlayOutEntityMetadata itemMetaDataPacket = new PacketPlayOutEntityMetadata(as.getId(), as.getDataWatcher(), true);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(itemMetaDataPacket);
		}
		return as;
	}
	
	@Override
	public void removeMessage(Hologram hologram, int index) {
		
		EntityArmorStand removeArmorStand = (EntityArmorStand) hologram.getArmorStands().get(index);
		List<Double> pos = new ArrayList<Double>();
		
		for (Player p : hologram.getPlayers()) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(removeArmorStand.getId());
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}

		for (int i = index + 1; i < hologram.getArmorStands().size(); ++i) {
			EntityArmorStand as = (EntityArmorStand) hologram.getArmorStands().get(i);
			EntityArmorStand oldAs = (EntityArmorStand) hologram.getArmorStands().get(i - 1);
			int dy = MathHelper.floor(oldAs.locY * 32.0D) - MathHelper.floor(as.locY * 32.0D);
			
			for (Player p : hologram.getPlayers()) {
				
				if (dy >= -128 && dy < 128) {
				
		        	PacketPlayOutRelEntityMove packet = new PacketPlayOutRelEntityMove(as.getId(), (byte) 0, (byte) dy, 
							(byte) 0, false);
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					
				} else {
					
					PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(as.getId(), 
							MathHelper.floor(oldAs.locX * 32.0D), MathHelper.floor(oldAs.locY * 32.0D), 
							MathHelper.floor(oldAs.locZ * 32.0D), (byte) as.yaw, (byte) as.pitch, false);
					
		        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					
				}
        	}
			pos.add(oldAs.locY);
		}
		
		//Restore the correct positions
		int j = 0;
		for (int i = index + 1; i < hologram.getArmorStands().size(); ++i) {
			EntityArmorStand as = (EntityArmorStand) hologram.getArmorStands().get(i);
			as.setLocation(as.locX, pos.get(j), as.locZ, as.yaw, as.pitch);
			++j;
		}
		
	}
	
	@Override
	public void setItem(Hologram hologram, Object rawitem) {
		
		if (!(rawitem instanceof EntityItem))
			throw new IllegalStateException("The item has to be a instance of EntityItem!");
		
		EntityItem item = (EntityItem) rawitem;
		EntityArmorStand as = (EntityArmorStand) hologram.getArmorStand(0);
		
        if (hologram.hasItem()) {
	    	EntityItem oldItem = (EntityItem) hologram.getItem();
			
			for (Player p : hologram.getPlayers()) {
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(oldItem.getId());
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
        }
        
		for (Player p : hologram.getPlayers()) {
			PlayerConnection playerConnection = ((CraftPlayer) p).getHandle().playerConnection;
			
			PacketPlayOutSpawnEntity itemPacket = new PacketPlayOutSpawnEntity(item, 2, 0);
			playerConnection.sendPacket(itemPacket);
			
			PacketPlayOutEntityMetadata itemMetaDataPacket = new PacketPlayOutEntityMetadata(item.getId(), item.getDataWatcher(), true);
			playerConnection.sendPacket(itemMetaDataPacket);
			
			PacketPlayOutAttachEntity attachEntityPacket = new PacketPlayOutAttachEntity(0, item, as);
			playerConnection.sendPacket(attachEntityPacket);
			
		}
		
	}
	
	@Override
	public void removeItem(Hologram hologram) {
		
		if (hologram.hasItem()) {
			
			for (Player p : hologram.getPlayers()) {
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(((EntityItem) hologram.getItem()).getId());
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
		}
		
	}
	
	@Override
	public void setDistance(Hologram hologram, double distance) {
		
		List<EntityArmorStand> armorStands = new ArrayList<EntityArmorStand>();
		Location loc = hologram.getLocation();
		
		for (Object as : hologram.getArmorStands())
			armorStands.add((EntityArmorStand) as);
			
    	for (Player p : hologram.getPlayers()) {
    		
        	for (int i = 1; i < armorStands.size(); ++i) {
        		
        		EntityArmorStand beforeAs = armorStands.get(i - 1);
        		EntityArmorStand as = armorStands.get(i);
        	
    			int newX = MathHelper.floor(loc.getX() * 32.0D);
    	        int newY = MathHelper.floor((beforeAs.locY - distance) * 32.0D);
    	        int newZ = MathHelper.floor(loc.getZ() * 32.0D);
    		
	        	PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(as.getId(), newX, newY, newZ, (
	        			byte) as.yaw, (byte) as.pitch, false);
	        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        		
	        	as.setLocation(as.locX, beforeAs.locY - distance, as.locZ, as.yaw, as.pitch);
        	}
    	}
		
	}

}
