package de.agentlv.dynamicholograms.objects;

import java.util.UUID;

public class PlayerSkullData {

	private UUID uniqueId;
	private String playerName;
	
	public PlayerSkullData() {
		
	}
	
	public PlayerSkullData(UUID uniqueId, String playerName) {
		this.uniqueId = uniqueId;
		this.playerName = playerName;
	}
	
	public PlayerSkullData setUniqueId(UUID uniqueId) {
		this.uniqueId = uniqueId;
		
		return this;
	}
	
	public PlayerSkullData setPlayerName(String playerName) {
		this.playerName = playerName;
		
		return this;
	}
	
	public UUID getUniqueId() {
		return this.uniqueId;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
}
