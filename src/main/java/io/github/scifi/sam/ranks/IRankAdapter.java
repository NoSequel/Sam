package io.github.scifi.sam.ranks;

import org.bukkit.entity.Player;

public interface IRankAdapter {

    String getPrefix(Player player);

    String getPrefix(String rank);

    String getSuffix(Player player);

    String getSuffix(String rank);

    String getRankName(Player player);

    String getConfigName();

}
