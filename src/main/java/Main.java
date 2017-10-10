import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.salesforce.GsonUtils;
import com.salesforce.monitored.IdGeneratorPinger;
import com.salesforce.refocus.Aspect;
import com.salesforce.refocus.RefocusClient;
import com.salesforce.refocus.Subject;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author sbabu
 * @since 10/3/2017
 */
public class Main {

    private static Map<String, Aspect> loadedAspects = Maps.newHashMap();
    private static Map<String, Subject> loadedSubjects = Maps.newHashMap();

    public static void main(String[] args) throws Exception {
        final RefocusClient client = new RefocusClient();


        System.out.println("Checking aspects...");
        for (Aspect aspect : loadAspects()) {
            Aspect refocusAspect = client.getAspect(aspect.getName());
            if (aspect.equals(refocusAspect)) {
                System.out.println("Refocus instance of " + aspect.getName() + " matches checked in entity, enjoy.");
            } else if (refocusAspect == null) {
                System.out.println("Creating instance of " + aspect.getName());
                client.createAspect(aspect);
            } else {
                System.out.println("Updating instance of " + aspect.getName());
                client.updateAspect(aspect);
            }
            System.out.println("Aspect: " + aspect);
            loadedAspects.put(aspect.getName(), aspect);
        }


        System.out.println("Checking subjects...");
        for (Subject subject : loadSubjects()) {
            Subject refocusAspect = client.getSubject(subject.getName());
            if (subject.equals(refocusAspect)) {
                System.out.println("Refocus instance of " + subject.getName() + " matches checked in entity, enjoy.");
            } else if (refocusAspect == null) {
                System.out.println("Creating instance of " + subject.getName());
                client.createSubject(subject);
            } else {
                System.out.println("Updating instance of " + subject.getName());
                client.updateSubject(subject);
            }
            System.out.println("Aspect: " + subject);
            loadedSubjects.put(subject.getName(), subject);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    System.out.println("Running Id generator checks");
                    try {
                        new RefocusClient().postSamples(new IdGeneratorPinger().runChecks());
                    } catch (IOException e) {
                        System.out.println("Error posting to refocus");
                    }
            }
        }, 1000, 1000);

        while (true) {
            // hang out indefinitely
        }
    }

    private static List<Aspect> loadAspects() throws IOException {
        String json = new String(Files.readAllBytes(GsonUtils.getAspectsFiles()));
        return GsonUtils.GSON.fromJson(json, new TypeToken<List<Aspect>>(){}.getType());
    }

    private static List<Subject> loadSubjects() throws IOException {
        String json = new String(Files.readAllBytes(GsonUtils.getEnvironmentsFiles()));
        return GsonUtils.GSON.fromJson(json, new TypeToken<List<Subject>>(){}.getType());
    }
}
