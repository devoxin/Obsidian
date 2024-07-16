package me.devoxin.obsidian.sources.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class HttpSourceConfiguration {
    private static final Logger log = LoggerFactory.getLogger(HttpSourceConfiguration.class);

    private final boolean followRedirects;
    private final boolean blockAll;
    private final boolean proxyAll;
    private final Map<String, String> hosts;
    private final Map<String, String> proxy;

    public HttpSourceConfiguration(final boolean followRedirects,
                                   final boolean blockAll,
                                   final boolean proxyAll,
                                   final Map<String, String> hosts,
                                   final Map<String, String> proxy) {
        this.followRedirects = followRedirects;
        this.blockAll = blockAll;
        this.proxyAll = proxyAll;
        this.hosts = hosts;
        this.proxy = proxy;
    }

    public static HttpSourceConfiguration defaultConfiguration() {
        return new HttpSourceConfiguration(true, false, true, Collections.emptyMap(), Collections.emptyMap());
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public boolean isBlockAll() {
        return blockAll;
    }

    public boolean isProxyAll() {
        return proxyAll;
    }

    public Map<String, String> getHosts() {
        return hosts;
    }

    public Map<String, String> getProxy() {
        return proxy;
    }

    private String getEntry(Map<String, String> collection, String key, String default_) {
        return collection.getOrDefault(key, default_);
    }

    public boolean isAllowed(String exact, String domain, String domainWithoutTld) {
        String entryWithoutTld = getEntry(hosts, domainWithoutTld, null);
        String entryDomain = getEntry(hosts, domain, null);
        String entryExact = getEntry(hosts, exact, null);

        if (blockAll) {
            if ("allow".equals(entryWithoutTld)) {
                log.trace("Hosts(blockAll): TLD-less domain is permitted, checking whether domain with TLD or exact entry blocks.");
                return !"block".equals(entryDomain) && !"block".equals(entryExact);
            } else if ("allow".equals(entryDomain)) {
                log.trace("Hosts(blockAll): Domain with TLD is permitted, checking whether exact entry blocks.");
                return !"block".equals(entryExact);
            } else {
                log.trace("Hosts(blockAll): Checking whether exact entry is allowed.");
                return "allow".equals(entryExact);
            }
        }

        if ("block".equals(entryWithoutTld)) {
            log.trace("Hosts: TLD-less domain is blocked, checking whether domain with TLD or exact entry allows.");
            return "allow".equals(entryDomain) || "allow".equals(entryExact);
        } else if ("block".equals(entryDomain)) {
            log.trace("Hosts: Domain with TLD is blocked, checking whether exact entry allows.");
            return "allow".equals(entryExact);
        } else {
            log.trace("Hosts: Checking whether exact entry is blocked.");
            return !"block".equals(entryExact);
        }
    }

    public boolean isProxyBypassed(String exact, String domain, String domainWithoutTld) {
        String entryWithoutTld = getEntry(proxy, domainWithoutTld, null);
        String entryDomain = getEntry(proxy, domain, null);
        String entryExact = getEntry(proxy, exact, null);

        if (proxyAll) {
            if ("bypass".equals(entryWithoutTld)) {
                log.trace("Proxy(blockAll): TLD-less domain is permitted, checking whether domain with TLD or exact entry proxies.");
                return !"proxy".equals(entryDomain) && !"proxy".equals(entryExact);
            } else if ("bypass".equals(entryDomain)) {
                log.trace("Proxy(blockAll): Domain with TLD is permitted, checking whether exact entry proxies.");
                return !"proxy".equals(entryExact);
            } else {
                log.trace("Proxy(blockAll): Checking whether exact entry is bypassed.");
                return "bypass".equals(entryExact);
            }
        }

        if ("proxy".equals(entryWithoutTld)) {
            log.trace("Proxy: TLD-less domain is proxied, checking whether domain with TLD or exact entry bypasses.");
            return "bypass".equals(entryDomain) || "bypass".equals(entryExact);
        } else if ("proxy".equals(entryDomain)) {
            log.trace("Proxy: Domain with TLD is proxied, checking whether exact entry bypasses.");
            return "bypass".equals(entryExact);
        } else {
            log.trace("Proxy: Checking whether exact entry is proxied.");
            return !"proxy".equals(entryExact);
        }
    }
}
