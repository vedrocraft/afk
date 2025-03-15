package ru.sema1ary.afk.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import ru.sema1ary.afk.model.AfkUser;
import ru.sema1ary.afk.service.AfkUserService;

@RequiredArgsConstructor
public class PreJoinListener implements Listener {
    private final AfkUserService userService;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        String username = event.getName();

        if(username.isEmpty()) {
            return;
        }

        if(userService.findByUsername(username).isEmpty()) {
            userService.save(AfkUser.builder()
                    .username(username)
                    .isAfk(false)
                    .build());
        }
    }
}
