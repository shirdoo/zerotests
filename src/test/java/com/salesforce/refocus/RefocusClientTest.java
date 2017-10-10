package com.salesforce.refocus;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This test is an integration test with a running refocus instance.
 *
 *
 * @author sbabu
 * @since 10/5/17
 */
public class RefocusClientTest {

    @Test
    public void testSubjectCrud() throws Exception {
        RefocusClient client = new RefocusClient();
        String subjectName = "RefocusClientTest";
        Subject.SubjectBuilder builder = Subject.SubjectBuilder.aSubject("testSubjectCrud");
        Subject expectedSubject = builder.build();
        try {
            //create
            client.createSubject(expectedSubject);

            //read
            Subject refocusSubject = client.getSubject(expectedSubject.getName());
            assertNotNull(refocusSubject);
            assertEquals(expectedSubject, refocusSubject);
            assertNull(refocusSubject.getDescription());

            //update
            String newDescription = "Updated description";
            client.updateSubject(builder.withDescription(newDescription).build());
            refocusSubject = client.getSubject(expectedSubject.getName());
            assertNotNull(refocusSubject);
            assertNotEquals(expectedSubject, refocusSubject);
            assertEquals(expectedSubject.getName(), refocusSubject.getName());
            assertEquals(newDescription, refocusSubject.getDescription());

            //delete
            client.deleteSubject(expectedSubject.getName());
            assertNull(client.getSubject(expectedSubject.getName()));
        } finally {
            try { client.deleteSubject(expectedSubject.getName()); } catch (IllegalStateException e) {} // dont complain if delete fails, it should on the happy path
        }
    }

    @Test
    public void testAspectCrud() throws Exception {
        RefocusClient client = new RefocusClient();
        Aspect.AspectBuilder aspectBuilder = Aspect.AspectBuilder.anAspect("testAspectCrud");
        Aspect originalAspect = aspectBuilder.build();
        try {
            //create
            client.createAspect(originalAspect);

            //read
            Aspect refocusAspect = client.getAspect(originalAspect.getName());
            assertEquals(originalAspect, refocusAspect);

            aspectBuilder.withCriticalRange(ImmutableList.of(1,2));
            client.updateAspect(aspectBuilder.build());
            Aspect updatedAspect = client.getAspect(originalAspect.getName());
            assertNotEquals(originalAspect, updatedAspect);
            assertEquals(2, updatedAspect.getCriticalRange().size());

            client.deleteAspect(originalAspect.getName());
            assertNull(client.getAspect(originalAspect.getName()));
        } finally {
            try {
                client.deleteAspect(originalAspect.getName());
            } catch (IllegalStateException e) {
            } // dont complain if delete fails, it should on the happy path
        }
    }

    @Test
    public void testSamplePost() throws Exception {
        RefocusClient client = new RefocusClient();
        System.out.println(client.getAspects().get(0));
        System.out.println(client.getSubjects().get(0));
        Sample sample = new Sample("IdGenerator", "IdGeneratorPing", "true");
        client.postSamples(ImmutableSet.of(sample));
    }
}
