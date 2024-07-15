package me.devoxin.obsidian.plugin;

import me.devoxin.obsidian.sources.http.HttpSourceConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@ConfigurationProperties(prefix = "plugins.obsidian.sources.http")
@Component
public class HttpSourceConfig implements Toggleable {
    private boolean enabled = true;
    private boolean followRedirects = true;
    private boolean blockAll = false;
    private boolean proxyAll = true;
    private Map<String, String> hosts = Collections.emptyMap();
    private Map<String, String> proxy = Collections.emptyMap();

    @Override
    public boolean isEnabled() {
        return enabled;
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public void setBlockAll(boolean blockAll) {
        this.blockAll = blockAll;
    }

    public void setProxyAll(boolean proxyAll) {
        this.proxyAll = proxyAll;
    }

    public void setHosts(Map<String, String> hosts) {
        this.hosts = hosts;
    }

    public void setProxy(Map<String, String> proxy) {
        this.proxy = proxy;
    }

    public HttpSourceConfiguration getAsSourceConfig() {
        return new HttpSourceConfiguration(followRedirects, blockAll, proxyAll, hosts, proxy);
    }
}
