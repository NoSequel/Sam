package io.github.thatkawaiisam.assemble.impl;

import io.github.scifi.sam.Sam;
import io.github.scifi.sam.utils.Config;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardAdapter implements AssembleAdapter {

    private Sam plugin = Sam.getPlugin();

    private final Config config = new Config("scoreboard", plugin, plugin.getDataFolder().getAbsolutePath());

    @Override
    public String getTitle(Player player) {
        return config.getString("scoreboard-title");
    }

    @Override
    public List<String> getLines(Player player) {
        return config.getStringList("scoreboard-lines")
                .stream().map(line -> PlaceholderAPI.setPlaceholders(player, line)
                .replace("%rank%", plugin.getRankHandler().getRankName(player))).collect(Collectors.toList());
    }
}
