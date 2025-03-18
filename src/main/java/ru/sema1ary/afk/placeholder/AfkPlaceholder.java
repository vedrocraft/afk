package ru.sema1ary.afk.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.sema1ary.afk.service.AfkService;

@RequiredArgsConstructor
public class AfkPlaceholder extends PlaceholderExpansion {
    private final AfkService afkService;

    @Override
    public @NotNull String getIdentifier() {
        return "afk";
    }

    @Override
    public @NotNull String getAuthor() {
        return "sema1ary";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String username = player.getName();

        if(username == null || username.isEmpty()) {
            return null;
        }

        Player onlinePlayer = Bukkit.getPlayer(player.getName());

        if(onlinePlayer == null || !onlinePlayer.isOnline()) {
            return null;
        }

        if(afkService.isPlayerInAfk(onlinePlayer)) {
            return "&c● ";
        }

        return "&a● ";
    }
}
