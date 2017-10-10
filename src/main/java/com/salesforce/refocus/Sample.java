package com.salesforce.refocus;

import com.google.gson.annotations.SerializedName;

import static com.salesforce.GsonUtils.GSON;

/**
 * Simple pojo representing a sample, which is essentially a value of an aspect for a subject posted in a time window:
 *   https://salesforce.github.io/refocus/docs/01-quickstart.html#sample
 *
 * @author sbabu
 * @since 10/9/17
 */
public class Sample {

    private final transient String subjectName;
    private final transient String aspectName;

    @SerializedName("name")
    private final String refocusPath;
    private final String value;
    private final String message;

    public Sample(String subjectName, String aspectName, String value, String message) {
        this.subjectName = subjectName;
        this.aspectName = aspectName;
        this.refocusPath = subjectName + "|" + aspectName;
        this.value = value;
        this.message = message;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getAspectName() {
        return aspectName;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
