package com.salesforce.refocus;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author sbabu
 * @since 10/5/17
 */
public class RefocusClientTest {

    @Test
    public void testSubjectCrud() throws Exception {
        RefocusClient client = new RefocusClient();
        String subjectName = "RefocusClientTest";
        Subject.SubjectBuilder builder = Subject.SubjectBuilder.aSubject("testSubjectCrud");
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
    public void testAspectCrud() throws Exception {
        RefocusClient client = new RefocusClient();
        Aspect.AspectBuilder aspectBuilder = Aspect.AspectBuilder.anAspect("testAspectCrud");
        Aspect a = aspectBuilder.build();
        try {
            //create
        client.createAspect(a);

        //read
            List<Aspect> refocusAspect = client.getAspect(a.getName());
        assertNotNull(refocusAspect);
        } finally {
            try {
                client.deleteAspect(a.getName());
            } catch (IllegalStateException e) {
            } // dont complain if delete fails, it should on the happy path
        }
        assertNotNull(client.getAspect("IdGeneratorPing"));
    }
}
