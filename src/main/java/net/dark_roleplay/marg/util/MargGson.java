package net.dark_roleplay.marg.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MargGson {
    public static final Gson NEW_GSON =
            new GsonBuilder()
                    .serializeNulls()
                    .setVersion(1.0)
                    .setPrettyPrinting()
                    .setExclusionStrategies(new HiddenExcluder())
                    .create();

    private static class HiddenExcluder implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(Exclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz.getAnnotation(Exclude.class) != null;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Exclude {}
}
