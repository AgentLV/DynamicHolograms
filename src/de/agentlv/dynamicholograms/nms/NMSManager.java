package de.agentlv.dynamicholograms.nms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.objects.HoloItem;
import de.agentlv.dynamicholograms.objects.Hologram;

public interface NMSManager {
	
	/**
	 * Creates a hologram
	 * @param hologram
	 * @return
	 */
	public NMSHologram createHologram(Hologram hologram);
	
	/**
	 * Creates a holoitem
	 * @param holoItem
	 * @return
	 */
	public void createHoloItem(HoloItem holoItem);
	
	/**
	 * Make a hologram visible to a player
	 * @param hologram
	 */
	public void showPlayerHologram(Hologram hologram, Player player);
	
	/**
	 * Make a holoitem visible to a player
	 * @param holoItem
	 */
	public void showPlayerHoloItem(HoloItem holoItem, Player player);
	
	/**
	 * Make a hologram invisible to a player
	 * @param hologram
	 */
	public void hidePlayerHologram(Hologram hologram, Player player);
	
	/**
	 * Make a holoitem invisible to a player
	 * @param holoItem
	 */
	public void hidePlayerHoloItem(HoloItem holoItem, Player player);
	
	/**
	 * Moves a hologram
	 * @param hologram
	 * @param newLocation
	 */
	public void moveHologram(Hologram hologram, Location newLocation);
	
	/**
	 * Moves a holoitem
	 * @param holoItem
	 * @param newLocation
	 */
	public void moveHoloItem(HoloItem holoItem, Location newLocation);
	
	/**
	 * Add an armorstand to a hologram 
	 * @param hologram
	 * @param object
	 */
	public Object hologramAddMessage(Hologram hologram, String message);
	
	/**
	 * Override a existing message
	 * @param hologram
	 * @param index
	 * @param message
	 * @return
	 */
	public Object hologramSetMessage(Hologram hologram, int index, String message);
	
	/**
	 * Remove the message at the specified index
	 * @param hologram
	 * @param index
	 */
	public void hologramRemoveMessage(Hologram hologram, int index);
	
	/**
	 * Set a holoitem for the specified hologram
	 * @param hologram
	 * @param holoItem
	 */
	public HoloItem hologramSetHoloItem(Hologram hologram, HoloItem holoItem);
	
	/**
	 * Remove the holoitem of a hologram
	 * @param hologram
	 */
	public void hologramRemoveHoloItem(Hologram hologram);
	
	/**
	 * Remove a hologram
	 * @param hologram
	 */
	public void removeHologram(Hologram hologram);
	
	/**
	 * Remove a holoitem
	 * @param holoItem
	 */
	public void removeHoloItem(HoloItem holoItem);
	
}
