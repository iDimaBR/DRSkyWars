package com.idimabr.lightskywars.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;

public class Item
{
    ItemStack item;

    public Item(Material material)
    {
        this.item = new ItemStack(material);
    }

    public Item setType(Material material)
    {
        this.item.setType(material);
        return this;
    }

    public Item setAmount(int amount)
    {
        this.item.setAmount(amount);
        return this;
    }

    public Item setDurability(int durability)
    {
    	int durabilidade = Math.round(this.item.getType().getMaxDurability() - durability);
    	this.item.setDurability((short) durabilidade);
        return this;
    }

    public Item addEnchant(Enchantment enchantment, int level)
    {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public Item removeEnchant(Enchantment enchantment)
    {
        this.item.removeEnchantment(enchantment);
        return this;
    }

    public Item setDisplayName(String name)
    {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public Item setLore(List<String> lore)
    {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lore);
        this.item.setItemMeta(meta);
        return this;
    }

    public Item setLore(String... lore)
    {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        this.item.setItemMeta(meta);
        return this;
    }

    public Item setPotion(PotionEffectType type, int level, int tempo)
    {
        this.item = new ItemStack(Material.POTION, 1);

        return this;
    }

    public Item setLeatherColor(Color color)
    {
        LeatherArmorMeta meta = (LeatherArmorMeta)this.item.getItemMeta();
        meta.setColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack build()
    {
        return this.item;
    }
}