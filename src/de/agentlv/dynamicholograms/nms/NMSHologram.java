package de.agentlv.dynamicholograms.nms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.objects.HoloItem;
import de.agentlv.dynamicholograms.objects.Hologram;

public interface NMSHologram {

	/**
	 * Create a hologram
	 * @param hologram
	 * @return
	 */
	public Object create(Hologram hologram);
	
	/**
	 * Make a hologram visible to a player
	 * @param hologram
	 */
	public void showPlayer(Hologram hologram, Player player);
	
	/**
	 * Make a hologram invisible to a player
	 * @param hologram
	 */
	public void hidePlayer(Hologram hologram, Player player);
	
	/**
	 * Moves a hologram
	 * @param hologram
	 * @param newLocation
	 */
	public void move(Hologram hologram, Location newLocation);
	
	/**
	 * Add a message/line to a hologram 
	 * @param hologram
	 * @param object
	 */
	public Object addMessage(Hologram hologram, String message);
	
	/**
	 * Override a existing message
	 * @param hologram
	 * @param index
	 * @param message
	 * @return
	 */
	public Object setMessage(Hologram hologram, int index, String message);
	
	/**
	 * Remove the message at the specified index
	 * @param hologram
	 * @param index
	 */
	public void removeMessage(Hologram hologram, int index);
	
	/**
	 * Set a holoitem for the specified hologram
	 * @param hologram
	 * @param holoItem
	 */
	public HoloItem setHoloItem(Hologram hologram, HoloItem holoItem);
	
	/**
	 * Remove the holoitem of a hologram
	 * @param hologram
	 */
	public void removeHoloItem(Hologram hologram);
	
	/**
	 * Remove a hologram
	 * @param hologram
	 */
	public void remove(Hologram hologram);
	
}
