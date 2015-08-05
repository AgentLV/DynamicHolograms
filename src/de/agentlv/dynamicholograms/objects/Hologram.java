package de.agentlv.dynamicholograms.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.agentlv.dynamicholograms.DynamicHolograms;
import de.agentlv.dynamicholograms.nms.NMSManager;

public class Hologram {

	private List<Object> armorStands = new ArrayList<Object>();
	private List<String> message = new ArrayList<String>();
	private HoloItem holoItem;
	private Location location;
	private double distance = 0.28;
	private Set<Player> players = new HashSet<Player>();
	private NMSManager nmsManager = DynamicHolograms.getNMSManager();
	
	public Hologram(Location location, String... message) {
		this.location = location;
		this.message.add(message[0]);
		this.armorStands.add(nmsManager.createHologram(this));
		
		for (int i = 1; i < message.length; ++i)
			addMessage(message[i]);
			
	}
	
	public Hologram(Location location, double distance, String... message) {
		this.location = location;
		this.message.add(message[0]);
		this.armorStands.add(nmsManager.createHologram(this));
		this.distance = distance;
		
		for (int i = 1; i < message.length; ++i)
			addMessage(message[i]);
	}
	
	public void addMessage(String message) {	
		this.message.add(message);
		this.armorStands.add(nmsManager.hologramAddMessage(this, message));
	}
	
	public void setMessage(int index, String message) {
		this.message.set(index, message);
		this.armorStands.set(index, nmsManager.hologramSetMessage(this, index, message));
	}
	
	public void removeMessage(int index) {
		nmsManager.hologramRemoveMessage(this, index);
		this.armorStands.remove(index);
		this.message.remove(index);
	}
	
	public void setHoloItem(HoloItem holoItem) {
		holoItem.setArmorStand(this.armorStands.get(0));
		this.holoItem = nmsManager.hologramSetHoloItem(this, holoItem);
	}
	
	public void removeHoloItem() {
		this.holoItem = null;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void moveHologram(Location location) {
		nmsManager.moveHologram(this, location);
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public Object getArmorStand(int index) {
		return this.armorStands.get(index);
	}
	
	public List<Object> getArmorStands() {
		return new ArrayList<Object>(this.armorStands);
	}
	
	public List<String> getMessage() {
		return new ArrayList<String>(this.message);
	}
	
	public String getMessage(int index) {
		return this.message.get(index);
	}
	
	public boolean hasHoloItem() {
		return holoItem != null;
	}
	
	public HoloItem getHoloItem() {
		return this.holoItem;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public void show(Player... player) {
		
		for (Player p : player) {
			this.players.add(p);
			nmsManager.showPlayerHologram(this, p);
		}
		
	}
	
	public void hide(Player... player) {
		
		for (Player p : player) {
			this.players.remove(p);
			nmsManager.hidePlayerHologram(this, p);
		}
	}
	
	public Set<Player> getPlayers() {
		return new HashSet<Player>(this.players);
	}
	
	public void delete() {
		nmsManager.removeHologram(this);
	}
	
}
