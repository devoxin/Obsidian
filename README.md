# Obsidian
Obsidian extends Lavaplayer's functionality.

### Using in Lavalink:
```yaml
lavalink:
  plugins:
    - dependency: "com.github.devoxin:obsidian:<COMMIT HASH>"
      repository: "https://jitpack.io"
```

### Basic configuration:
Disable the built-in HTTP source manager within Lavalink first.
```yaml
lavalink:
  server:
    sources:
      http: false
```

```yaml
plugins:
  obsidian:
    sources:
      http:
        enabled: true
        followRedirects: true # set to false if redirects should not be permitted
        blockAll: false # Setting to true will put the hosts list into 'allowlist' mode, where only domains
                        # permitted with 'allow' will work.
        proxyAll: true # Proxies all domains by default (if a proxy is configured).
                       # You may configure a proxy using Lavalink's own `httpConfig` block.
        hosts:
          google: 'allow' # allow all requests to google, regardless of subdomain and TLD
          google.com: 'block' # block google.com specifically. All other Google combinations are permitted.
        proxy:
          example.com: 'bypass' # Requests to this site will not be proxied, regardless of the "proxyAll" setting.
          some.example.com: 'block'
          sketchy.site: 'proxy' # Requests to this site WILL be proxied, regardless of the "proxyAll" setting.
          google: 'bypass'
```
