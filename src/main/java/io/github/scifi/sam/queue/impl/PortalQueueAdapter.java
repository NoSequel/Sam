package io.github.scifi.sam.queue.impl;

import io.github.scifi.sam.queue.IQueueAdapter;
import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.entity.Player;

public class PortalQueueAdapter implements IQueueAdapter {

    @Override
    public void addToQueue(String queue, Player player) {
        Queue.getByName(queue).sendPlayer(player, queue);
    }

    @Override
    public int getQueueSize(Player player) {
        return Queue.getByPlayer(player.getUniqueId()).getPlayers().size();
    }

    @Override
    public int getQueueSize(String queue) {
        return Queue.getByName(queue).getPlayers().size();
    }

    @Override
    public int getQueuePosition(Player player) {
        return Queue.getByPlayer(player.getUniqueId()).getPosition(player.getUniqueId());
    }

    @Override
    public String getQueue(Player player) {
        return Queue.getByPlayer(player.getUniqueId()).getName();
    }

    @Override
    public String getConfigName() {
        return "PORTAL";
    }
}
