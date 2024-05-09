package me.devoxin.obsidian.sources.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class HttpSourceConfiguration {
    private static final Logger log = LoggerFactory.getLogger(HttpSourceConfiguration.class);

    private final boolean followRedirects;
    private final boolean blockAll;
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
                log.trace("[blockAll] TLD-less domain is permitted, checking whether domain with TLD or exact entry blocks.");
                return !"block".equals(entryDomain) && !"block".equals(entryExact);
            } else if ("allow".equals(entryDomain)) {
                log.trace("[blockAll] Domain with TLD is permitted, checking whether exact entry blocks.");
                return !"block".equals(entryExact);
            } else {
                log.trace("[blockAll] Checking whether exact entry is allowed.");
                return "allow".equals(entryExact);
            }
        }

        if ("block".equals(entryWithoutTld)) {
            log.trace("TLD-less domain is blocked, checking whether domain with TLD or exact entry allows.");
            return "allow".equals(entryDomain) || "allow".equals(entryExact);
        } else if ("block".equals(entryDomain)) {
            log.trace("Domain with TLD is blocked, checking whether exact entry allows.");
            return "allow".equals(entryExact);
        } else {
            log.trace("Checking whether exact entry is blocked.");
            return !"block".equals(entryExact);
        }
    }
}
