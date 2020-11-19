package io.github.scifi.sam.queue;

import io.github.scifi.sam.Sam;
import io.github.scifi.sam.queue.impl.PortalQueueAdapter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class QueueHandler {

    private final List<IQueueAdapter> adapters;

    private final Sam plugin = Sam.getPlugin();

    public QueueHandler() {
        adapters = Arrays.asList(new PortalQueueAdapter());
    }

    public IQueueAdapter getAdapter() {
        return adapters.stream().filter(iQueueAdapter -> iQueueAdapter.getConfigName().equalsIgnoreCase(plugin.getConfig().getString("queue-name"))).findFirst().orElse(null);
    }

    public void placeInQueue(String queue, Player player) {
        getAdapter().addToQueue(queue, player);
    }

    public int getQueueSize(String queue) {
        return getAdapter().getQueueSize(queue);
    }

    public int getQueueSize(Player player) {
        return  getAdapter().getQueueSize(player);
    }

    public int getQueuePosition(Player player) {
        return getAdapter().getQueuePosition(player);
    }

}
