package ru.sema1ary.afk.service;

import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.sema1ary.vedrocraftapi.service.Service;

public interface AfkService extends Service {
    void registerPlayer(@NonNull Player player);

    void unregisterPlayer(@NonNull Player player);

    boolean isPlayerInAfk(@NonNull Player player);

    void changeAfk(@NonNull Player player);
}
