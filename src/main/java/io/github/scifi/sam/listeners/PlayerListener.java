package io.github.scifi.sam.listeners;

import io.github.scifi.sam.utils.CC;
import io.github.scifi.sam.utils.Config;
import io.github.scifi.sam.Sam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {

    private final Sam plugin = Sam.getPlugin();

    private final boolean isLocked = true;

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (plugin.getBuildHandler().canBuild(player.getUniqueId())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (plugin.getBuildHandler().canBuild(player.getUniqueId())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (event.getInitiator().getViewers().get(0) == null || !(event.getSource() instanceof PlayerInventory) || !(event.getInitiator().getHolder() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getInitiator().getHolder();

        if (plugin.getBuildHandler().canBuild(player.getUniqueId()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        Bukkit.getServer().getOnlinePlayers().forEach(player::hidePlayer);

        Bukkit.getServer().getOnlinePlayers().forEach(player1 -> player1.hidePlayer(player));

        event.setJoinMessage(null);

        CC.chat(plugin.getConfig().getStringList("join-message"))
                .stream()
                .map(string -> string.replace("%player%", player.getName()))
                .forEach(player::sendMessage);

        player.setGameMode(GameMode.SURVIVAL);

        player.setFoodLevel(20);

        player.getInventory().clear();

        player.getInventory().setItem(4, plugin.getServerSelectorHandler().getServerSelector());

        player.teleport(player.getWorld().getSpawnLocation());

    }

//    @EventHandler
//    public void onRightClickBlock(InventoryOpenEvent event) {
//
//        Inventory inventory = event.getInventory();
//        Player player = (Player) event.getPlayer();
//
//        if (player == null || event.getInventory() == null) return;
//
//        if (Stream.of(InventoryType.CHEST, InventoryType.ENDER_CHEST, InventoryType.ANVIL, InventoryType.FURNACE,
//                InventoryType.BEACON, InventoryType.ENCHANTING, InventoryType.DISPENSER, InventoryType.BREWING)
//                .anyMatch(inventoryType -> !inventory.getType().equals(inventoryType))) return;
//
//        if (plugin.getBuildHandler().canBuild(player.getUniqueId())) {
//            return;
//        }
//
//        event.setCancelled(true);
//        player.closeInventory();
//
//    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!event.getAction().name().contains("RIGHT"))
            return;

        if (event.getItem() == null)
            return;

        if (!event.getItem().isSimilar(plugin.getServerSelectorHandler().getServerSelector()))
            return;

        player.openInventory(plugin.getServerSelectorHandler().getServerSelectorInventory(player));

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getInventory() == null)
            return;

        if (!event.getInventory().getTitle().equalsIgnoreCase(CC.chat(plugin.getServerSelectorHandler().getConfig().getString("selector-title"))))
            return;

        event.setCancelled(true);

        if (!event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasDisplayName())
            return;


        Config config = plugin.getServerSelectorHandler().getConfig();

        for (String path : config.getConfigurationSection("servers").getKeys(false)) {
            if (!CC.chat(config.getString("servers." + path + ".name")).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()))
                continue;

            try {
                plugin.getQueueHandler().placeInQueue(config.getString("servers." + path + ".queue"), player);
                player.closeInventory();
            } catch (Exception exception) {
                player.sendMessage(CC.chat("&cFailed to connect you to that queue."));
            }
        }

    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (plugin.getBuildHandler().canBuild(player.getUniqueId()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if (plugin.getBuildHandler().canBuild(player.getUniqueId()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!event.getAction().equals(Action.PHYSICAL))
            return;

        if (plugin.getBuildHandler().canBuild(player.getUniqueId()))
            return;

        if (!event.getClickedBlock().getType().equals(Material.SOIL))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().replace("%", "%%");

        if (isLocked && !player.hasPermission("sam.chat.bypass")) {
            player.sendMessage(CC.chat("&cYou cannot talk in the hub."));
            event.setCancelled(true);
        } else if (isLocked && player.hasPermission("sam.chat.bypass"))
            event.setFormat(CC.chat(plugin.getRankHandler().getPrefix(player) + " " + player.getName() + "&8: &f") + message);

        else
            event.setFormat(CC.chat(plugin.getRankHandler().getPrefix(player) + " " + player.getName() + "&8: &f") + message);


    }
}
