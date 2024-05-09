package me.devoxin.obsidian.plugin;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.arbjerg.lavalink.api.AudioPlayerManagerConfiguration;
import lavalink.server.config.HttpConfig;
import lavalink.server.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
public class ObsidianPluginLoader implements AudioPlayerManagerConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ObsidianPluginLoader.class);

    private final ScheduledExecutorService scheduler;
    private final HttpConfig serverHttpConfig;
    private final HttpSourceConfig httpSourceConfig;

    public ObsidianPluginLoader(final ServerConfig serverConfig,
                                final HttpSourceConfig httpSourceConfig) {
        this.serverHttpConfig = serverConfig != null ? serverConfig.getHttpConfig() : null;
        this.httpSourceConfig = httpSourceConfig;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public AudioPlayerManager configure(final AudioPlayerManager audioPlayerManager) {
        enableIfConfigPresentAndEnabled(httpSourceConfig, audioPlayerManager, this::applyHttpSource);
        return audioPlayerManager;
    }

    private void applyHttpSource(final AudioPlayerManager manager) {
        log.info("Scheduling registration of HTTP source manager.");
        // this is a hack to ensure this source manager is registered last.
        // Lavalink currently doesn't have a plugin weighting system and if
        // the http source is registered before any others, it will screw up
        // the loading system causing any sources registered AFTER the HTTP source
        // to never be queried.
        scheduler.schedule(() -> {
            log.info("Registering HTTP source...");
            // TODO
        }, 5, TimeUnit.SECONDS);
    }

    private <T extends Toggleable> void enableIfConfigPresentAndEnabled(final T config,
                                   final AudioPlayerManager manager,
                                   final Consumer<AudioPlayerManager> receiver) {
        if (config != null && config.isEnabled()) {
            receiver.accept(manager);
        }
    }
}
