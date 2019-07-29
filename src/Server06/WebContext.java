package Server06;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理数据使用，实现一个方法getServletClass，持有一个url，可以得到唯一的一个servlet class
 */
public class WebContext {
    private List<ServletMapping> servletMappings;
    private List<ServletEntity> servletEntities;

    // 使用map处理两个string间的映射关系 servletName -> servletClass
    private Map<String, String> EntitiesMap;
    // 使用map处理两个string间的映射关系 urlPattern -> servletName
    private Map<String, String> MappingsMap;

    public WebContext(List<ServletMapping> servletMappings, List<ServletEntity> servletEntities) {
        this.servletMappings = servletMappings;
        this.servletEntities = servletEntities;

        EntitiesMap = new HashMap<>();
        MappingsMap = new HashMap<>();

        for (ServletEntity entity : servletEntities) {
            EntitiesMap.put(entity.getServletName(), entity.getServletClass());
        }
        for (ServletMapping mapping : servletMappings) {
            for (String urlPattern : mapping.getUrlPattern()) {
                MappingsMap.put(urlPattern, mapping.getServletName());
            }
        }
    }

    public String getServletClass(String urlPattern) {
        String servletName = MappingsMap.get(urlPattern);

        return EntitiesMap.get(servletName);
    }

}
