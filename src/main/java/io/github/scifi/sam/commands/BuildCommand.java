package io.github.scifi.sam.commands;

import com.qrakn.honcho.command.CommandMeta;
import io.github.scifi.sam.Sam;
import io.github.scifi.sam.utils.CC;
import org.bukkit.entity.Player;

@CommandMeta(label = "build", permission = "sam.build")
public class BuildCommand {

    private final Sam plugin = Sam.getPlugin();

    public void execute(Player player) {
        if (plugin.getBuildHandler().canBuild(player.getUniqueId())) {
            plugin.getBuildHandler().getEnabled().remove(player.getUniqueId());
            player.sendMessage(CC.chat("&3Insidious &7» &fYou have &cdisabled &fBuild Mode."));
            return;
        }

        plugin.getBuildHandler().getEnabled().add(player.getUniqueId());
        player.sendMessage(CC.chat("&3Insidious &7» &fYou have &aenabled &fBuild Mode."));
    }

}
