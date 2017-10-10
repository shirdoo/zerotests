package com.salesforce.refocus;

import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Simple pojo describing an aspect:
 *   https://salesforce.github.io/refocus/docs/01-quickstart.html#aspect
 *
 * @author sbabu
 * @since 10/6/17
 */
public class Aspect<T> {

    private final String name;
    private final String description;
    private final boolean isPublished;
    private final List<Integer> criticalRange;
    private final String timeout;
    private final ValueType valueType;

    private Aspect(String name, String description, String timeout, boolean isPublished, List<Integer> criticalRange, ValueType<T> valueType) {
        this.name = name;
        this.description = description;
        this.isPublished = isPublished;
        this.criticalRange = criticalRange;
        this.timeout = timeout;
        this.valueType = valueType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public List<Integer> getCriticalRange() {
        return criticalRange;
    }

    public String getTimeout() {
        return timeout;
    }

    public ValueType getValueType() {
        return valueType;
    }

    @Override
    public String toString() {
        return "Aspect{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPublished=" + isPublished +
                ", criticalRange=" + criticalRange +
                ", timeout='" + timeout + '\'' +
                ", valueType='" + valueType + '\'' +
                '}';
    }

    public static final class AspectBuilder {
        private String name;
        private String description;
        private boolean isPublished = true;
        private List<Integer> criticalRange;
        private String timeout = "60m";
        private ValueType valueType = ValueType.NumericType.NUMERIC;

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

        public AspectBuilder withValueType(ValueType valueType) {
            this.valueType = valueType;
            return this;
        }

        public AspectBuilder withCriticalRange(List<Integer> criticalRange) {
            Preconditions.checkArgument(criticalRange != null, "Missing critical range arg");
            Preconditions.checkArgument(criticalRange.size() == 2, "Critical range list must contain 2 elements, a min and max");
            Preconditions.checkArgument(criticalRange.get(0) < criticalRange.get(1), "Min should be less than max");
            this.criticalRange = criticalRange;
            return this;
        }

        public AspectBuilder withTimeout(String timeout) {
            this.timeout = timeout;
            return this;
        }

        public Aspect build() {
            return new Aspect(name, description, timeout, isPublished, criticalRange, valueType);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aspect aspect = (Aspect) o;

        if (isPublished() != aspect.isPublished()) return false;
        if (getName() != null ? !getName().equals(aspect.getName()) : aspect.getName() != null) return false;
        if (getValueType() != null ? !getValueType().equals(aspect.getValueType()) : aspect.getValueType() != null) return false;
        if (getDescription() != null ? !getDescription().equals(aspect.getDescription()) : aspect.getDescription() != null)
            return false;
        if (getCriticalRange() != null ? !getCriticalRange().equals(aspect.getCriticalRange()) : aspect.getCriticalRange() != null)
            return false;
        return getTimeout() != null ? getTimeout().equals(aspect.getTimeout()) : aspect.getTimeout() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (isPublished() ? 1 : 0);
        result = 31 * result + (getCriticalRange() != null ? getCriticalRange().hashCode() : 0);
        result = 31 * result + (getTimeout() != null ? getTimeout().hashCode() : 0);
        return result;
    }
}
