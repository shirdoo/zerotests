package com.salesforce.refocus;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;

import static com.salesforce.GsonUtils.GSON;

/**
 * Simple pojo describing an aspect:
 * https://salesforce.github.io/refocus/docs/01-quickstart.html#aspect
 *
 * @author sbabu
 * @since 10/6/17
 */
public class Aspect<T> {

    private final String name;
    private final boolean isPublished;
    private final String description;
    private final List<Integer> criticalRange;
    private final List<Integer> warningRange;
    private final List<Integer> infoRange;
    private final List<Integer> okRange;
    private final String timeout;
    private final ValueType valueType;
    private final String valueLabel;


    private Aspect(String name, String description, String timeout, boolean isPublished, List<Integer> criticalRange, List<Integer> warningRange, List<Integer> infoRange, List<Integer> okRange, ValueType<T> valueType, String valueLabel) {
        this.name = name;
        this.description = description;
        this.isPublished = isPublished;
        this.criticalRange = criticalRange;
        this.infoRange = infoRange;
        this.okRange = okRange;
        this.warningRange = warningRange;
        this.timeout = timeout;
        this.valueType = valueType;
        this.valueLabel = valueLabel;
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

    public List<Integer> getWarningRange() {
        return warningRange;
    }

    public List<Integer> getInfoRange() {
        return infoRange;
    }

    public List<Integer> getOkRange() {
        return okRange;
    }

    public String getValueLabel() {
        return valueLabel;
    }

    public String getTimeout() {
        return timeout;
    }

    public ValueType getValueType() {
        return valueType;
    }


    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    public static final class AspectBuilder {
        private String name;
        private String description;
        private boolean isPublished = true;
        private Optional<List<Integer>> criticalRange = Optional.empty();
        private Optional<List<Integer>> warningRange = Optional.empty();
        private Optional<List<Integer>> okRange = Optional.empty();
        private Optional<List<Integer>> infoRange = Optional.empty();
        private String timeout = "60m";
        private ValueType valueType = ValueType.NumericType.NUMERIC;
        private String valueLabel;

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
            assertValidRange(criticalRange);
            this.criticalRange = Optional.of(criticalRange);
            return this;
        }

        public AspectBuilder withWarningRange(List<Integer> warningRange) {
            assertValidRange(warningRange);
            this.warningRange = Optional.of(warningRange);
            return this;
        }

        public AspectBuilder withOkRange(List<Integer> okRange) {
            assertValidRange(okRange);
            this.okRange = Optional.of(okRange);
            return this;
        }

        public AspectBuilder withInfoRange(List<Integer> infoRange) {
            assertValidRange(infoRange);
            this.infoRange = Optional.of(infoRange);
            return this;
        }

        public AspectBuilder withTimeout(String timeout) {
            this.timeout = timeout;
            return this;
        }

        public AspectBuilder withValueLabel(String valueLabel) {
            this.valueLabel = valueLabel;
            return this;
        }

        public Aspect build() {
            return new Aspect(name, description, timeout, isPublished, criticalRange.orElse(ImmutableList.of(0, 0)), warningRange.orElse(ImmutableList.of(0, 0)), infoRange.orElse(ImmutableList.of(0, 0)), okRange.orElse(ImmutableList.of(1, 1)), valueType, valueLabel);
        }

        private void assertValidRange(List<Integer> range) {
            Preconditions.checkArgument(range != null, "Missing critical range arg");
            Preconditions.checkArgument(range.size() == 2, "Critical range list must contain 2 elements, a min and max");
            Preconditions.checkArgument(range.get(0) <= range.get(1), "Min should be less than max");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aspect aspect = (Aspect) o;

        if (isPublished() != aspect.isPublished()) return false;
        if (getName() != null ? !getName().equals(aspect.getName()) : aspect.getName() != null) return false;
        if (getValueType() != null ? !getValueType().equals(aspect.getValueType()) : aspect.getValueType() != null)
            return false;
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
