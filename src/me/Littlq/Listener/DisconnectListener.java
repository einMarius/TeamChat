package me.Littlq.Listener;

import me.Littlq.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectListener implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDisconnect(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if (Main.login.contains(p)) {
			Main.login.remove(p);
			for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				if (Main.login.contains(all)) {
					String player = Main.otheruserlogoutmsg.replace("%PLAYER%", e.getPlayer().getName());
					all.sendMessage(Main.configpref + player);
				}
			}
		}
		if (Main.hidden.contains(p))
			Main.hidden.remove(p);
	}
}
