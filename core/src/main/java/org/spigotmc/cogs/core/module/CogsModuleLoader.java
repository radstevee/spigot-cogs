package org.spigotmc.cogs.core.module;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spigotmc.cogs.api.module.CogModule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @NonNull
    public Set<@NonNull Path> collectModuleJars() throws IOException {
        this.init();
        try (final Stream<Path> walk = Files.walk(CogsModuleLoader.MODULES_DIRECTORY)) {
            return walk.filter((path) -> {
                        if (Files.isDirectory(path)) {
                            return false;
                        }

                        final String fileName = path.getFileName().toString();

                        return Objects.equals(
                            fileName.substring(fileName.lastIndexOf('.')),
                            ".jar"
                        );
                    }
                )
                .collect(Collectors.toSet());
        }
    }

    public void loadModule(@NonNull Path jar) {
        LOGGER.info("Loading module {}", jar.getFileName().toString());

        try (final CogsModuleClassLoader classLoader = new CogsModuleClassLoader(jar)) {
            classLoader.module().enable();
            this.classLoaders.add(classLoader);
            LOGGER.info("Module {} v{} loaded!", classLoader.meta().id(), classLoader.meta().version());
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Failed converting URI to URL for jar " + jar.toString() + ": " + exception);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Nullable
    public CogModule findById(@NonNull String id) {
        return this.classLoaders
            .stream()
            .filter(loader -> loader.meta().id().equals(id))
            .map(CogsModuleClassLoader::module)
            .findFirst()
            .orElse(null);
    }

    public void unloadModule(@NonNull String id) {
        final CogModule module = this.findById(id);
        if (module == null) {
            return;
        }

        module.disable();
    }

    public void unloadAllModules() {
        this.classLoaders
            .stream()
            .map(CogsModuleClassLoader::module)
            .forEach(CogModule::disable);
    }
}
