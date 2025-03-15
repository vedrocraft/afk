package ru.sema1ary.afk.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.sema1ary.afk.model.AfkUser;
import ru.sema1ary.afk.service.AfkUserService;

@RequiredArgsConstructor
public class MoveListener implements Listener {
    private final AfkUserService userService;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        AfkUser user = userService.getUser(event.getPlayer().getName());

        if(user.isAfk()) {
            userService.changeAfk(user);
        }
    }
}
