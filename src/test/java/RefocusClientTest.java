import org.junit.Test;
import refocus.RefocusClient;
import refocus.pojo.Subject;

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
        Subject s = Subject.SubjectBuilder.aSubject(this.getClass().getName()).build();

        client.postSubject(s);
        assertNotNull(client.getSubject(s.getName()));
        client.deleteSubject(s.getName());
        assertNull(client.getSubject(s.getName()));
    }


    @Test
    public void testGetAspect() throws Exception {
        RefocusClient client = new RefocusClient();
        System.out.println(client.getAspect("IdGeneratorPing"));
    }
}
