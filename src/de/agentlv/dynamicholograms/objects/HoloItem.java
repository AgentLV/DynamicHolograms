package de.agentlv.dynamicholograms.objects;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.nms.NMSHoloItem;

/**
 * A floating item
 */
public class HoloItem {

	private Object item;
	private Object armorStand;
	private Location location;
	private String itemName = "minecraft:";
	private int subId;
	private PlayerSkullData playerSkullData;
	private Set<Player> players = new HashSet<Player>();
	private NMSHoloItem nmsHoloItem = DynamicHolograms.getNMSHoloItem();
	
	/**
	 * 
	 * @param location
	 * @param itemName
	 * @param subId
	 */
	public HoloItem(Location location, String itemName, int subId) {
		this.itemName += itemName.toLowerCase();
		this.location = location;
		this.subId = subId;
		
		Object[] ob = nmsHoloItem.create(this);
		this.item = ob[0];
		this.armorStand = ob[1];
		
	}
	
	/**
	 * 
	 * @param location
	 * @param playerSkullData
	 */
	public HoloItem(Location location, PlayerSkullData playerSkullData) {
		this.itemName = "skull";
		this.subId = 3;
		this.location = location;
		this.playerSkullData = playerSkullData;
		
		Object[] ob = nmsHoloItem.create(this);
		this.item = ob[0];
		this.armorStand = ob[1];
		
	}
	
	/**
	 * Move the HoloItem
	 * @param location
	 * @return HoloItem
	 */
	public HoloItem move(Location location) {
		nmsHoloItem.move(this, location);
		this.location = location;
		
		return this;
	}
	
	/**
	 * Set the item for the HoloItem
	 * @param itemName
	 * @param subId
	 * @return HoloItem
	 */
	public HoloItem setItem(String itemName, int subId) {
		this.playerSkullData = null;
		this.itemName = "minecraft:" + itemName;
		this.subId = subId;
		this.item = nmsHoloItem.setItem(this, itemName, subId);
		
		return this;
	}
	
	/**
	 * Set the item to a player skull
	 * @param playerSkullData
	 * @return HoloItem
	 */
	public HoloItem setSkull(PlayerSkullData playerSkullData) {
		this.playerSkullData = playerSkullData;
		this.itemName = "skull";
		this.subId = 3;
		nmsHoloItem.setItem(this, this.itemName, this.subId);
		
		return this;
	}
	
	/**
	 * Gets the Item of the HoloItem
	 * @return the Item that belongs to the HoloItem 
	 */
	public Object getItem() {
		return this.item;
	}
	
	/**
	 * Gets the the ArmorStand which the Item is riding
	 * @return the ArmorStand that belongs to the HoloItem
	 */
	public Object getArmorStand() {
		return this.armorStand;
	}
	
	/**
	 * Gets the location of the HoloItem
	 * @return the location of the HoloItem
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Gets the name of the Item
	 * @return the name of the Item
	 */
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * Gets the subId of the Item
	 * @return the subId of the Item
	 */
	public int getSubId() {
		return this.subId;
	}
	
	/**
	 * Check if the HoloItem is a player skull
	 * @return True if the holoItem is a player skull
	 */
	public boolean isPlayerSkull() {
		return this.playerSkullData != null;
	}
	
	/**
	 * Gets the PlayerSkullData of the HoloItem
	 * @return PlayerSkullData of the HoloItem
	 */
	public PlayerSkullData getPlayerSkullData() {
		return this.playerSkullData;
	}

	/**
	 * Show the HoloItem to the specified player(s)
	 * @param player
	 * @return HoloItem
	 */
	public HoloItem show(Player... player) {
		
		for (Player p : player) {
			players.add(p);
			nmsHoloItem.showPlayer(this, p);
		}
		
		return this;
	}
	
	/**
	 * Hide the HoloItem to the specified player(s)
	 * @param player
	 */
	public HoloItem hide(Player... player) {
		
		for (Player p : player) {
			players.remove(p);
			nmsHoloItem.hidePlayer(this, p);
		}
		
		return this;
	}
	
	/**
	 * Gets the players that belong to the HoloItem
	 * @return
	 */
	public Set<Player> getPlayers() {
		return new HashSet<Player>(this.players);
	}
	
	/**
	 * Removes the HoloItem
	 */
	public void delete() {
		hide(this.getPlayers().toArray(new Player[this.getPlayers().size()]));
	}
	
	/**
	 * Checks if the player can see the HoloItem
	 * @param player
	 * @return True if the provided player can see the HoloItem
	 */
	public boolean canSee(Player player) {
		return this.players.contains(player);
	}
}
