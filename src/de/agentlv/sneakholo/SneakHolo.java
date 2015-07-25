package de.agentlv.sneakholo;

import org.bukkit.plugin.java.JavaPlugin;

import de.agentlv.sneakholo.listeners.PlayerMoveListener;
import de.agentlv.sneakholo.listeners.PlayerToggleSneakListener;

public class SneakHolo extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new PlayerMoveListener(this);
		new PlayerToggleSneakListener(this);
	}
}
