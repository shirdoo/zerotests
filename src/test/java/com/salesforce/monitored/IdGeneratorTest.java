package com.salesforce.monitored;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author sbabu
 * @since 10/5/17
 */
public class IdGeneratorTest {
    private static final String ORG_ID = "00D000000000062";
    private static final String KEY_PREFIX = "001";

    @Test
    public void testIdGenerator() throws Exception {
        IdGeneratorClient client = new IdGeneratorClient();
        String key = client.getKey(ORG_ID, KEY_PREFIX);
        assertNotNull(key);
        assertTrue(key.startsWith(KEY_PREFIX));
        assertEquals(15, key.length());
    }

}
