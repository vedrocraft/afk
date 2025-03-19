package ru.sema1ary.afk;

import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.afk.command.AfkCommand;
import ru.sema1ary.afk.listener.JoinListener;
import ru.sema1ary.afk.listener.MoveListener;
import ru.sema1ary.afk.listener.QuitListener;
import ru.sema1ary.afk.placeholder.AfkPlaceholder;
import ru.sema1ary.afk.service.AfkService;
import ru.sema1ary.afk.service.impl.AfkServiceImpl;
import ru.sema1ary.vedrocraftapi.BaseCommons;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

public final class Afk extends JavaPlugin implements BaseCommons {
    @Override
    public void onEnable() {
        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        ServiceManager.registerService(AfkService.class, new AfkServiceImpl(
                getService(ConfigService.class)
        ));

        getServer().getPluginManager().registerEvents(new JoinListener(
                getService(AfkService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new QuitListener(
                getService(AfkService.class)
        ), this);

        getServer().getPluginManager().registerEvents(new MoveListener(
                getService(AfkService.class)
        ), this);

        LiteCommandBuilder.builder()
                .commands(new AfkCommand(
                        getService(AfkService.class)
                ))
                .build();

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new AfkPlaceholder(
                    getService(AfkService.class)
            ).register();
        }
    }

    @Override
    public void onDisable() {
        ServiceManager.disableServices();
    }

}
