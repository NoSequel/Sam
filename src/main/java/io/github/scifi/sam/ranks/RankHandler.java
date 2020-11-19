package io.github.scifi.sam.ranks;

import io.github.scifi.sam.ranks.impl.VaultRankAdapter;
import io.github.scifi.sam.Sam;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RankHandler {

    private final Sam plugin = Sam.getPlugin();

    private final List<IRankAdapter> rankAdapters;

    public RankHandler() {
        this.rankAdapters = Arrays.asList(new VaultRankAdapter());
    }

    public IRankAdapter getAdapter() {
        return this.rankAdapters.stream().filter(iRankAdapter -> iRankAdapter.getConfigName().equalsIgnoreCase(plugin.getConfig().getString("permissions-plugin"))).findFirst().orElse(null);
    }

    public String getPrefix(Player player) {
        return getAdapter().getPrefix(player);
    }

    public String getRankName(Player player) {
        return getAdapter().getRankName(player);
    }

}
