package ru.sema1ary.afk.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import ru.sema1ary.afk.model.AfkUser;
import ru.sema1ary.afk.service.AfkUserService;

@RequiredArgsConstructor
public class AfkPlaceholder extends PlaceholderExpansion {
    private final AfkUserService userService;

    @Override
    public @NotNull String getIdentifier() {
        return "afk";
    }

    @Override
    public @NotNull String getAuthor() {
        return "";
    }

    @Override
    public @NotNull String getVersion() {
        return "";
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

        AfkUser user = userService.getUser(username);

        if(user.isAfk()) {
            return "&c● ";
        }

        return "&a● ";
    }
}
