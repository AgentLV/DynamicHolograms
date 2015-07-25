package de.agentlv.sneakholo;

import net.minecraft.server.v1_8_R3.EntityArmorStand;

import org.bukkit.Location;

public class PlayerHologramInfo {

	private Location location;
	private EntityArmorStand armorStand;
	
	public Location getLocation() {
		return location;
	}
	
	public EntityArmorStand getArmorStand() {
		return armorStand;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setArmorStand(EntityArmorStand armorStand) {
		this.armorStand = armorStand;
	}
 }
