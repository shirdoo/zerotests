package com.salesforce.refocus;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.util.List;

/**
 * Simple java pojo representing a Sample:
 *   https://salesforce.github.io/refocus/docs/01-quickstart.html#subject
 * @author sbabu
 * @since 10/5/17
 */
public class Subject {

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

    public String getDescription() {
        return description;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public List<Link> getRelatedLinks() {
        return relatedLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (isPublished() != subject.isPublished()) return false;
        if (getDescription() != null ? !getDescription().equals(subject.getDescription()) : subject.getDescription() != null)
            return false;
        if (getName() != null ? !getName().equals(subject.getName()) : subject.getName() != null) return false;
        return getRelatedLinks() != null ? getRelatedLinks().equals(subject.getRelatedLinks()) : subject.getRelatedLinks() == null;
    }

    @Override
    public int hashCode() {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + (isPublished() ? 1 : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getRelatedLinks() != null ? getRelatedLinks().hashCode() : 0);
        return result;
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
        private List<Link> relatedLinks = Lists.newArrayList();

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
