package de.agentlv.dynamicholograms.objects;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.nms.NMSHoloItem;

public class HoloItem {

	private Object item;
	private Object armorStand;
	private Location location;
	private String itemName = "minecraft:";
	private PlayerSkullData playerSkullData;
	private Set<Player> players = new HashSet<Player>();
	private NMSHoloItem nmsHoloItem = DynamicHolograms.getNMSHoloItem();
	
	public HoloItem() {
		
	}
	
	public HoloItem(String itemName, Location location) {
		this.itemName += itemName.toLowerCase();
		this.location = location;
		this.item = nmsHoloItem.create(this);
	}
	
	public HoloItem(Location location, PlayerSkullData playerSkullData) {
		this.itemName = "skull";
		this.location = location;
		this.playerSkullData = playerSkullData;
		this.item = nmsHoloItem.create(this);
	}
	
	public void setArmorStand(Object armorStand) {
		this.armorStand = armorStand;
	}
	
	public HoloItem setLocation(Location location) {
		this.location = location;
		
		return this;
	}
	
	public HoloItem setItemName(String itemName) {
		this.itemName = "minecraft:" + itemName;
		
		return this;
	}
	
	public HoloItem setPlayerSkullData(PlayerSkullData playerSkullData) {
		this.playerSkullData = playerSkullData;
		
		return this;
	}
	
	public Object getItem() {
		return this.item;
	}
	
	public Object getArmorStand() {
		return this.armorStand;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public boolean isPlayerSkull() {
		return this.playerSkullData != null;
	}
	
	public PlayerSkullData getPlayerSkullData() {
		return this.playerSkullData;
	}

	public void show(Player... player) {
		
		for (Player p : player)
			players.add(p);
		
	}
	
	public void hide(Player... player) {
		
		for (Player p : player) {
			players.remove(p);
		}
	}
	
	public Set<Player> getPlayers() {
		return new HashSet<Player>(this.players);
	}
	
	public void delete() {
		
		
	}
}
