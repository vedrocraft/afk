package ru.sema1ary.afk.service;

import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.sema1ary.afk.model.AfkUser;
import ru.sema1ary.vedrocraftapi.service.UserService;

public interface AfkUserService extends UserService<AfkUser> {
    void changeAfk(@NonNull AfkUser user);

    void changeAfk(@NonNull Player player);
}
