package uk.co.quartzcraft.core.systems.websync;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.exception.WebsyncException;
import uk.co.quartzcraft.core.systems.config.QCConfig;
import uk.co.quartzcraft.core.util.Util;

import java.io.IOException;

public class Websync {
    public static String url;
    public static String key;

    public static void init() {
        QCConfig config = new QCConfig(QuartzCore.plugin);

        Websync.url = config.getString("settings.websync.url");
        Websync.key = config.getString("settings.websync.key");
    }

    public static void createAlert(String alertUserId, String causeUserId, String contentType, String contentId, String action, String[] extraData) throws WebsyncException {
        String url = "action=createAlert&user=" + alertUserId + "&cause_user=" + causeUserId + "&content_type=" + contentType + "&content_id=" + contentId + "&alert_action=" + action + "&hash=" + Websync.key;
        try {
            HttpMethods.httpGet(url);
        } catch(IOException e) {
            Util.printException("Failed to create alert", e);
        }
    }

    public static String getUser(String userName) {
        String url = "action=getUser&value=" + userName + "&hash=" + Websync.key;
        String result = null;
        try {
            result = HttpMethods.httpGet(url);
        } catch(IOException e) {
            Util.printException("Failed to get user", e);
        }

        return result;
    }

}
