package uk.co.quartzcraft.core.systems.notifications;

import java.util.HashMap;

public class AlertArgs {

    private HashMap<String, Object> args = new HashMap<String, Object>();

    public AlertArgs() {

    }

    public AlertArgs setString(String k, String value) {
        args.put(k, value);
        return this;
    }

    public AlertArgs setInt(String k, int value) {
        args.put(k, value);
        return this;
    }

    public AlertArgs setBoolean(String k, boolean value) {
        args.put(k, value);
        return this;
    }

    public HashMap getArgs() {
        return this.args;
    }

    public Object getArg(String key) {
        return this.args.get(key);
    }
}
