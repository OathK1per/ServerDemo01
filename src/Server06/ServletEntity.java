package Server06;

/**
 * xml中servlet tag部分下的信息和获取
 */
public class ServletEntity {
    private String servletName;
    private String servletClass;

    public ServletEntity() {
    }

    public ServletEntity(String servletName, String servletClass) {
        this.servletName = servletName;
        this.servletClass = servletClass;
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }
}
