package com.salesforce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.salesforce.refocus.ValueType;

import java.io.File;
import java.nio.file.Path;

/**
 * Just a wrapper around google's Gson customized for our objects.
 *
 * @author sbabu
 * @since 10/10/17
 */
public class GsonUtils {
    public static final Gson GSON;
    static {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(ValueType.class, new ValueType.ValueTypeSerializer());
        gson.registerTypeAdapter(ValueType.class, new ValueType.ValueTypeDeserializer());
        GSON = gson.setPrettyPrinting().create();
    }

    private static final String ENVIRONMENT_LISTINGS_DIR = "environments";

    /*
     * Loads files from src/main/resources.
     */
    public static Path getEnvironmentsFiles() {
        return new File(Thread.currentThread().getContextClassLoader().getResource(ENVIRONMENT_LISTINGS_DIR + "/environments.json").getPath()).toPath();
    }

    public static Path getAspectsFiles() {
        return new File(Thread.currentThread().getContextClassLoader().getResource(ENVIRONMENT_LISTINGS_DIR + "/aspects.json").getPath()).toPath();
    }
}
