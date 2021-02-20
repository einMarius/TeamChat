package me.Littlq.Listener;

import me.Littlq.main.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
//import me.Littlq.main.Main;
//import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PostLoginEvent e) {

		ProxiedPlayer p = e.getPlayer();

		Main.autologin = Main.config.getBoolean("Log." + p.getName());
		//Main.otheruserlogoutmsg = Main.otheruserlogginmsg.replace("%PLAYER%", p.getName());

		if (Main.autologin == true) {

			p.sendMessage(Main.configpref + Main.logginmsg);

			for (ProxiedPlayer all : Main.login) {

				all.sendMessage(Main.configpref + "Der Spieler §a" + p.getName() + " §7hat sich eingeloggt!");

			}

			Main.login.add(p);

		}

	}
}
