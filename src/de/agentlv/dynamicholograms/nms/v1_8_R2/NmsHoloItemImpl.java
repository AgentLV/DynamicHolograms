package de.agentlv.dynamicholograms.nms.v1_8_R2;

import net.minecraft.server.v1_8_R2.EntityArmorStand;
import net.minecraft.server.v1_8_R2.EntityItem;
import net.minecraft.server.v1_8_R2.GameProfileSerializer;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.MathHelper;
import net.minecraft.server.v1_8_R2.NBTTagCompound;
import net.minecraft.server.v1_8_R2.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R2.PlayerConnection;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.agentlv.dynamicholograms.nms.NMSHoloItem;
import de.agentlv.dynamicholograms.objects.HoloItem;
import de.agentlv.dynamicholograms.objects.PlayerSkullData;

public class NmsHoloItemImpl implements NMSHoloItem {
	
	@Override
	public Object[] create(HoloItem holoItem) {
		
		Location loc = holoItem.getLocation();
		EntityItem item = (EntityItem) setItem(holoItem, holoItem.getItemName(), holoItem.getSubId());
		
		EntityArmorStand as = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
		as.setInvisible(true);
		as.setCustomNameVisible(false);
		as.setSmall(true);
		as.setGravity(false);
		as.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		Object[] ob = {item, as};
		return ob;
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
		
		PacketPlayOutEntityDestroy itemPacket = new PacketPlayOutEntityDestroy(((EntityItem) holoItem.getItem()).getId());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacket);
		
		PacketPlayOutEntityDestroy armorStandPacket = new PacketPlayOutEntityDestroy(((EntityArmorStand) holoItem.getArmorStand()).getId());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(armorStandPacket);
		
	}
	
	@Override
	public void move(HoloItem holoItem, Location newLocation) {
		
		EntityArmorStand as = (EntityArmorStand) holoItem.getArmorStand();
		
		int newX = MathHelper.floor(newLocation.getX() * 32.0D);
		int newY = MathHelper.floor(newLocation.getY() * 32.0D);
		int newZ = MathHelper.floor(newLocation.getZ() * 32.0D);
     
		for (Player p : holoItem.getPlayers()) {
			
			PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(as.getId(), newX, newY, newZ, (
        			byte) newLocation.getYaw(), (byte) newLocation.getPitch(), false);
        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	        
		}
		
		as.setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ(), newLocation.getYaw(), newLocation.getPitch());
		
	}
	
	@Override
	public Object setItem(HoloItem holoItem, String itemName, int subId) {
		
		Location loc = holoItem.getLocation();
		ItemStack nmsStack = new ItemStack(Item.d(holoItem.getItemName()), 1, holoItem.getSubId());
		EntityItem item = new EntityItem(((CraftWorld) loc.getWorld()).getHandle());
		EntityArmorStand as = (EntityArmorStand) holoItem.getArmorStand();
		EntityItem oldItem = (EntityItem) holoItem.getItem();
		
		if (holoItem.isPlayerSkull()) {
			
			PlayerSkullData skullData = holoItem.getPlayerSkullData();
			
			nmsStack.setTag(new NBTTagCompound());
			NBTTagCompound skullOwnerTag = new NBTTagCompound();
			GameProfileSerializer.serialize(skullOwnerTag, new GameProfile(skullData.getUniqueId(), skullData.getPlayerName()));
			nmsStack.getTag().set("SkullOwner", skullOwnerTag);
		
		}
		
        item.setItemStack(nmsStack);
        item.setLocation(loc.getX(), loc.getY() + 1.5, loc.getZ(), loc.getYaw(), loc.getPitch());
		
        if (oldItem != null) {
			
			for (Player p : holoItem.getPlayers()) {
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(oldItem.getId());
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
        }
        
        if (holoItem.getArmorStand() != null) {
        	
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

        return item;
	}

}

