package io.github.scifi.sam.build;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The handler that controls if a player can build
 */
@Getter
public class BuildHandler {

    private final Set<UUID> enabled = new HashSet<>();

    /**
     *
     * @param uuid The players {@link UUID}
     * @return If the player can build
     */
    public boolean canBuild(UUID uuid) {
        return enabled.contains(uuid);
    }

}
