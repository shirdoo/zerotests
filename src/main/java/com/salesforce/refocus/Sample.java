package com.salesforce.refocus;

import com.google.gson.annotations.SerializedName;

/**
 * Simple pojo representing a sample, which is essentially a value of an aspect for a subject posted in a time window:
 *   https://salesforce.github.io/refocus/docs/01-quickstart.html#sample
 *
 * @author sbabu
 * @since 10/9/17
 */
public class Sample<T> {

    private final transient String subjectName;
    private final transient String aspectName;

    @SerializedName("name")
    private final String refocusPath;
    private final T value;

    public Sample(String subjectName, String aspectName, T value) {
        this.subjectName = subjectName;
        this.aspectName = aspectName;
        this.refocusPath = subjectName + "|" + aspectName;
        this.value = value;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getAspectName() {
        return aspectName;
    }

    public T getValue() {
        return value;
    }

}
