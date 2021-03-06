package me.Littlq.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.Littlq.Listener.DisconnectListener;
import me.Littlq.Listener.JoinListener;
import me.Littlq.commands.cmd_tc;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {

	public static ArrayList<ProxiedPlayer> login = new ArrayList<>();
	public static ArrayList<ProxiedPlayer> hidden = new ArrayList<>();

	private static Main plugin;

	public static File file;
	public static Configuration config;

	// MESSAGES STRINGS
	public static String configpref;
	public static String noperms;
	public static String logginmsg;
	public static String logoutmsg;
	public static String alreadyloggedinmsg;
	public static String alreadyloggedoutmsg;
	public static String otheruserlogginmsg;
	public static String otheruserlogoutmsg;
	public static String mustbeloggedinmsg;
	public static String helpmsg;
	public static String userservermsg;
	public static String userserverhiddenmsg;
	public static String serverhiddenmsg;
	public static String serveralreadyhiddenmsg;
	public static String servershownmsg;
	public static String serveralreadyshownmsg;
	public static String autologinonmsg;
	public static String autologinoffmsg;
	public static String confmessage;

	public static Boolean autologin;

	public void onEnable() {

		// -------------------------------
		System.out.println("----------[TeamChat]----------");
		System.out.println("Plugin aktiviert");
		System.out.println("Version: 6.0.1");
		System.out.println("Author: Littlq");
		System.out.println("----------[TeamChat]----------");
		// -------------------------------

		plugin = this;

		getProxy().getPluginManager().registerCommand(this, (Command) new cmd_tc());
		getProxy().getPluginManager().registerListener(this, (Listener) new DisconnectListener());
		getProxy().getPluginManager().registerListener(this, (Listener) new JoinListener());

		try {

			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}

			file = new File(getDataFolder().getParent(), "TeamChat/config.yml");
			if (!file.exists()) {
				file.createNewFile();
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				config.set("Information.Sonderzeichen",
						"Wenn ihr Sonderzeichen verwenden wollt, muesst ihr schauen, ob sie funktionieren");
				config.set("Information.Permission",
						"Die Berechtigung, um den Teamchat nutzen zu koennen lautet: tc.see");
				config.set("Information.Log", "Der Log zeigt, wer den autologin aktiviert hat. True bedeutet aktiviert, false bedeutet deaktiviert!");
				config.set("Einstellungen.Prefix", "&7[&cTeamChat&7] &7");
				config.set("Einstellungen.Message", "&a%PLAYER% &7: %MESSAGE%");
				config.set("Einstellungen.Keine Berechtigung", "Dazu hast du keine Berechtigung!");
				config.set("Einstellungen.Server verstecken",
						"Dein aktueller Server ist nun im TeamChat &aversteckt&7!");
				config.set("Einstellungen.Server bereits versteckt",
						"Dein aktueller Server ist bereits &cnicht &7zu sehen!");

				config.set("Einstellungen.Server anzeigen",
						"Dein aktueller Server ist nun im TeamChat nicht länger §aversteckt§7!");
				config.set("Einstellungen.Server bereits angezeigt", "Dein aktueller Server ist &abereits &7zu sehen!");

				config.set("Einstellungen.Hilfenachricht",
						"Nutze: <&alogin &7| &clogout &7| &elist &7| &bNachricht &7| &6autologin &7| &3remove&7/&3add list &7>");
				config.set("Loggin.User-Loggin", "Du hast dich &aeingeloggt&7!");
				config.set("Loggin.User-Loggout", "Du hast dich &causgeloggt&7!");
				config.set("Loggin.Anderer User-Loggin", "Der Spieler &a%PLAYER% &7hat sich eingeloggt!");
				config.set("Loggin.Anderer User-Loggout", "Der Spieler &a%PLAYER% &7hat sich ausgeloggt!");
				config.set("Loggin.Bereits eingeloggt", "Du bist &abereits &7eingeloggt!");
				config.set("Loggin.Muss eingeloggt sein", "Du musst &aeingeloggt &7sein!");
				config.set("Loggin.Spieler-Server", "Der Spieler &a%PLAYER% &7ist &aeingeloggt &7auf: &a&l%SERVER%");
				config.set("Loggin.Spieler-Server versteckt", "Der Spieler &a%PLAYER% &7ist &aeingeloggt &7auf: &a&lHIDDEN");
				config.set("Loggin.Autologin-On", "Du wirst nun &aautomatisch &7eingeloggt!");
				config.set("Loggin.Autologin-Off", "Du wirst nun &cnicht &7mehr automatisch eingeloggt!");
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
			}

			// REGISTER-------------------

			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			configpref = config.getString("Einstellungen.Prefix");
			configpref = ChatColor.translateAlternateColorCodes('&', configpref);

			confmessage = config.getString("Einstellungen.Message");
			confmessage = ChatColor.translateAlternateColorCodes('&', confmessage);

			autologinonmsg = config.getString("Loggin.Autologin-On");
			autologinonmsg = ChatColor.translateAlternateColorCodes('&', autologinonmsg);

			autologinoffmsg = config.getString("Loggin.Autologin-Off");
			autologinoffmsg = ChatColor.translateAlternateColorCodes('&', autologinoffmsg);

			servershownmsg = config.getString("Einstellungen.Server anzeigen");
			servershownmsg = ChatColor.translateAlternateColorCodes('&', servershownmsg);

			serveralreadyshownmsg = config.getString("Einstellungen.Server bereits angezeigt");
			serveralreadyshownmsg = ChatColor.translateAlternateColorCodes('&', serveralreadyshownmsg);

			serverhiddenmsg = config.getString("Einstellungen.Server verstecken");
			serverhiddenmsg = ChatColor.translateAlternateColorCodes('&', serverhiddenmsg);

			serveralreadyhiddenmsg = config.getString("Einstellungen.Server bereits versteckt");
			serveralreadyhiddenmsg = ChatColor.translateAlternateColorCodes('&', serveralreadyhiddenmsg);

			userservermsg = config.getString("Loggin.Spieler-Server");
			userservermsg = ChatColor.translateAlternateColorCodes('&', userservermsg);

			userserverhiddenmsg = config.getString("Loggin.Spieler-Server versteckt");
			userserverhiddenmsg = ChatColor.translateAlternateColorCodes('&', userserverhiddenmsg);

			noperms = config.getString("Einstellungen.Keine Berechtigung");

			helpmsg = config.getString("Einstellungen.Hilfenachricht");
			helpmsg = ChatColor.translateAlternateColorCodes('&', helpmsg);

			logginmsg = config.getString("Loggin.User-Loggin");
			logginmsg = ChatColor.translateAlternateColorCodes('&', logginmsg);

			logoutmsg = config.getString("Loggin.User-Loggout");
			logoutmsg = ChatColor.translateAlternateColorCodes('&', logoutmsg);

			otheruserlogginmsg = config.getString("Loggin.Anderer User-Loggin");
			otheruserlogginmsg = ChatColor.translateAlternateColorCodes('&', otheruserlogginmsg);

			otheruserlogoutmsg = config.getString("Loggin.Anderer User-Loggout");
			otheruserlogoutmsg = ChatColor.translateAlternateColorCodes('&', otheruserlogoutmsg);

			alreadyloggedinmsg = config.getString("Loggin.Bereits eingeloggt");
			alreadyloggedinmsg = ChatColor.translateAlternateColorCodes('&', alreadyloggedinmsg);

			mustbeloggedinmsg = config.getString("Loggin.Muss eingeloggt sein");
			mustbeloggedinmsg = ChatColor.translateAlternateColorCodes('&', mustbeloggedinmsg);
			// REGISTER--------------------

		} catch (IOException ex) {
			System.out.println("Es gab einen Fehler beim erstellen der Config.yml");
		}

	}

	public void onDisable() {

		// -------------------------------
		System.out.println("----------[TeamChat]----------");
		System.out.println("Plugin deaktiviert");
		System.out.println("Version: 6.0.1");
		System.out.println("Author: Littlq");
		System.out.println("----------[TeamChat]----------");
		// -------------------------------

	}

	public static Main getPlugin() {
		return plugin;
	}
}
