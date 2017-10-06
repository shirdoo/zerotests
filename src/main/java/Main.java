import refocus.RefocusClient;

/**
 * @author sbabu
 * @since 10/3/2017
 */
public class Main {
    public static void main(String[] args) throws Exception {
        RefocusClient client = new RefocusClient();
        System.out.println(client.getSubject("IdGenerator"));
        System.out.println(client.getAspect("IdGeneratorPing"));
    }

}
