package com.idimabr.lightskywars.comandos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.idimabr.lightskywars.Main;
import com.idimabr.lightskywars.eventos.Eventos;
import com.idimabr.lightskywars.util.Item;
import com.idimabr.lightskywars.util.ScoreboardWrapper;

public class Comandos implements CommandExecutor {

	public static ArrayList<Player> entrou = new ArrayList<Player>();
	public static ArrayList<Player> teleportar = new ArrayList<Player>();
	public static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
	public static boolean havendo_skywars = false;
	public static boolean fechado = true;
	
	private boolean inventoryIsEmpty(Player p){
		PlayerInventory inv = p.getInventory();
		ItemStack[] arrayOfItemStack;
		int j = (arrayOfItemStack = inv.getContents()).length;
		for (int i = 0; i < j; i++){
			ItemStack is = arrayOfItemStack[i];
			if ((is != null) && (is.getType() != Material.AIR)) {
				return false;
			}
		}
	    j = (arrayOfItemStack = inv.getArmorContents()).length;
	    for (int i = 0; i < j; i++){
	    	ItemStack is = arrayOfItemStack[i];
	     	if ((is != null) && (is.getType() != Material.AIR)) {
	     		return false;
	     	}
	    }
	    if ((p.getItemOnCursor() != null) && (p.getItemOnCursor().getType() != Material.AIR)) {
	    	return false;
	    }
	    return true;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void SetarScoreboard(final Player p) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.pl, new Runnable() {
			public void run() {
				if(entrou.contains(p)) {
					Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective obj = board.registerNewObjective("VC", "BOARD");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					obj.setDisplayName("§9§lSkyWars");
					// obj.getScore(Bukkit.getOfflinePlayer("")).setScore(10);
					obj.getScore("  ").setScore(9);
					obj.getScore(Bukkit.getOfflinePlayer("§eMapa: §fDeserto")).setScore(8);
					//obj.getScore(Bukkit.getOfflinePlayer("").setScore(8);
					obj.getScore(Bukkit.getOfflinePlayer("§eKills: §f" + kills.get(p))).setScore(7);
					obj.getScore(Bukkit.getOfflinePlayer("§eVivos: §f" + entrou.size())).setScore(6);
					obj.getScore(" ").setScore(5);
					obj.getScore(Bukkit.getOfflinePlayer("§eTop Players:" )).setScore(3);
					
					Comandos.kills.entrySet().stream().sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
					.forEach(k -> obj.getScore(Bukkit.getOfflinePlayer("§c  " + k.getKey().getDisplayName() + ": §f" + k.getValue())).setScore(2));
					// obj.getScore(Bukkit.getOfflinePlayer("")).setScore(4);
					// obj.getScore(Bukkit.getOfflinePlayer("")).setScore(3);
					// obj.getScore(Bukkit.getOfflinePlayer("")).setScore(2);
					// obj.getScore(Bukkit.getOfflinePlayer("")).setScore(1);
					// obj.getScore(Bukkit.getOfflinePlayer("")).setScore(0);
					board.registerNewTeam("1").addPlayer(Bukkit.getOfflinePlayer("1"));
					board.registerNewTeam("2").addPlayer(Bukkit.getOfflinePlayer("2"));
					board.registerNewTeam("3").addPlayer(Bukkit.getOfflinePlayer("3"));
					board.registerNewTeam("4").addPlayer(Bukkit.getOfflinePlayer("4"));
					p.setScoreboard(board);
				}
			}
		}, 1L, 35L);
	}
	
	public static double getRandomChance(){
	    double x = (Math.random()*((100-10)+1))+10;
	    return x;
	}
	
	public void AddItem(InventoryHolder holder, ItemStack item) {
		holder.getInventory().addItem(item);
	}
	
	public void AddItemChance(InventoryHolder holder, ItemStack item, double chance) {
		if ((Math.random() * 100) <= chance) {
			holder.getInventory().addItem(item);
		}
	}

	public void CarregarBaus() {
		for (String get : Main.pl.locais.getKeys(true)) {
			// ILHAS
			if (get.contains("Ilhas.Baus.")) {
				int bau_numero = Integer.valueOf(get.replace("Ilhas.Baus.", ""));
				InventoryHolder holder = (InventoryHolder) Main.pl.locais.getLocation("Ilhas.Baus." + bau_numero).getBlock().getState();
				holder.getInventory().clear();

				if ((Math.random() * 100) <= 50) {
					AddItem(holder, new Item(Material.IRON_SWORD).build());
				} else {
					AddItem(holder, new Item(Material.STONE_SWORD).build());
				}

				AddItem(holder, new Item(Material.GOLDEN_APPLE).setAmount(10).build());
				AddItem(holder, new Item(Material.COOKED_BEEF).setAmount(16).build());
				AddItem(holder, new Item(Material.BOW).build());
				AddItem(holder, new Item(Material.ARROW).setAmount(8).build());

				if ((Math.random() * 100) <= 50) {
					AddItem(holder, new Item(Material.IRON_CHESTPLATE).build());
				} else {
					AddItem(holder, new Item(Material.LEATHER_CHESTPLATE).build());
				}
				
				if ((Math.random() * 100) <= 50) {
					AddItem(holder, new Item(Material.IRON_CHESTPLATE).build());
				} else {
					AddItem(holder, new Item(Material.LEATHER_CHESTPLATE).build());
				}

				if ((Math.random() * 100) <= 50) {
					AddItem(holder, new Item(Material.IRON_LEGGINGS).build());
				} else {
					AddItem(holder, new Item(Material.LEATHER_LEGGINGS).build());
				}
				if ((Math.random() * 100) <= 50) {
					AddItem(holder, new Item(Material.CHAINMAIL_HELMET).build());
				}
				if ((Math.random() * 100) <= 50) {
					AddItem(holder, new Item(Material.CHAINMAIL_BOOTS).build());
				}
				AddItem(holder, new Item(Material.COBBLESTONE).setAmount(16).build());
				AddItemChance(holder, new Item(Material.ENDER_PEARL).setAmount(1).build(), 2);
			}
			// FEAST
			if (get.contains("Ilhas.Baus_Feast.")) {
				int bau_numero = Integer.valueOf(get.replace("Ilhas.Baus_Feast.", ""));
				InventoryHolder holder = (InventoryHolder) Main.pl.locais.getLocation("Ilhas.Baus_Feast." + bau_numero).getBlock().getState();
				holder.getInventory().clear();
				
				AddItemChance(holder, new Item(Material.COBBLESTONE).setAmount(64).build(), 50);
				AddItemChance(holder, new Item(Material.DIAMOND_SWORD).build(), getRandomChance());
				AddItemChance(holder, new Item(Material.GOLDEN_APPLE).setAmount(10).build(), getRandomChance());
				AddItemChance(holder, new Item(Material.GOLDEN_APPLE).setAmount(6).build(), getRandomChance());
				AddItemChance(holder, new Item(Material.DIAMOND_CHESTPLATE).build(), 25);
				AddItemChance(holder, new Item(Material.ARROW).setAmount(64).build(), 50);
				AddItemChance(holder, new Item(Material.ARROW).setAmount(64).build(), 50);
				AddItemChance(holder, new Item(Material.BUCKET).build(), getRandomChance());
				AddItemChance(holder, new Item(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 1).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).build(), 25);
				AddItemChance(holder, new Item(Material.SLIME_BALL).addEnchant(Enchantment.KNOCKBACK, 5).build(), 10);
				AddItemChance(holder, new Item(Material.DIAMOND_BOOTS).build(), 25);
				AddItemChance(holder, new Item(Material.SNOW_BALL).setAmount(16).build(), 50);
				AddItemChance(holder, new Item(Material.SNOW_BALL).setAmount(16).build(), 50);
				AddItemChance(holder, new Item(Material.EGG).setAmount(16).build(), 50);
				AddItemChance(holder, new Item(Material.EGG).setAmount(16).build(), 50);
				AddItemChance(holder, new Item(Material.FISHING_ROD).build(), 50);
				AddItemChance(holder, new Item(Material.ENDER_PEARL).setAmount(3).build(), 100);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("sw")) {
			if (args.length == 0) {
				if (p.hasPermission("lightskywars.admin")) {
					p.sendMessage("");
					p.sendMessage("§6  /sw entrar §7- Entra em uma partida");
					p.sendMessage("§6  /sw iniciar §7- Inicia uma partida");
					p.sendMessage("§6  /sw setlobby §7- Seta o lobby do skywars");
					p.sendMessage("§6  /sw setspawn <numero> §7- Seta os spawn");
					p.sendMessage("§6  /sw setbau <numero> §7- Seta os baus do mapa");
					p.sendMessage("§6  /sw setfbau <numero> §7- Seta os baus do feast");
					p.sendMessage("§6  /sw cancelar §7- Cancela um skywars");
					p.sendMessage("");
				} else {
					p.sendMessage("§6Utilize /sw <entrar/sair>");
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("entrar")) {
					if (havendo_skywars) {
						if (fechado == false) {
							if (!entrou.contains(p)) {
								if(!inventoryIsEmpty(p)){
									p.sendMessage("§9[SkyWars] §7Para participar deixe seu inventário vazio.");
								} else {
									entrou.add(p);
									teleportar.add(p);
									kills.put(p, 0);
									SetarScoreboard(p);
									p.setHealth(20);
									p.setFoodLevel(20);
									p.setFlying(false);
									p.setAllowFlight(false);
									p.setGameMode(GameMode.SURVIVAL);
									p.sendMessage("§9[SkyWars] §7Você entrou no SkyWars!");
									p.teleport(Main.pl.locais.getLocation("Lobby"));
								}
							} else {
								p.sendMessage("§9[SkyWars] §7Para sair do SkyWars digite §c/sw sair");
							}
						} else {
							p.sendMessage("§9[SkyWars] §7Não é possivel entrar no SkyWars mais...");
						}
					} else {
						p.sendMessage("§cNão há nenhum SkyWars acontecendo.");
					}
				} else if (args[0].equalsIgnoreCase("sair")) {
					if (havendo_skywars) {
						if (entrou.contains(p)) {
							entrou.remove(p);
							teleportar.remove(p);
							kills.remove(p);
							p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
							p.sendMessage("§9[SkyWars] §7Você saiu do SkyWars.");
							p.teleport(Bukkit.getWorld("world").getSpawnLocation());
							Eventos.CheckWin();
						} else {
							p.sendMessage("§9[SkyWars] §7Você não está no SkyWars.");
						}
					} else {
						p.sendMessage("§cNão há nenhum SkyWars acontecendo.");
					}
				} else if (args[0].equalsIgnoreCase("iniciar")) {
					if (p.hasPermission("lightskywars.admin")) {
						if (!havendo_skywars) {
							p.sendMessage("§4[SkyWars] §7Você iniciou um SkyWars!");
							Bukkit.getWorld("sw_deserto").setPVP(false);
							CarregarBaus();

							havendo_skywars = true;
							fechado = false;
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§9[SkyWars] §eSkyWars foi aberto!");
							Bukkit.broadcastMessage("§9[SkyWars] §eMapa: §fDeserto");
							Bukkit.broadcastMessage("§9[SkyWars] §eUtilize §f/sw entrar");
							Bukkit.broadcastMessage("§9[SkyWars] §eCompetidores: §f0");
							Bukkit.broadcastMessage("§9[SkyWars] §eIniciando em §f40 §esegundos");
							Bukkit.broadcastMessage("");

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.pl, new Runnable() {
								public void run() {
									Bukkit.broadcastMessage("");
									Bukkit.broadcastMessage("§9[SkyWars] §eSkyWars foi aberto!");
									Bukkit.broadcastMessage("§9[SkyWars] §eMapa: §fDeserto");
									Bukkit.broadcastMessage("§9[SkyWars] §eUtilize §f/sw entrar");
									Bukkit.broadcastMessage("§9[SkyWars] §eCompetidores: §f" + entrou.size());
									Bukkit.broadcastMessage("§9[SkyWars] §eIniciando em §f15 §esegundos");
									Bukkit.broadcastMessage("");
								}
							}, 20 * 30);
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.pl, new Runnable() {
								public void run() {
									if (entrou.size() > 1) {
										fechado = true;
										Bukkit.broadcastMessage("");
										Bukkit.broadcastMessage("§9[SkyWars] §eSkyWars iniciando!");
										Bukkit.broadcastMessage("§9[SkyWars] §eMapa: §fDeserto");
										Bukkit.broadcastMessage("§9[SkyWars] §eCompetidores: §f" + entrou.size());
										Bukkit.broadcastMessage("§9[SkyWars] §eBoa sorte e cuidado, §4PvP LIGADO§e!");
										Bukkit.broadcastMessage("");
										for (int i = 0; i < teleportar.size(); i++) {
											teleportar.get(i).getPlayer().teleport(Main.pl.locais.getLocation("Ilhas.Spawn." + i));
											teleportar.get(i).getPlayer().sendMessage("§9[SkyWars] §7Teleportado para morrer >:)");
										}
										teleportar.clear();
										Bukkit.getWorld("sw_deserto").setPVP(true);
									} else {
										fechado = true;
										havendo_skywars = false;
										Bukkit.broadcastMessage("");
										Bukkit.broadcastMessage("§9[SkyWars] §eSkyWars cancelado!");
										Bukkit.broadcastMessage("§9[SkyWars] §eMapa: §fDeserto");
										Bukkit.broadcastMessage("§9[SkyWars] §eNão havia jogadores suficientes");
										Bukkit.broadcastMessage("");
										for (Player player : entrou) {
											player.sendMessage("§9[SkyWars] §7SkyWars foi §cCancelado§7 pos não havia players, infelizmente.");
											player.teleport(Bukkit.getWorld("world").getSpawnLocation());
											entrou.remove(player);
											player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
										}
										teleportar.clear();
										entrou.clear();
										kills.clear();
										Bukkit.getWorld("sw_deserto").setPVP(false);
									}
								}
							}, 20 * 45);
						} else {
							p.sendMessage("§4[SkyWars] §7Está acontecendo um SkyWars no momento, para cancelar digite §f/sw cancelar");
						}
					}
				} else if (args[0].equalsIgnoreCase("cancelar")) {
					if (p.hasPermission("lightskywars.admin")) {
						if (havendo_skywars) {
							fechado = true;
							havendo_skywars = false;
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§9[SkyWars] §eSkyWars cancelado!");
							Bukkit.broadcastMessage("§9[SkyWars] §eMapa: §fDeserto");
							Bukkit.broadcastMessage("§9[SkyWars] §eCompetidores: §f" + entrou.size());
							Bukkit.broadcastMessage("§9[SkyWars] §eAté a proxima :/");
							Bukkit.broadcastMessage("");
							Bukkit.getWorld("sw_deserto").setPVP(false);
							for (Player player : entrou) {
								entrou.remove(player);
								teleportar.remove(player);
								player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
								player.sendMessage("§9[SkyWars] §7SkyWars foi §cCancelado§7 infelizmente.");
								player.teleport(Bukkit.getWorld("world").getSpawnLocation());
							}
							teleportar.clear();
							entrou.clear();
							kills.clear();
							Main.pl.getServer().getScheduler().cancelTasks(Main.pl);
						} else {
							p.sendMessage("§cNão há nenhum SkyWars acontecendo.");
						}
					}
				} else if (args[0].equalsIgnoreCase("setlobby")) {
					if (p.hasPermission("lightskywars.admin")) {
						Main.pl.locais.setLocation("Lobby", p.getLocation());
						Main.pl.locais.save();
						Main.pl.locais.reload();
						p.sendMessage("§4[SkyWars] §7Lobby setado!");
					}
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("setspawn")) {
					if (p.hasPermission("lightskywars.admin")) {
						int numero_spawn = Integer.valueOf(args[1]);
						Main.pl.locais.setLocation("Ilhas.Spawn." + numero_spawn, p.getLocation());
						Main.pl.locais.save();
						Main.pl.locais.reload();
						p.sendMessage("§4[SkyWars] §7Spawn §f" + numero_spawn + " §7setado!");
					}
				} else if (args[0].equalsIgnoreCase("setbau")) {
					if (p.hasPermission("lightskywars.admin")) {
						int numero_bau = Integer.valueOf(args[1]);
						p.getLocation().getBlock().setType(Material.CHEST);
						Main.pl.locais.setLocation("Ilhas.Baus." + numero_bau, p.getLocation());
						Main.pl.locais.save();
						Main.pl.locais.reload();
						p.sendMessage("§4[SkyWars] §7Bau §f" + numero_bau + " §7setado!");
					}
				} else if (args[0].equalsIgnoreCase("setfbau")) {
					if (p.hasPermission("lightskywars.admin")) {
						int numero_bau = Integer.valueOf(args[1]);
						p.getLocation().getBlock().setType(Material.CHEST);
						Main.pl.locais.setLocation("Ilhas.Baus_Feast." + numero_bau, p.getLocation());
						Main.pl.locais.save();
						Main.pl.locais.reload();
						p.sendMessage("§4[SkyWars] §7Bau §f" + numero_bau + " §7 do Feast setado!");
					}
				}
			}
			return false;
		}
		return false;
	}
}
