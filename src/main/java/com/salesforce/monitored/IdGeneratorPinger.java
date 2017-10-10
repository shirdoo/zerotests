package com.salesforce.monitored;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import com.salesforce.refocus.Sample;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author sbabu
 * @since 10/10/17
 */
public class IdGeneratorPinger {
    private static final String ORG_ID = "00D000000000062";
    private static final String KEY_PREFIX = "001";
    private final IdGeneratorClient client = new IdGeneratorClient();
    public Set<Sample> runChecks() {
        Stopwatch timer = Stopwatch.createStarted();
        long pingTime;
        boolean success = false;
        try {
            String key = client.getKey(ORG_ID, KEY_PREFIX);
            success = true;
        } catch (Exception e) {
            System.err.println("Caught exception talking to id generator" + e);
        } finally {
            pingTime = timer.elapsed(TimeUnit.MILLISECONDS);
        }

        if (Math.random() < .25) { success = false; System.out.println("Injecting failure "); }
        Sample pingSample = new Sample("IdGenerator", "IdGeneratorPing", Boolean.toString(success), success ? "Successfully pinged " + client.getUrl() : "Failed pinging " + client.getUrl());
        Sample pingTimeSample = new Sample("IdGenerator", "IdGeneratorPingTime", pingTime + "", "Ping check took " + pingTime + " ms to execute.");
        return ImmutableSet.of(pingSample, pingTimeSample);
    }
}
