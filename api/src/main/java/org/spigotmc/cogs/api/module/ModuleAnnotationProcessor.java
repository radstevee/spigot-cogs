package org.spigotmc.cogs.api.module;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes({"org.spigotmc.cogs.api.module.ModuleMeta"})
public class ModuleAnnotationProcessor extends AbstractProcessor {
    private static final Gson GSON = new Gson();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, @NonNull RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }

        for (final Element element : roundEnv.getElementsAnnotatedWith(ModuleMeta.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Only classes can be annotated with @ModuleMeta!");
                return false;
            }

            final Name fqn = ((TypeElement) element).getQualifiedName();
            final JsonObject json = new JsonObject();
            json.addProperty("entrypointClass", fqn.toString());

            try {
                final FileObject file = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "config.json");

                try (final Writer writer = new BufferedWriter(file.openWriter())) {
                    GSON.toJson(json, writer);
                }
            } catch (IOException exception) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to generate module config: " + exception);
            }
        }

        return false;
    }
}
