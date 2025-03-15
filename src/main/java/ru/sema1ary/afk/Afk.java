package ru.sema1ary.afk;

import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.afk.command.AfkCommand;
import ru.sema1ary.afk.listener.MoveListener;
import ru.sema1ary.afk.listener.PreJoinListener;
import ru.sema1ary.afk.model.AfkUser;
import ru.sema1ary.afk.placeholder.AfkPlaceholder;
import ru.sema1ary.afk.service.AfkUserService;
import ru.sema1ary.afk.service.impl.AfkUserServiceImpl;
import ru.sema1ary.vedrocraftapi.BaseCommons;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.ormlite.ConnectionSourceUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Afk extends JavaPlugin implements BaseCommons {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        initConnectionSource();

        ServiceManager.registerService(AfkUserService.class, new AfkUserServiceImpl(
                getDao(AfkUser.class),
                ServiceManager.getService(ConfigService.class)
        ));

        getServer().getPluginManager().registerEvents(new PreJoinListener(
                ServiceManager.getService(AfkUserService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new MoveListener(
                ServiceManager.getService(AfkUserService.class)
        ), this);

        LiteCommandBuilder.builder()
                .commands(new AfkCommand(
                        ServiceManager.getService(AfkUserService.class)
                ))
                .build();

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new AfkPlaceholder(
                    ServiceManager.getService(AfkUserService.class)
            ).register();
        }
    }

    @Override
    public void onDisable() {
        ConnectionSourceUtil.closeConnection(true);
    }

    @SneakyThrows
    private void initConnectionSource() {
        Path databaseFilePath = Paths.get("plugins/afk/database.sqlite");
        if(!Files.exists(databaseFilePath) && !databaseFilePath.toFile().createNewFile()) {
            return;
        }

        ConnectionSourceUtil.connectNoSQLDatabase(databaseFilePath.toString(), AfkUser.class);
    }
}
