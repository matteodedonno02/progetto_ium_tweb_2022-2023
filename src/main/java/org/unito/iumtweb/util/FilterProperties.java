package org.unito.iumtweb.util;

import java.util.*;

public class FilterProperties {
    private Map<String, List<String>> properties;

    public FilterProperties() {
        properties = new HashMap<String, List<String >>();
    }

    public void add(String servletName, String[] operations) {
        properties.put(servletName, Arrays.asList(operations));
    }

    public boolean propertyExists(String servletName, String operation) {
        boolean found = false;
        if(properties.get(servletName) != null) {
            List<String> operations = properties.get(servletName);
            for(int i = 0; i < operations.size() && !found; i ++) {
                found = operations.get(i).equals(operation);
            }
        }
        return found;
    }
}
