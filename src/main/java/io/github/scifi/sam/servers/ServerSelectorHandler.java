package io.github.scifi.sam.servers;

import io.github.scifi.sam.utils.Config;
import io.github.scifi.sam.utils.ItemBuilder;
import io.github.scifi.sam.Sam;
import io.github.scifi.sam.utils.CC;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ServerSelectorHandler {

    private Sam plugin = Sam.getPlugin();

    private final ItemStack serverSelector;

    private final ItemStack fillItem;

    private final Config config;

    public ServerSelectorHandler() {
        config = new Config("selector", plugin, plugin.getDataFolder().getAbsolutePath());
        this.serverSelector = new ItemBuilder(Material.getMaterial(config.getString("selector-type")), config.getInt("selector-amount"))
                .setName(CC.chat(config.getString("selector-title")))
                .setLore(CC.chat(config.getStringList("selector-lore")))
                .build();

        this.fillItem = new ItemBuilder(Material.getMaterial(config.getString("fill-type")), config.getInt("fill-amount"))
                .setData(config.getInt("fill-data"))
                .build();
    }

    public Inventory getServerSelectorInventory(Player player) {
        Inventory inventory = Bukkit.getServer().createInventory(null, config.getInt("selector-size"), CC.chat(config.getString("selector-title")));
        fillInventory(inventory);

        for (String path : config.getConfigurationSection("servers").getKeys(false)) {
            ItemStack itemStack = new ItemBuilder(Material.getMaterial(config.getString("servers." + path + ".type")), config.getInt("servers." + path + ".amount"))
                    .setName(CC.chat(config.getString("servers." + path + ".name")))
                    .setLore(PlaceholderAPI.setPlaceholders(player, config.getStringList("servers." + path + ".lore")))
                    .build();

            inventory.setItem(config.getInt("servers." + path + ".slot"), itemStack);
        }

        return inventory;
    }

    public void fillInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, getFillItem());
        }
    }

}
