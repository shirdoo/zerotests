package com.salesforce.refocus;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author sbabu
 * @since 10/5/17
 */
public class RefocusClientTest {

    @Test
    public void testSubjectCrud() throws Exception {
        RefocusClient client = new RefocusClient();
        String subjectName = "RefocusClientTest";
        Subject s = Subject.SubjectBuilder.aSubject("RefocusClientTest").build();
        try {
            client.postSubject(s);
            assertNotNull(client.getSubject(s.getName()));
            client.deleteSubject(s.getName());
            assertNull(client.getSubject(s.getName()));
        } finally {
            try { client.deleteSubject(s.getName()); } catch (IllegalStateException e) {} // dont complain if delete fails, it should on the happy path
        }
    }

    @Test
    public void testGetAspect() throws Exception {
        RefocusClient client = new RefocusClient();
        assertNotNull(client.getAspect("IdGeneratorPing"));
    }
}
