package io.github.scifi.sam;

import com.qrakn.honcho.Honcho;
import io.github.scifi.sam.commands.BuildCommand;
import io.github.scifi.sam.commands.SetSpawnCommand;
import io.github.scifi.sam.listeners.PlayerListener;
import io.github.scifi.sam.ranks.RankHandler;
import io.github.scifi.sam.servers.ServerSelectorHandler;
import io.github.scifi.sam.utils.Config;
import io.github.scifi.sam.build.BuildHandler;
import io.github.scifi.sam.queue.QueueHandler;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import io.github.thatkawaiisam.assemble.impl.ScoreboardAdapter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * The main class of Sam
 */
@Getter
public class Sam extends JavaPlugin {

   @Getter private static Sam plugin;

   private Config config;

   private RankHandler rankHandler;

   private QueueHandler queueHandler;

   private BuildHandler buildHandler;

   private ServerSelectorHandler serverSelectorHandler;

   private Honcho honcho;


   //Handles the startup tasks
   public void onEnable() {
       plugin = this;
       registerConfigs();
       registerHandlers();
       registerCommands();
       registerListeners();
   }

   //Registers the configs
   private void registerConfigs() {
       this.config = new Config("config", this, this.getDataFolder().getAbsolutePath());
   }

   //Registers the handlers
   private void registerHandlers() {
       this.queueHandler = new QueueHandler();
       this.rankHandler = new RankHandler();
       this.buildHandler = new BuildHandler();
       this.serverSelectorHandler = new ServerSelectorHandler();
       new Assemble(this, new ScoreboardAdapter()).setAssembleStyle(AssembleStyle.KOHI);

   }

   //Registers all of the commands
   private void registerCommands() {
       this.honcho = new Honcho(this); 

       Arrays.asList(new SetSpawnCommand(),new BuildCommand())
               .forEach(honcho::registerCommand);
   }

   //Registers all of the listener's
   private void registerListeners() {
       PluginManager manager = Bukkit.getServer().getPluginManager();

       manager.registerEvents(new PlayerListener(), this);
   }

}
