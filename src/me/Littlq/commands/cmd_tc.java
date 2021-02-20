package me.Littlq.commands;

import java.io.IOException;

import me.Littlq.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class cmd_tc extends Command {

	public cmd_tc() {
		super("tc");
	}

	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (p.hasPermission("tc.see")) {

				if (args.length > 0) {

					// LOGIN DES SPIELERS

					if (args[0].equalsIgnoreCase("login")) {
						if (!Main.login.contains(p)) {
							for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								if (Main.login.contains(all)) {
									// Main.otheruserlogginmsg = Main.otheruserlogginmsg.replace("%PLAYER%",
									// sender.getName());
									all.sendMessage(Main.configpref + "Der Spieler §a" + p.getName()
											+ " §7hat sich eingeloggt!");
								}
								// Main.login.contains(all);
							}
							Main.login.add(p);
							p.sendMessage(Main.configpref + Main.logginmsg);
						} else if (Main.login.contains(p)) {
							p.sendMessage(Main.configpref + Main.alreadyloggedinmsg);
						}

						// LOGOUT DES SPIELERS

					} else if (args[0].equalsIgnoreCase("logout")) {
						if (Main.login.contains(p)) {
							Main.login.remove(p);
							p.sendMessage(Main.configpref + Main.logoutmsg);
							for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								if (Main.login.contains(all)) {
									// Main.otheruserlogoutmsg = Main.otheruserlogoutmsg.replace("%PLAYER%",
									// sender.getName());
									all.sendMessage(Main.configpref + "Der Spieler §a" + p.getName()
											+ " §7hat sich ausgeloggt!");
								}
								// Main.login.contains(all);
							}

						} else if (!Main.login.contains(p)) {
							p.sendMessage(Main.configpref + Main.alreadyloggedoutmsg);
						}

						// HELP BEFEHL

					} else if (args[0].equalsIgnoreCase("help")) {
						p.sendMessage(Main.configpref + Main.helpmsg);

						// LIST BEFEHL

					} else if (args[0].equalsIgnoreCase("list")) {
						if (Main.login.contains(p)) {
							p.sendMessage("");
							p.sendMessage(Main.configpref + "§8----------=" + Main.configpref + "§8=----------");
							for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								if (Main.login.contains(all)) {
									if (!Main.hidden.contains(all)) {

										// Main.userservermsg = Main.userservermsg.replace("%PLAYER%", all.getName());
										// Main.userservermsg = Main.userservermsg.replace("%SERVER%",
										// all.getServer().getInfo().getName());

										p.sendMessage(Main.configpref + "Der Spieler §a" + all.getName()
												+ " §7ist §aeingeloggt §7auf: §a§l"
												+ all.getServer().getInfo().getName());
										continue;
									}
									//Main.userserverhiddenmsg = Main.userserverhiddenmsg.replace("%PLAYER%",
											//all.getName());
									p.sendMessage(Main.configpref + "Der Spieler §a" + p.getName() + " §7ist §aeingeloggt §7auf: §a§lHIDDEN");
									continue;
								}
							}
							p.sendMessage(Main.configpref + "§8----------=" + Main.configpref + "§8=----------");
						} else {
							p.sendMessage(Main.configpref + Main.mustbeloggedinmsg);
						}

						// VON LISTE VERSCHWINDEN

					} else if (args[0].equalsIgnoreCase("remove")) {
						if (args[1].equalsIgnoreCase("list"))
							if (Main.login.contains(p)) {
								if (!Main.hidden.contains(p)) {
									Main.hidden.add(p);
									p.sendMessage(Main.configpref + Main.serverhiddenmsg);
								} else {
									p.sendMessage(Main.configpref + Main.serveralreadyhiddenmsg);
								}
							} else {
								p.sendMessage(Main.configpref + Main.mustbeloggedinmsg);
							}

						// IN LISTE WIEDER AUFTAUCHEN

					} else if (args[0].equalsIgnoreCase("add")) {
						if (args[1].equalsIgnoreCase("list"))
							if (Main.login.contains(p)) {
								if (Main.hidden.contains(p)) {
									Main.hidden.remove(p);
									p.sendMessage(Main.configpref + Main.servershownmsg);
								} else {
									p.sendMessage(Main.configpref + Main.serveralreadyshownmsg);
								}
							} else {
								p.sendMessage(Main.configpref + Main.mustbeloggedinmsg);
							}

						// AUTOLOGIN

					} else if (args[0].equalsIgnoreCase("autologin")) {

						Main.autologin = Main.config.getBoolean("Log." + p.getName());

						if (Main.login.contains(p)) {

							if (Main.autologin == null) {

								Main.config.set("Log." + p.getName(), true);

								try {
									ConfigurationProvider.getProvider(YamlConfiguration.class).save(Main.config,
											Main.file);
								} catch (IOException e) {
									e.printStackTrace();
									System.out.println("Es gab einen Fehler beim Speichern der config.yml");
								}

								p.sendMessage(Main.configpref + Main.autologinonmsg);

							} else if (Main.autologin != null) {

								if (Main.autologin == false) {

									Main.config.set("Log." + p.getName(), true);

									try {
										ConfigurationProvider.getProvider(YamlConfiguration.class).save(Main.config,
												Main.file);
									} catch (IOException e) {
										e.printStackTrace();
										System.out.println("Es gab einen Fehler beim Speichern der config.yml");
									}

									p.sendMessage(Main.configpref + Main.autologinonmsg);

								} else if (Main.autologin == true) {

									Main.config.set("Log." + p.getName(), false);

									try {
										ConfigurationProvider.getProvider(YamlConfiguration.class).save(Main.config,
												Main.file);
									} catch (IOException e) {
										e.printStackTrace();
										System.out.println("Es gab einen Fehler beim Speichern der config.yml");
									}

									p.sendMessage(Main.configpref + Main.autologinoffmsg);

								}
							}
						} else
							p.sendMessage(Main.configpref + Main.mustbeloggedinmsg);

					} else if (Main.login.contains(p)) {
						for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
							if (all.hasPermission("tc.see")) {
								if (Main.login.contains(all)) {
									String nachricht = "";
									for (int i = 0; i < args.length; i++)
										nachricht = String.valueOf(nachricht) + " " + args[i];
									all.sendMessage(Main.configpref + "§a" + p.getDisplayName() + " §7➡" + nachricht);
									continue;
								}
								Main.login.contains(all);
							}
						}
					} else if (!Main.login.contains(p)) {
						p.sendMessage(Main.configpref + Main.mustbeloggedinmsg);
					}
				} else if (args.length == 0) {

					p.sendMessage(Main.configpref + Main.helpmsg);
				}
			} else {
				p.sendMessage(Main.configpref + Main.noperms);
			}
		} else {
			sender.sendMessage("§cMust be a player!");
		}
	}
}
