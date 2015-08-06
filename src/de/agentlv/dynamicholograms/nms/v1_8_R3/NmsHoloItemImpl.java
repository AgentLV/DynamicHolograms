package de.agentlv.dynamicholograms.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
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

import com.mojang.authlib.GameProfile;

import de.agentlv.dynamicholograms.nms.NMSHoloItem;
import de.agentlv.dynamicholograms.objects.HoloItem;
import de.agentlv.dynamicholograms.objects.PlayerSkullData;

public class NmsHoloItemImpl implements NMSHoloItem {
	
	@Override
	public void create(HoloItem holoItem) {
		
		Location loc = holoItem.getLocation();
		ItemStack nmsStack;
		
		EntityArmorStand as = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
		as.setInvisible(true);
		as.setCustomNameVisible(false);
		as.setSmall(true);
		as.setGravity(false);
		as.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		holoItem.setArmorStand(as);
		
		if (holoItem.isPlayerSkull()) {
			
			PlayerSkullData skullData = holoItem.getPlayerSkullData();
			
			nmsStack = new ItemStack(Items.SKULL, 1, 3);
			nmsStack.setTag(new NBTTagCompound());
			NBTTagCompound skullOwnerTag = new NBTTagCompound();
			GameProfileSerializer.serialize(skullOwnerTag, new GameProfile(skullData.getUniqueId(), skullData.getPlayerName()));
			nmsStack.getTag().set("SkullOwner", skullOwnerTag);
		
		} else {
			nmsStack = new ItemStack(Item.d(holoItem.getItemName()));
		}
		
		EntityItem item = new EntityItem(((CraftWorld) loc.getWorld()).getHandle());
        item.setItemStack(nmsStack);
        item.setLocation(loc.getX(), loc.getY() + 1.5, loc.getZ(), loc.getYaw(), loc.getPitch());
			
        holoItem.setItem(item);
        
		for (Player p : holoItem.getPlayers()) {
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
	public void showPlayer(HoloItem holoItem, Player player) {
		
		EntityItem item = (EntityItem) holoItem.getItem();
			
		PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
		
		PacketPlayOutSpawnEntityLiving armorStandPacket = new PacketPlayOutSpawnEntityLiving((EntityArmorStand) holoItem.getArmorStand());
		playerConnection.sendPacket(armorStandPacket);
		
		PacketPlayOutSpawnEntity itemPacket = new PacketPlayOutSpawnEntity(item, 2, 0);
		playerConnection.sendPacket(itemPacket);
		
		PacketPlayOutEntityMetadata itemMetaDataPacket = new PacketPlayOutEntityMetadata(item.getId(), item.getDataWatcher(), true);
		playerConnection.sendPacket(itemMetaDataPacket);
		
		PacketPlayOutAttachEntity attachEntityPacket = new PacketPlayOutAttachEntity(0, item, (EntityArmorStand) holoItem.getArmorStand());
		playerConnection.sendPacket(attachEntityPacket);
		
	}
	
	@Override
	public void hidePlayer(HoloItem holoItem, Player player) {
		PacketPlayOutEntityDestroy armorStandPacket = new PacketPlayOutEntityDestroy(((EntityArmorStand) holoItem.getArmorStand()).getId());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(armorStandPacket);
		
		PacketPlayOutEntityDestroy itemPacket = new PacketPlayOutEntityDestroy(((EntityItem) holoItem.getItem()).getId());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacket);
	}
	
	@Override
	public void move(HoloItem holoItem, Location newLocation) {
		
		Location oldLocation = holoItem.getLocation();
		
		int oldX = MathHelper.floor(oldLocation.getX() * 32.0D);
        int oldY = MathHelper.floor(oldLocation.getY() * 32.0D);
        int oldZ = MathHelper.floor(oldLocation.getZ() * 32.0D);
		
		int newX = MathHelper.floor(newLocation.getX() * 32.0D);
        int newY = MathHelper.floor(newLocation.getY() * 32.0D);
        int newZ = MathHelper.floor(newLocation.getZ() * 32.0D);
        
        int dx = newX - oldX;
        int dy = newY - oldY;
        int dz = newZ - oldZ;
        
        if (dx >= -128 && dx < 128 && dy >= -128 && dy < 128 && dz >= -128 && dz < 128) {
           
        	for (Player p : holoItem.getPlayers()) {
	        	PacketPlayOutRelEntityMove packet = new PacketPlayOutRelEntityMove(((EntityArmorStand) holoItem.getArmorStand()).getId(), (byte) dx, (byte) dy, 
						(byte) dz, false);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        	}
        	
        } else {
        	
        	for (Player p : holoItem.getPlayers()) {
	        	PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(((EntityArmorStand) holoItem.getArmorStand()).getId(), newX, newY, newZ, 
	        			(byte) newLocation.getYaw(), (byte) newLocation.getPitch(), false);
	        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        	}   	
    	}
        
        holoItem.setLocation(newLocation);
	}
	
	@Override
	public void remove(HoloItem holoItem) {
		
		for (Player p : holoItem.getPlayers()) {
			PacketPlayOutEntityDestroy armorStandPacket = new PacketPlayOutEntityDestroy(((EntityArmorStand) holoItem.getArmorStand()).getId());
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(armorStandPacket);
			
			PacketPlayOutEntityDestroy itemPacket = new PacketPlayOutEntityDestroy(((EntityItem) holoItem.getItem()).getId());
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(itemPacket);
		}
	}

}
