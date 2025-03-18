package ru.sema1ary.afk.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.sema1ary.afk.service.AfkService;

@RequiredArgsConstructor
public class QuitListener implements Listener {
    private final AfkService afkService;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        afkService.unregisterPlayer(event.getPlayer());
    }
}
