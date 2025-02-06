package org.spigotmc.cogs.api.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleMeta {
    String id() default "";

    String version() default "0.0.0";
}
