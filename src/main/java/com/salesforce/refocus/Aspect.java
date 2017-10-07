package com.salesforce.refocus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author sbabu
 * @since 10/6/17
 */
public class Aspect {
    private static final Gson GSON = new Gson();


    public static List<Aspect> fromJson(String json) {
        return GSON.fromJson(json, new TypeToken<List<Aspect>>(){}.getType());
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    private final String name;
    private final String description;
    private final boolean isPublished;
    private final List<Integer> criticalRange;
    private final String timeout;

    private Aspect(String name, String description, String timeout, boolean isPublished, List<Integer> criticalRange) {
        this.name = name;
        this.description = description;
        this.isPublished = isPublished;
        this.criticalRange = criticalRange;
        this.timeout = timeout;
    }

    public String getName() {
        return name;
    }

    public static final class AspectBuilder {
        private String name;
        private String description;
        private boolean isPublished = true;
        private List<Integer> criticalRange;
        private String timeout = "20s";

        private AspectBuilder(String name) {
            this.name = name;
        }

        public static AspectBuilder anAspect(String name) {
            return new AspectBuilder(name);
        }

        public AspectBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AspectBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public AspectBuilder withIsPublished(boolean isPublished) {
            this.isPublished = isPublished;
            return this;
        }

        public AspectBuilder withCriticalRange(List<Integer> criticalRange) {
            this.criticalRange = criticalRange;
            return this;
        }

        public AspectBuilder withTimeout(String timeout) {
            this.timeout = timeout;
            return this;
        }

        public Aspect build() {
            return new Aspect(name, description, timeout, isPublished, criticalRange);
        }
    }
}
