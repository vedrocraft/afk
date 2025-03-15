package ru.sema1ary.afk.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.sema1ary.afk.dao.AfkUserDao;
import ru.sema1ary.afk.model.AfkUser;
import ru.sema1ary.afk.service.AfkUserService;
import ru.sema1ary.vedrocraftapi.player.PlayerUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AfkUserServiceImpl implements AfkUserService {
    private final AfkUserDao userDao;
    private final ConfigService configService;

    @Override
    public AfkUser save(@NonNull AfkUser user) {
        try {
            return userDao.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(@NonNull List<AfkUser> list) {
        try {
            userDao.saveAll(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AfkUser> findById(Long aLong) {
        try {
            return userDao.findById(aLong);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AfkUser> findByUsername(@NonNull String s) {
        try {
            return userDao.findByUsername(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AfkUser> findAll() {
        try {
            return userDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AfkUser getUser(@NonNull String s) {
        return findByUsername(s).orElseGet(() -> save(AfkUser.builder()
                .username(s)
                .isAfk(false)
                .build()));
    }

    @Override
    public void changeAfk(@NonNull AfkUser user) {
        Player player = Bukkit.getPlayer(user.getUsername());
        if(player != null && player.isOnline()) {
            changeUserAfkStatus(player);
        }
    }

    @Override
    public void changeAfk(@NonNull Player player) {
        changeUserAfkStatus(player);
    }

    private void changeUserAfkStatus(@NonNull Player player) {
        AfkUser user = getUser(player.getName());
        if(user.isAfk()) {
            user.setAfk(false);
            PlayerUtil.sendMessage(player, (String) configService.get("disable-afk-message"));
            PlayerUtil.resetTitle(player);
        } else {
            user.setAfk(true);
            PlayerUtil.sendMessage(player, (String) configService.get("enable-afk-message"));
            PlayerUtil.showInfinityTitle(player,
                    configService.get("title"),
                    configService.get("subtitle"));
        }
        save(user);
    }
}
