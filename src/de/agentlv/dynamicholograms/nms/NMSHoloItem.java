package de.agentlv.dynamicholograms.nms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.objects.HoloItem;

public interface NMSHoloItem {

	/**
	 * Create a holoitem
	 * @param holoItem
	 */
	public void create(HoloItem holoItem);
	
	/**
	 * Make a holoitem visible to a player
	 * @param holoItem
	 */
	public void showPlayer(HoloItem holoItem, Player player);
	
	/**
	 * Make a holoitem invisible to a player
	 * @param holoItem
	 */
	public void hidePlayer(HoloItem holoItem, Player player);
	
	/**
	 * Moves a holoitem
	 * @param holoItem
	 * @param newLocation
	 */
	public void move(HoloItem holoItem, Location newLocation);
	
	/**
	 * Remove a holoitem
	 * @param holoItem
	 */
	public void remove(HoloItem holoItem);
	
}
