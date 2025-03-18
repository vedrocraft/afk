package ru.sema1ary.afk.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.sema1ary.afk.service.AfkService;

@RequiredArgsConstructor
public class JoinListener implements Listener {
    private final AfkService afkService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        afkService.registerPlayer(event.getPlayer());
    }
}
