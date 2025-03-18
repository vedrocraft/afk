package ru.sema1ary.afk.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.sema1ary.afk.service.AfkService;
import ru.sema1ary.vedrocraftapi.player.PlayerUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
public class AfkServiceImpl implements AfkService {
    private final ConfigService configService;
    private final Map<String, Boolean> playerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    @Override
    public void registerPlayer(@NonNull Player player) {
        playerMap.put(player.getName(), false);
    }

    @Override
    public void unregisterPlayer(@NonNull Player player) {
        playerMap.remove(player.getName());
    }

    @Override
    public boolean isPlayerInAfk(@NonNull Player player) {
        return playerMap.get(player.getName());
    }

    @Override
    public void changeAfk(@NonNull Player player) {
        if(isPlayerInAfk(player)) {
            playerMap.replace(player.getName(), false);

            PlayerUtil.sendMessage(player, (String) configService.get("disable-afk-message"));
            PlayerUtil.resetTitle(player);
            return;
        }

        playerMap.replace(player.getName(), true);

        PlayerUtil.sendMessage(player, (String) configService.get("enable-afk-message"));
        PlayerUtil.showInfinityTitle(player,
                configService.get("title"),
                configService.get("subtitle"));
    }
}
