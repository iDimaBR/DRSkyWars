package com.idimabr.lightskywars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.idimabr.lightskywars.comandos.Comandos;
import com.idimabr.lightskywars.eventos.Eventos;
import com.idimabr.lightskywars.util.FlatFile;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public static Main pl;
	public static Economy econ = null;
	
	public FlatFile locais = new FlatFile("locais.yml", this);
	
	private boolean setupEconomy() {
	    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	    if (economyProvider != null) {
	        econ = economyProvider.getProvider();
	    }

	    return (econ != null);
	}
	
	
	public void onEnable() {
		setupEconomy();
		pl = this;
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[LightSkyWars] Ativado com sucesso! by iDimaBR");
		Bukkit.getConsoleSender().sendMessage("");
		getServer().getPluginManager().registerEvents(new Eventos(), this);
		getCommand("sw").setExecutor(new Comandos());
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getWorld("sw_deserto").setAutoSave(false);
				Bukkit.getConsoleSender().sendMessage("");
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[LightSkyWars] §aMUNDO SKYWARS REMOVIDO DO SAVE COM SUCESSO!");
				Bukkit.getConsoleSender().sendMessage("");
			}
		}.runTaskLater(this, 20 * 20);
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[LightSkyWars] Desativado com sucesso! by iDimaBR");
		Bukkit.getConsoleSender().sendMessage("");
	}
}
