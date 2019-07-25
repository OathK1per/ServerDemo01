package Server02;

import java.util.HashSet;
import java.util.Set;

public class ServletMapping {
    private Set<String> urlPattern;
    private String servletName;

    public ServletMapping() {
        urlPattern = new HashSet<>();
    }

    public ServletMapping(Set<String> urlPattern, String servletName) {
        this.urlPattern = urlPattern;
        this.servletName = servletName;
    }

    public Set<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(Set<String> urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public void addUrlPattern(String pattern) {
        this.urlPattern.add(pattern);
    }
}
