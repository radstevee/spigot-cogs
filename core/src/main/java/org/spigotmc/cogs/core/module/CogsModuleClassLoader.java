package org.spigotmc.cogs.core.module;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spigotmc.cogs.api.module.CogModule;
import org.spigotmc.cogs.api.module.ModuleMeta;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class CogsModuleClassLoader extends URLClassLoader {
    private static final Gson GSON = new Gson();

    private final CogModule module;
    private final ModuleMeta meta;
    private final CogsModuleConfiguration config;

    @SuppressWarnings("unchecked")
    public CogsModuleClassLoader(@NonNull Path jar) throws MalformedURLException {
        super(new URL[]{jar.toUri().toURL()}, CogsModuleLoader.class.getClassLoader());

        final InputStream configStream = this.getResourceAsStream("config.json");
        if (configStream == null) {
            throw new IllegalStateException("Module does not have a config.json!");
        }
        final JsonReader jsonReader = GSON.newJsonReader(new InputStreamReader(configStream));
        this.config = GSON.fromJson(jsonReader, CogsModuleConfiguration.class);

        final Class<? extends CogModule> moduleClass;
        try {
            moduleClass = (Class<? extends CogModule>) this.loadClass(this.config.entrypointClass());
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Entrypoint for module " + jar.getFileName().toString() + " does not exist");
        }
        this.meta = moduleClass.getAnnotation(ModuleMeta.class);

        Constructor<? extends CogModule> constructor;
        try {
            constructor = moduleClass.getDeclaredConstructor();
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException("Module " + moduleClass.getSimpleName() + " does not have a primary constructor: " + exception);
        }

        constructor.setAccessible(true);
        try {
            this.module = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException("Failed invoking constructor of module " + moduleClass.getSimpleName() + ": " + exception);
        }
    }

    @NonNull
    public ModuleMeta meta() {
        return this.meta;
    }

    @NonNull
    public CogModule module() {
        return this.module;
    }

    @NonNull
    public CogsModuleConfiguration config() {
        return this.config;
    }
}
