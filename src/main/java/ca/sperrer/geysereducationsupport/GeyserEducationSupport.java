package ca.sperrer.geysereducationsupport;

import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.command.Command;
import org.geysermc.geyser.api.command.CommandSource;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCommandsEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;

import java.io.File;

public class GeyserEducationSupport implements Extension {
    private static TokenManager tokenManager;

    public static TokenManager getTokenManager() {
        return tokenManager;
    }

    @Subscribe
    public void onGeyserDefineCommandsEvent(GeyserDefineCommandsEvent event) {
        Command command = Command.builder(this)
                .source(CommandSource.class)
                .name("education")
                .description("Allow Education Edition users to connect to Geyser")
                .permission("geysereducationsupport.command.education")
                .suggestedOpOnly(true)
                .executableOnConsole(true)
                .executor(new EducationCommand<>())
                .build();

        event.register(command);
    }

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        this.logger().info("Loading GeyserEducationSupport extension...");

        File tokenFile = new File(this.dataFolder().toFile(), "tokens.yml");
        if (!tokenFile.exists()) {
            this.logger().info("Token file does not exist, creating...");
            try {
                if (!tokenFile.getParentFile().exists()) {
                    tokenFile.getParentFile().mkdirs();
                }
                tokenFile.createNewFile();
            } catch (Exception e) {
                this.logger().error("Failed to create token file", e);
                return;
            }
        }
        tokenManager = new TokenManager(tokenFile);
    }
}
