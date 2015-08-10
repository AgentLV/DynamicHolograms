package de.agentlv.dynamicholograms.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.nms.NMSHologram;

/**
 * A hologram that can be dynamically changed
 */
public class Hologram {

	private List<Object> armorStands = new ArrayList<Object>();
	private List<String> message = new ArrayList<String>();
	private Object item;
	private Location location;
	private double distance = 0.28;
	private Set<Player> players = new HashSet<Player>();
	private NMSHologram nmsHologram = DynamicHolograms.getNMSHologram();
	
	/**
	 * 
	 * @param location
	 * @param message
	 */
	public Hologram(Location location, String... message) {
		this(location, 0.28, message);
	}
	
	/**
	 * 
	 * @param location
	 * @param distance
	 * @param message
	 */
	public Hologram(Location location, double distance, String... message) {
		this.location = location;
		this.message.add(message[0]);
		this.armorStands.add(nmsHologram.create(this));
		this.distance = distance;
		
		for (int i = 1; i < message.length; ++i)
			addMessage(message[i]);
	}
	
	/**
	 * Add a message(line) to the hologram
	 * @param message
	 * @return Hologram
	 */
	public Hologram addMessage(String message) {	
		this.message.add(message);
		this.armorStands.add(nmsHologram.addMessage(this, message));
		
		return this;
	}
	
	/**
	 * Replace the message at the specified index
	 * @param index
	 * @param message
	 * @return Hologram
	 */
	public Hologram setMessage(int index, String message) {
		
		if (index >= this.getArmorStands().size())
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.getArmorStands().size());
		
		this.message.set(index, message);
		this.armorStands.set(index, nmsHologram.setMessage(this, index, message));
		
		return this;
	}
	
	/**
	 * Remove a message(line) a the specified index
	 * @param index
	 * @return Hologram
	 */
	public Hologram removeMessage(int index) {
		
		if (index >= this.getArmorStands().size())
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.getArmorStands().size());
		
		nmsHologram.removeMessage(this, index);
		this.armorStands.remove(index);
		this.message.remove(index);
		
		return this;
	}
	
	/**
	 * Set a Item for the Hologram, it must be a instance of EntityItem! You can use HoloItem to create one and then just pass holoItem.getItem()
	 * @param holoItem
	 * @return Hologram
	 */
	public Hologram setItem(Object item) {
		nmsHologram.setItem(this, item);
		this.item = item;
		
		return this;
	}
	
	/**
	 * Removes the Item of the Hologram
	 * @return Hologram
	 */
	public Hologram removeItem() {
		nmsHologram.removeItem(this);
		this.item = null;
		
		return this;
	}
	
	/**
	 * Moves the Hologram
	 * @param location
	 * @return Hologram
	 */
	public Hologram move(Location location) {
		nmsHologram.move(this, location);
		this.location = location;
		
		return this;
	}
	
	/**
	 * Set the distance between the lines of the Hologram
	 * @param distance
	 * @return Hologram
	 */
	public Hologram setDistance(double distance) {
		
		if (this.distance != distance) {
			this.distance = distance;
			nmsHologram.setDistance(this, distance);
		}
		return this;
	}
	
	/**
	 * Gets the ArmorStand at the specified index
	 * @param index
	 * @return ArmorStand at the specified index
	 */
	public Object getArmorStand(int index) {
		return this.armorStands.get(index);
	}
	
	/**
	 * Gets all ArmorStands that belong to the Hologram
	 * @return all ArmorStands that belong to the hologram
	 */
	public List<Object> getArmorStands() {
		return new ArrayList<Object>(this.armorStands);
	}
	
	/**
	 * Gets all messages that belong to the Hologram
	 * @return all messages that belong to the Hologram
	 */
	public List<String> getMessage() {
		return new ArrayList<String>(this.message);
	}
	
	/**
	 * Gets the message at the specified index
	 * @param index
	 * @return get the message at the specified index, if there is no message, returns null
	 */
	public String getMessage(int index) {
		
		if (index >= this.message.size())
			return null;
		
		return this.message.get(index);
	}
	
	/**
	 * Gets whether the Hologram has a Item
	 * @return if the Hologram has a Item
	 */
	public boolean hasItem() {
		return item != null;
	}
	
	/**
	 * Gets the Item that belongs to the Hologram
	 * @return the Item that belongs to the Hologram
	 */
	public Object getItem() {
		return this.item;
	}
	
	/**
	 * Gets the location of the Hologram
	 * @return the location of the Hologram
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Gets the distance between the lines of the Hologram
	 * @return the distance between the lines of the Hologram
	 */
	public double getDistance() {
		return this.distance;
	}
	
	/**
	 * Show the HoloItem to the specified player(s)
	 * @param player
	 * @return Hologram
	 */
	public Hologram show(Player... player) {
		
		for (Player p : player) {
			this.players.add(p);
			nmsHologram.showPlayer(this, p);
		}
		
		return this;
	}
	
	/**
	 * Hide the Hologram to the specified player(s)
	 * @param player
	 * @return Hologram
	 */
	public Hologram hide(Player... player) {
		
		for (Player p : player) {
			nmsHologram.hidePlayer(this, p);
			this.players.remove(p);
		}
		
		return this;
	}
	
	/**
	 * Gets all players who can currently see the Hologram
	 * @return the players that belong to the Hologram
	 */
	public Set<Player> getPlayers() {
		return new HashSet<Player>(this.players);
	}
	
	/**
	 * Removes the Hologram
	 */
	public void delete() {
		hide(this.getPlayers().toArray(new Player[this.getPlayers().size()]));
	}
	
	/**
	 * Checks if the player can see the Hologram
	 * @param player
	 * @return True if the provided player can see the Hologram
	 */
	public boolean canSee(Player player) {
		return this.players.contains(player);
	}
	
}
