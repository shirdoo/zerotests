package com.salesforce.refocus;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Currently supported config types of aspects as documented at:
 * https://salesforce.github.io/refocus/docs/01-quickstart.html#aspect
 */
abstract class ValueType<T> {
    public abstract String encode(T value);

    public abstract T decode(String encoding);

    static class NumericType extends ValueType<Long> {
        public static final NumericType NUMERIC = new NumericType();

        @Override
        public String encode(Long value) {
            return Long.toString(value);
        }

        @Override
        public Long decode(String encoding) {
            return Long.valueOf(encoding);
        }

        @Override
        public String toString() {
            return "NUMERIC";
        }
    }

    static class BooleanType extends ValueType<Boolean> {
        public static final BooleanType BOOLEAN = new BooleanType();

        @Override
        public String encode(Boolean value) {
            return value.toString();
        }

        @Override
        public Boolean decode(String encoding) {
            return Boolean.valueOf(encoding);
        }

        @Override
        public String toString() {
            return "BOOLEAN";
        }
    }

    static class PercentType extends ValueType<Integer> {
        public static final PercentType PERCENT = new PercentType();

        @Override
        public String encode(Integer value) {
            return value.toString();
        }

        @Override
        public Integer decode(String encoding) {
            return Integer.valueOf(encoding);
        }

        @Override
        public String toString() {
            return "PERCENT";
        }
    }

    static class ValueTypeSerializer implements JsonSerializer<ValueType> {
        public JsonElement serialize(ValueType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    static class ValueTypeDeserializer implements JsonDeserializer<ValueType> {
        public ValueType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            switch(json.getAsJsonPrimitive().getAsString()) {
                case "PERCENT" : return ValueType.PercentType.PERCENT;
                case "NUMERIC" : return ValueType.NumericType.NUMERIC;
                case "BOOLEAN" : return ValueType.BooleanType.BOOLEAN;
                default : throw new IllegalArgumentException("Unexpected value type passed: " + json.getAsJsonPrimitive().getAsString());
            }
        }
    }
}