package studio.krykher.shush;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;


import static net.minecraft.command.argument.EntityArgumentType.getPlayer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class UtterCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(literal("utter")
                .then(argument("targets", EntityArgumentType.player())
                        .executes(context -> execute(context.getSource(),
                                getPlayer(context, "targets")))));
        dispatcher.register(literal("u").redirect(literalCommandNode));
    }

    private static int execute(ServerCommandSource source,
                               ServerPlayerEntity target) {
        if (target == null) {
            return 0;
        }
        MutableText startingMsg = Text.translatable("commands.shush.utter.feedback", target.getDisplayName());
        startingMsg.setStyle(startingMsg.getStyle().withColor(0xF23A66));
        source.sendFeedback(startingMsg, false);
        target.sendMessage(startingMsg, false);
        return 1;
    }
}
