package de.agentlv.dynamicholograms.objects;

import java.util.UUID;

/**
 * A object used to store data of player skulls
 */
public class PlayerSkullData {

	private UUID uniqueId;
	private String playerName;
	
	/**
	 * 
	 * @param uniqueId
	 * @param playerName
	 */
	public PlayerSkullData(UUID uniqueId, String playerName) {
		this.uniqueId = uniqueId;
		this.playerName = playerName;
	}
	
	/**
	 * Set the UUID
	 * @param uniqueId
	 * @return PlayerSkullData
	 */
	public PlayerSkullData setUniqueId(UUID uniqueId) {
		this.uniqueId = uniqueId;
		
		return this;
	}
	
	/**
	 * Set the player name
	 * @param playerName
	 * @return PlayerSkullData
	 */
	public PlayerSkullData setPlayerName(String playerName) {
		this.playerName = playerName;
		
		return this;
	}
	
	/**
	 * Gets the UUID of the PlayerSkullData
	 * @return UUID
	 */
	public UUID getUniqueId() {
		return this.uniqueId;
	}
	
	/**
	 * Gets the player name of the PlayerSkullData
	 * @return Player name
	 */
	public String getPlayerName() {
		return this.playerName;
	}
}
