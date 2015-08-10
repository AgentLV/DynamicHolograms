package de.agentlv.dynamicholograms.nms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.objects.Hologram;

public interface NMSHologram {

	/**
	 * Create a Hologram
	 * @param hologram
	 * @return first ArmorStand
	 */
	public Object create(Hologram hologram);
	
	/**
	 * Make a Hologram visible to a player
	 * @param hologram
	 */
	public void showPlayer(Hologram hologram, Player player);
	
	/**
	 * Make a Hologram invisible to a player
	 * @param hologram
	 */
	public void hidePlayer(Hologram hologram, Player player);
	
	/**
	 * Moves a Hologram
	 * @param hologram
	 * @param newLocation
	 */
	public void move(Hologram hologram, Location newLocation);
	
	/**
	 * Add a message/line to a Hologram 
	 * @param hologram
	 * @param object
	 * @return ArmorStand for the the message
	 */
	public Object addMessage(Hologram hologram, String message);
	
	/**
	 * Override a existing message
	 * @param hologram
	 * @param index
	 * @param message
	 * @return updated ArmorStand
	 */
	public Object setMessage(Hologram hologram, int index, String message);
	
	/**
	 * Remove the message at the specified index
	 * @param hologram
	 * @param index
	 */
	public void removeMessage(Hologram hologram, int index);
	
	/**
	 * Set a Item for the specified Hologram
	 * @param hologram
	 * @param holoItem
	 */
	public void setItem(Hologram hologram, Object rawitem);
	
	/**
	 * Remove the Items of a Hologram
	 * @param hologram
	 */
	public void removeItem(Hologram hologram);
	
	/**
	 * Set the distance between the lines
	 * @param hologram
	 * @param distance
	 * @return update armorstands
	 */
	public void setDistance(Hologram hologram, double distance);
	
}
