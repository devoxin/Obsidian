package me.devoxin.obsidian.sources.http;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRoutePlanner extends DefaultRoutePlanner {
    private static final Logger log = LoggerFactory.getLogger(HttpRoutePlanner.class);

    protected final HttpAudioSourceManager sourceManager;
    protected final HttpHost proxy;

    public HttpRoutePlanner(final HttpAudioSourceManager sourceManager,
                            final HttpHost proxy) {
        super(null);
        this.sourceManager = sourceManager;
        this.proxy = proxy;
    }

    @Override
    protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) {
        String requestUri = request.getRequestLine().getUri();

        if (!sourceManager.isProxyBypassed(requestUri)) {
            log.trace("Using configured proxy for {}.", requestUri);
            return proxy;
        }

        log.trace("Not using a proxy for {} as it is bypassed.", requestUri);
        return null;
    }
}
