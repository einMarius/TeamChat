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

		if (Main.autologin == true) {

			p.sendMessage(Main.configpref + Main.logginmsg);

			for (ProxiedPlayer all : Main.login) {

				String otherloggin = Main.otheruserlogginmsg.replace("%PLAYER%", p.getName());
				all.sendMessage(Main.configpref + otherloggin);

			}

			Main.login.add(p);

		}

	}
}
