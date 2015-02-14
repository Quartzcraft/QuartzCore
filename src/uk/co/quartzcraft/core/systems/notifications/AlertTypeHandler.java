package uk.co.quartzcraft.core.systems.notifications;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AlertTypeHandler {

    private static Map<String, Entry<Method, Object>> methodMap = new HashMap<String, Entry<Method, Object>>();
    private static Map<String, AlertType> typeMap = new HashMap<String, AlertType>();

    /**
     * Registers all AlertTypes inside an object
     *
     * @param obj
     */
    public static void registerAlertTypes(Object obj) {
        for (Method m : obj.getClass().getMethods()) {
            if (m.getAnnotation(AlertType.class) != null) {
                AlertType alertType = m.getAnnotation(AlertType.class);
                if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != String[].class) {
                    System.out.println("Unable to register AlertType " + m.getName() + ". Unexpected method arguments");
                    continue;
                }
                registerAlertType(alertType, alertType.name(), m, obj);
            }
        }
    }

    private static void registerAlertType(AlertType alertType, String label, Method m, Object obj) {
        Entry<Method, Object> entry = new AbstractMap.SimpleEntry<Method, Object>(m, obj);
        methodMap.put(label.toLowerCase(), entry);
        typeMap.put(label.toLowerCase(), alertType);
    }

    public static AlertType getAlertType(String label) {
        if(typeMap.get(label) != null && methodMap.get(label) != null) {
            return typeMap.get(label);
        }
        return null;
    }

    public static Entry getAlertTypeMethod(String label) {
        if(typeMap.get(label) != null && methodMap.get(label) != null) {
            return methodMap.get(label);
        }
        return null;
    }
}
