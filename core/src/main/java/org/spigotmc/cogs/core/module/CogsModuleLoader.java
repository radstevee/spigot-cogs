package org.spigotmc.cogs.core.module;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spigotmc.cogs.api.module.CogModule;
import org.spigotmc.cogs.api.module.ModuleMeta;

public class CogsModuleLoader {
    private static final Path MODULES_DIRECTORY = Path.of("modules");
    private static final Logger LOGGER = LogManager.getLogger();

    private final Set<CogsModuleClassLoader> classLoaders = new HashSet<>();

    private void init() {
        try {
            Files.createDirectories(MODULES_DIRECTORY);
        } catch (IOException exception) {
            throw new RuntimeException("Failed creating modules directory: " + exception);
        }
    }

    @NonNull public Set<@NonNull Path> collectModuleJars() throws IOException {
        this.init();
        try (final Stream<Path> walk = Files.walk(CogsModuleLoader.MODULES_DIRECTORY)) {
            return walk.filter((path) -> {
                        if (Files.isDirectory(path)) {
                            return false;
                        }

                        final String fileName = path.getFileName().toString();

                        return Objects.equals(fileName.substring(fileName.lastIndexOf('.')), ".jar");
                    })
                    .collect(Collectors.toSet());
        }
    }

    public void loadModule(@NonNull Path jar) {
        try (final CogsModuleClassLoader classLoader = new CogsModuleClassLoader(jar)) {
            final ModuleMeta meta = classLoader.meta();
            LOGGER.info("Loading module {} v{}", meta.id(), meta.version());
            classLoader.module().enable();

            this.classLoaders.add(classLoader);
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Failed converting URI to URL for jar " + jar.toString() + ": " + exception);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Optional<CogModule> findById(@NonNull String id) {
        return this.classLoaders.stream()
                .filter(loader -> loader.meta().id().equals(id))
                .map(CogsModuleClassLoader::module)
                .findFirst();
    }

    public void unloadModule(@NonNull String id) {
        this.findById(id).ifPresent(CogModule::disable);
    }

    public void unloadAllModules() {
        this.classLoaders.stream().map(CogsModuleClassLoader::module).forEach(CogModule::disable);
    }
}
