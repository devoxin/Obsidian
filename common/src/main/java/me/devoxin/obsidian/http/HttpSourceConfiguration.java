package me.devoxin.obsidian.http;

import java.util.Collections;
import java.util.Map;

public class HttpSourceConfiguration {
    private final boolean followRedirects;
    private boolean blockAll = false;
    private final Map<String, String> hosts;

    public HttpSourceConfiguration(final boolean followRedirects,
                                   final boolean blockAll,
                                   final Map<String, String> hosts) {
        this.followRedirects = followRedirects;
        this.blockAll = blockAll;
        this.hosts = hosts;
    }

    public static HttpSourceConfiguration defaultConfiguration() {
        return new HttpSourceConfiguration(true, false, Collections.emptyMap());
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public boolean isBlockAll() {
        return blockAll;
    }

    public Map<String, String> getHosts() {
        return hosts;
    }

    private String getHostEntry(String key, String default_) {
        return hosts.getOrDefault(key, default_);
    }

    // Yep, this is a mess. It is 03:48 and logic has begun to fail me as I become tired.
    // Will I clean this up? Probably not. Don't fix what's not broken.
    // At least... I don't think it's broken. I wrote some sketchy tests which are all valid
    // so... I believe this is working as intended.
    public boolean isAllowed(String exact, String domain, String domainWithoutTld) {
        String entryWithoutTld = getHostEntry(domainWithoutTld, null);
        String entryDomain = getHostEntry(domain, null);
        String entryExact = getHostEntry(exact, null);

        if (blockAll) {
            if ("allow".equals(entryWithoutTld)) {
                return !"block".equals(entryDomain) && !"block".equals(entryExact);
            } else if ("allow".equals(entryDomain)) {
                return !"block".equals(entryExact);
            } else {
                return "allow".equals(entryExact);
            }
        }

        if ("block".equals(entryWithoutTld)) {
            return "allow".equals(entryDomain) || "allow".equals(entryExact);
        } else if ("block".equals(entryDomain)) {
            return "allow".equals(exact);
        } else {
            return !"block".equals(exact);
        }
    }
}
