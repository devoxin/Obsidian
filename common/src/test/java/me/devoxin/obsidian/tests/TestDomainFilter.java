package me.devoxin.obsidian.tests;

import me.devoxin.obsidian.http.HttpSourceConfiguration;

import java.util.HashMap;
import java.util.Map;

public class TestDomainFilter {
    private static final Map<String, String> hostConfig = new HashMap<String, String>() {{
        put("youtube", "block");
        put("youtube.com", "block");
        put("music.youtube.com", "allow");
    }};

    private static final Map<String, String> hostConfig2 = new HashMap<String, String>() {{
        put("youtube", "allow");
        put("youtube.com", "block");
        put("music.youtube.com", "block");
    }};

    private static final Map<String, String> hostConfig3 = new HashMap<String, String>() {{
        put("youtube", "block");
        put("youtube.com", "allow");
        put("music.youtube.com", "allow");
    }};

    public static void main(String[] args) {
        // probably better you don't ask questions here...

        HttpSourceConfiguration config = new HttpSourceConfiguration(true, false, hostConfig);
        boolean notAllowed1 = !config.isAllowed("youtube.com", "youtube.com", "youtube");
        boolean notAllowed2 = !config.isAllowed("youtube.fr", "youtube.fr", "youtube");
        boolean notAllowed3 = !config.isAllowed("m.youtube.com", "youtube.com", "youtube");
        boolean allowed1    = config.isAllowed("music.youtube.com", "youtube.com", "youtube");

        System.out.println("notAllowed1 = " + notAllowed1);
        System.out.println("notAllowed2 = " + notAllowed2);
        System.out.println("notAllowed3 = " + notAllowed3);
        System.out.println("allowed1    = " + allowed1);

        HttpSourceConfiguration config2 = new HttpSourceConfiguration(true, false, hostConfig2);
        boolean notAllowed4 = !config2.isAllowed("youtube.com", "youtube.com", "youtube");
        boolean notAllowed5 = !config2.isAllowed("m.youtube.com", "youtube.com", "youtube");
        boolean notAllowed6 = !config2.isAllowed("music.youtube.com", "youtube.com", "youtube");
        boolean allowed2    = config2.isAllowed("youtube.fr", "youtube.fr", "youtube");

        System.out.println();
        System.out.println("notAllowed4 = " + notAllowed4);
        System.out.println("notAllowed5 = " + notAllowed5);
        System.out.println("notAllowed6 = " + notAllowed6);
        System.out.println("allowed2    = " + allowed2);

        HttpSourceConfiguration config3 = new HttpSourceConfiguration(true, false, hostConfig3);
        boolean notAllowed7 = !config3.isAllowed("youtube.fr", "youtube.fr", "youtube");
        boolean allowed3    = config3.isAllowed("youtube.com", "youtube.com", "youtube");
        boolean allowed4    = config3.isAllowed("music.youtube.com", "youtube.com", "youtube");
        boolean allowed5    = config3.isAllowed("m.youtube.com", "youtube.com", "youtube");

        System.out.println();
        System.out.println("notAllowed7 = " + notAllowed7);
        System.out.println("allowed3    = " + allowed3);
        System.out.println("allowed4    = " + allowed4);
        System.out.println("allowed5    = " + allowed5);
    }

}
