package io.github.scifi.sam.commands;

import com.qrakn.honcho.command.CommandMeta;
import io.github.scifi.sam.Sam;
import io.github.scifi.sam.utils.CC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandMeta(label = "setspawn", permission = "sam.setspawn")
public class SetSpawnCommand {

    private final Sam plugin = Sam.getPlugin();

    public void execute(Player player) {
        Location location = player.getLocation();
        player.getWorld().setSpawnLocation(location.getBlockX(),  location.getBlockY(), location.getBlockZ());
        player.sendMessage(CC.chat("&eSpawn point updated."));
    }

}
