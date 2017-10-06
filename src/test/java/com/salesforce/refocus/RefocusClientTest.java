package com.salesforce.refocus;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
        Subject.SubjectBuilder builder = Subject.SubjectBuilder.aSubject("RefocusClientTest");
        Subject s = builder.build();
        try {

            //create
            client.createSubject(s);

            //read
            Subject refocusSubject = client.getSubject(s.getName());
            assertNotNull(refocusSubject);
            assertEquals(s.getName(), refocusSubject.getName());
            assertNull(refocusSubject.getDescription());

            //update
            String newDescription = "Updated description";
            client.updateSubject(builder.withDescription(newDescription).build());
            refocusSubject = client.getSubject(s.getName());
            assertNotNull(refocusSubject);
            assertEquals(s.getName(), refocusSubject.getName());
            assertEquals(newDescription, refocusSubject.getDescription());

            //delete
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
