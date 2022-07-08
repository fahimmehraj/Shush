package studio.krykher.shush.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class ShushClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Shush");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Shush for client is initializing");
    }
}
