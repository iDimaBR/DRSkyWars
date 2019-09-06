package com.idimabr.lightskywars.eventos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.idimabr.lightskywars.Main;
import com.idimabr.lightskywars.comandos.Comandos;

public class Eventos implements Listener {

	public static void unloadMap(String mapname) {
		Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false);
	}

	public static void loadMap(String mapname) {
		Bukkit.getServer().createWorld(new WorldCreator(mapname));
	}

	public static void rollback(String mapname) {
		unloadMap(mapname);
		loadMap(mapname);
		Bukkit.getWorld("sw_deserto").setAutoSave(false);
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[LightSkyWars] §aMUNDO SKYWARS RESETADO COM SUCESSO!");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getWorld("sw_deserto").setPVP(false);
	}

	public static void CheckWin() {
		if (Comandos.entrou.size() == 1) {
			Comandos.fechado = true;
			Comandos.havendo_skywars = false;
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§9[SkyWars] §eSkyWars terminado!");
			Bukkit.broadcastMessage("§9[SkyWars] §eMapa: §fDeserto");
			Bukkit.broadcastMessage("§9[SkyWars] §eGanhador foi §f" + Comandos.entrou.get(0).getPlayer().getDisplayName());
			Bukkit.broadcastMessage("");
			Comandos.entrou.get(0).getPlayer().getInventory().clear();
			Comandos.entrou.get(0).getInventory().setHelmet(null);
			Comandos.entrou.get(0).getInventory().setChestplate(null);
			Comandos.entrou.get(0).getInventory().setLeggings(null);
			Comandos.entrou.get(0).getInventory().setBoots(null);
			Comandos.entrou.get(0).getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
			Comandos.entrou.get(0).getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			Comandos.entrou.remove(Comandos.entrou.get(0).getPlayer());
			Comandos.teleportar.clear();
			Comandos.entrou.clear();
			Comandos.kills.clear();
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.pl, new Runnable() {
				public void run() {
					for (Player player : Bukkit.getWorld("sw_deserto").getPlayers()) {
						player.teleport(Bukkit.getWorld("world").getSpawnLocation());
					}
					rollback("sw_deserto");
					Main.pl.getServer().getScheduler().cancelTasks(Main.pl);
				}
			}, 20*3);
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (Comandos.fechado == true) {
			if (Comandos.havendo_skywars == true) {
				if (event.getEntity() instanceof Player) {
					Player p = (Player) event.getEntity();
					EntityDamageEvent deathCause = p.getLastDamageCause();
					if (deathCause.getCause() == DamageCause.ENTITY_ATTACK || (deathCause.getCause() == DamageCause.PROJECTILE)) {
						Entity entity = deathCause.getEntity();
						if (entity instanceof Player) {
							Player killer = (Player) entity;
							for (Player player : Comandos.entrou) {
								player.sendMessage("§9[SkyWars] §eO jogador §a" + killer.getDisplayName() + " §eassassinou §c" + p.getDisplayName());
							}
							int kills_atual = Integer.valueOf(Comandos.kills.get(killer));
							Comandos.kills.remove(killer);
							Comandos.kills.put(killer, Integer.valueOf(kills_atual + 1));
							Comandos.entrou.remove(p);
							p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
							CheckWin();
						} else {
							for (Player player : Comandos.entrou) {
								player.sendMessage("§9[SkyWars] §eO jogador §a" + p.getDisplayName() + " §emorreu sozinho :p");
							}
							Comandos.entrou.remove(p);
							Comandos.teleportar.remove(p);
							Comandos.kills.remove(p);
							p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
							CheckWin();
						}
					} else {
						for (Player player : Comandos.entrou) {
							player.sendMessage("§9[SkyWars] §eO jogador §a" + p.getDisplayName() + " §emorreu sozinho.");
						}
						p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
						Comandos.entrou.remove(p);
						Comandos.teleportar.remove(p);
						Comandos.kills.remove(p);
						p.teleport(Bukkit.getWorld("world").getSpawnLocation());
						CheckWin();
					}
				}
			}
		}
	}

	@EventHandler
	public void OnSair(PlayerQuitEvent e) {
		if (Comandos.fechado == true) {
			if (Comandos.havendo_skywars == true) {
				Player p = e.getPlayer();
				for (Player player : Comandos.entrou) {
					player.sendMessage("§9[SkyWars] §eO jogador §a" + p.getDisplayName() + " §esaiu do jogo.");
				}
				Comandos.entrou.remove(p);
				Comandos.teleportar.remove(p);
				Comandos.kills.remove(p);
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				p.teleport(Bukkit.getWorld("world").getSpawnLocation());
				CheckWin();
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (Comandos.fechado == true) {
			if (Comandos.havendo_skywars == true) {
				if (Comandos.entrou.contains(e.getPlayer())) {
					if (!e.getMessage().toLowerCase().startsWith("/sw")
							|| !e.getMessage().toLowerCase().startsWith("/g")
							|| !e.getMessage().toLowerCase().startsWith("/tell")) {
						e.setCancelled(true);
						e.getPlayer().sendMessage("§9[SkyWars] §cPara sair utilize §f/sw sair");
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (Comandos.fechado == true) {
			if (Comandos.havendo_skywars == true) {
				Player p = event.getPlayer();
				if (p.getLocation().getBlockY() <= 0) {
					for (Player player : Comandos.entrou) {
						player.sendMessage("§9[SkyWars] §eO jogador §a" + p.getDisplayName() + " §ecaiu para o nada.");
					}
					p.getInventory().clear();
					p.getInventory().clear();
					p.getInventory().clear();
					Comandos.entrou.remove(p);
					Comandos.teleportar.remove(p);
					Comandos.kills.remove(p);
					p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
					p.sendMessage("§9[SkyWars] §7Você caiu para o nada :p");
					p.teleport(Bukkit.getWorld("world").getSpawnLocation());
					CheckWin();
				}
			}
		}
	}
}
