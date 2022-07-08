package studio.krykher.shush;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shush implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Shush");

    @Override
    public void onInitialize() {
        LOGGER.info("Registering Commands");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> UtterCommand.register(dispatcher));
        LOGGER.info("Finished registering commands");
    }
}
