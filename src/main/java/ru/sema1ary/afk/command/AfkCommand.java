package ru.sema1ary.afk.command;

import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.sema1ary.afk.service.AfkService;

@RequiredArgsConstructor
@Command(name = "afk")
public class AfkCommand {
    private final AfkService afkService;

    @Async
    @Execute
    void execute(@Context Player sender) {
        afkService.changeAfk(sender);
    }
}
