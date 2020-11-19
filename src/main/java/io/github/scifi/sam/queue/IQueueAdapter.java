package io.github.scifi.sam.queue;

import org.bukkit.entity.Player;

public interface IQueueAdapter {

    public void addToQueue(String queue, Player player);

    public int getQueueSize(Player player);

    public int getQueueSize(String queue);

    public int getQueuePosition(Player player);

    public String getQueue(Player player);

    public String getConfigName();

}
