package com.salesforce.refocus;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author sbabu
 * @since 10/5/17
 */
public class Subject {

    public static Subject fromJson(String json) {
        return GSON.fromJson(json, Subject.class);
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    private Subject(String name, String description, boolean isPublished, List<Link> relatedLinks) {
        this.description = description;
        this.isPublished = isPublished;
        this.name = name;
        this.relatedLinks = relatedLinks;
    }

    private final String description;
    private final boolean isPublished;
    private final String name;
    private final List<Link> relatedLinks;

    private static final Gson GSON = new Gson();

    public String getName() {
        return name;
    }

    private static class Link {
        private final String name;
        private final String url;

        private Link(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public static final class SubjectBuilder {
        private String description;
        private boolean isPublished;
        private String name;
        private List<Link> relatedLinks;

        private SubjectBuilder() {
        }

        public static SubjectBuilder aSubject(String name) {
            return new SubjectBuilder().withName(name);
        }


        public SubjectBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SubjectBuilder withIsPublished(boolean isPublished) {
            this.isPublished = isPublished;
            return this;
        }

        public SubjectBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SubjectBuilder withRelatedLinks(List<Link> relatedLinks) {
            this.relatedLinks = relatedLinks;
            return this;
        }

        public Subject build() {
            return new Subject(name, description, isPublished, relatedLinks);
        }
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPublished=" + isPublished +
                ", relatedLinks=" + relatedLinks +
                '}';
    }
}
