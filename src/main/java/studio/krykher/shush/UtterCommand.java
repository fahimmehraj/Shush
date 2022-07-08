package studio.krykher.shush;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collection;

import static net.minecraft.command.argument.EntityArgumentType.getPlayers;
import static net.minecraft.command.argument.MessageArgumentType.getSignedMessage;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class UtterCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(literal("utter")
                .then(argument("targets", EntityArgumentType.players())
                        .then(argument("message", MessageArgumentType.message())
                                .executes(context -> execute(context.getSource(),
                                        getPlayers(context, "targets"),
                                        getSignedMessage(context, "message"))))));
        dispatcher.register(literal("u").redirect(literalCommandNode));
    }

    private static int execute(ServerCommandSource source,
                               Collection<ServerPlayerEntity> targets,
                               MessageArgumentType.SignedMessage signedMessage) {
        if (targets.isEmpty()) {
            return 0;
        }
        signedMessage.decorate(source).thenAcceptAsync(decoratedMessage -> {
            Text text = decoratedMessage.raw().getContent();

            for (ServerPlayerEntity serverPlayerEntity : targets) {
                source.sendFeedback(Text.translatable("commands.message.display.outgoing", serverPlayerEntity.getDisplayName(), text)
                        .formatted(Formatting.GRAY, Formatting.ITALIC), false);
                SignedMessage utter = decoratedMessage.getFilterableFor(source, serverPlayerEntity);
                if (utter == null) continue;
                serverPlayerEntity.sendChatMessage(utter, source.getChatMessageSender(), MessageType.MSG_COMMAND);
            }
        }, source.getServer());
        return targets.size();
    }
}
