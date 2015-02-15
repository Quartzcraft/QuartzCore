package uk.co.quartzcraft.core.systems.notifications;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.data.Server;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.Map;
import java.util.logging.Level;

public class Alert {

    private String prefix = null;
    private String message = null;
    private String[] args = null;
    private boolean displayPrefix = true;
    private AlertType alertType = null;
    private Server server = null;
    private QPlayer player = null;
    private boolean read = false;

    public Alert(String pre, String msg, String[] argss, boolean dP, String type, Server s) {
        if(msg.equals("") && type.equals("") && args == null) {
            Util.log(Level.WARNING, "Invalid alert arguments!");
        }
        this.prefix = pre;
        this.message = msg;
        this.args = argss;
        this.displayPrefix = dP;
        this.alertType = AlertTypeHandler.getAlertType(type);
        this.server = s;
    }

    public Alert(String pre, String msg, String[] argss, boolean dP, String type, Server s, QPlayer receiver) {
        if(msg.equals("") && type.equals("") && args == null) {
            Util.log(Level.WARNING, "Invalid alert arguments!");
        }
        this.prefix = pre;
        this.message = msg;
        this.args = argss;
        this.displayPrefix = dP;
        this.alertType = AlertTypeHandler.getAlertType(type);
        this.server = s;
        this.player = receiver;
    }

    public void send() {
        if(this.player == null) {
            Util.log(Level.WARNING, "Attempting to send alert with no specified player");
            return;
        }
        this.send(this.player);
    }

    public void send(QPlayer player) {
        String Apre = "";
        String message = "";
        if(player.getPlayer().hasPermission(alertType.permission())) {
            if (alertType.requireArgs()) {
                Entry<Method, Object> entry = AlertTypeHandler.getAlertTypeMethod(alertType.name());
                try {
                    entry.getKey().invoke(entry.getValue(), new AlertArgs());
                } catch (IllegalArgumentException e) {
                    Util.printException(e);
                } catch (IllegalAccessException e) {
                    Util.printException(e);
                } catch (InvocationTargetException e) {
                    Util.printException(e);
                }

            } else {
                message = Apre + alertType.prefix() + this.message;
            }

            if(displayPrefix) {
                Apre = QCChat.getPhrase("alert_prefix");
            }

            if (player.isOnline()) {
                player.getPlayer().sendMessage(message);
            }
        }
    }

    public void setAsRead() {
        this.read = true;
    }

    public void setAsUnRead() {
        this.read = true;
    }
}
