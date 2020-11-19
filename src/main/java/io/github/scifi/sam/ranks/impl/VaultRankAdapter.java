package io.github.scifi.sam.ranks.impl;

import io.github.scifi.sam.Sam;
import io.github.scifi.sam.ranks.IRankAdapter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VaultRankAdapter implements IRankAdapter {

    private final Sam plugin = Sam.getPlugin();

    private Chat chat;

    private Permission permission;

    public VaultRankAdapter() {
        if (plugin.getConfig().getString("permissions-plugin").equalsIgnoreCase("VAULT")) {
            this.chat = Bukkit.getServer().getServicesManager().getRegistration(Chat.class).getProvider();
            this.permission = Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        }
    }

    @Override
    public String getPrefix(Player player) {
        return chat.getGroupPrefix(player.getWorld(), permission.getPrimaryGroup(player));
    }

    @Override
    public String getConfigName() {
        return "VAULT";
    }

    @Override
    public String getPrefix(String rank) {
        return null;
    }

    @Override
    public String getSuffix(Player player) {
        return chat.getGroupPrefix(player.getWorld(), permission.getPrimaryGroup(player));
    }

    @Override
    public String getSuffix(String rank) {
        return null;
    }

    @Override
    public String getRankName(Player player) {
        return permission.getPrimaryGroup(player);
    }
}
