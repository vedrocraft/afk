package ru.sema1ary.afk.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.sema1ary.afk.service.AfkService;

@RequiredArgsConstructor
public class MoveListener implements Listener {
    private final AfkService afkService;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(afkService.isPlayerInAfk(player)) {
            afkService.changeAfk(player);
        }
    }
}
