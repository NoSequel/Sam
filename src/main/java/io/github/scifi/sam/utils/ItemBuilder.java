package io.github.scifi.sam.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.List;

/**
 * This class is used for creating new ItemStack objects with ease
 */
public class ItemBuilder {

    //The ItemStack object
    private final ItemStack stack;

    //The ItemMeta object
    private final ItemMeta meta;

    /**
     * The constructor which creates a new ItemBuilder instance
     */
    public ItemBuilder() {
        this.stack = new ItemStack(Material.AIR, 1);
        this.meta = this.stack.getItemMeta();
    }

    /**
     * The constructor which creates a new ItemBuilder instance
     * @param material The material to be used for the ItemStack
     */
    public ItemBuilder(Material material) {
        this.stack = new ItemStack(material, 1);
        this.meta = this.stack.getItemMeta();
    }

    /**
     * The constructor which creates a new ItemBuilder instance
     * @param material The material to be used for the ItemStack
     * @param amount The amount to be used for the ItemStack
     */
    public ItemBuilder(Material material, int amount) {
        this.stack = new ItemStack(material, amount);
        this.meta = this.stack.getItemMeta();
    }

    /**
     *
     * @param material The {@link Material} to be set
     * @return The {@link ItemBuilder} instance
     */
    public ItemBuilder setType(Material material) {
        this.stack.setType(material);
        return this;
    }

    /**
     *
     * @param amount The {@link Integer} to be set as the amount
     * @return The {@link ItemBuilder} instance
     */
    public ItemBuilder setAmount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }

    /**
     *
     * @param name The {@link String} to be set as the name
     * @return The {@link ItemBuilder} instance
     */
    public ItemBuilder setName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    /**
     *
     * @param lore The {@link List<String>} to be set as the lore
     * @return The {@link ItemBuilder} instance
     */
    public ItemBuilder setLore(List<String> lore) {
        this.meta.setLore(CC.chat(lore));
        return this;
    }

    /**
     *
     * @param data The {@link Integer} to be set as the data
     * @return The {@link ItemBuilder} instance
     */
    public ItemBuilder setData(int data) {
        this.stack.setData(new MaterialData(data));
        return this;
    }

    /**
     *
     * @return The constructed {@link ItemStack} instance
     */
    public ItemStack build() {
        this.stack.setItemMeta(this.meta);
        return this.stack;
    }



}
