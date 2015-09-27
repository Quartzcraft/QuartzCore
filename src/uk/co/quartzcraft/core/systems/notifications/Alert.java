package uk.co.quartzcraft.core.systems.notifications;

import org.bukkit.Note;
import org.bukkit.Sound;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.data.QServer;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.logging.Level;

public class Alert {

    private String message = null;
    private String[] args = null;
    private boolean displayPrefix = true;
    private AlertType alertType = null;
    private QServer server = null;
    private QPlayer player = null;
    private boolean read = false;

    public Alert(String msg, String[] argss, boolean dP, String type, QServer s) {
        if(msg.equals("") && type.equals("") && args == null) {
            Util.log(Level.WARNING, "Invalid alert arguments!");
        }
        this.message = msg;
        this.args = argss;
        this.displayPrefix = dP;
        this.alertType = AlertTypeHandler.getAlertType(type);
        this.server = s;
    }

    public Alert(String msg, String[] argss, boolean dP, String type, QServer s, QPlayer receiver) {
        if(msg.equals("") && type.equals("") && args == null) {
            Util.log(Level.WARNING, "Invalid alert arguments!");
        }
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
        String Apre = QCChat.getPhrase("alert_prefix");
        String msg = "";
        Object returnedMessage = "";
        if (displayPrefix) {
            Apre = QCChat.getPhrase("alert_prefix");
        }

        if(alertType.permission().equals("") || player.getPlayer().hasPermission(alertType.permission())) {
            if (alertType.requireArgs()) {
                Entry<Method, Object> entry = AlertTypeHandler.getAlertTypeMethod(alertType.name());

                if(entry.getKey().getReturnType() == String.class) {
                    try {
                        returnedMessage = entry.getKey().invoke(entry.getValue(), new AlertArgs());
                    } catch (IllegalArgumentException e) {
                        Util.printException(e);
                    } catch (IllegalAccessException e) {
                        Util.printException(e);
                    } catch (InvocationTargetException e) {
                        Util.printException(e);
                    }

                    msg = Apre + alertType.prefix() + returnedMessage;
                }
            } else {
                msg = Apre + alertType.prefix() + this.message;
            }

            if (player.isOnline()) {
                player.getPlayer().sendMessage(msg);
                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.LEVEL_UP, 10, 1);
                //player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.NOTE_PLING, 10, 1);
                this.read = true;
            }

            this.message = msg;
            this.save();
        }
    }

    public void setAsRead() {
        this.read = true;
        this.player.setUnreadAlertCount(player.getUnreadAlertCount() - 1);
        this.save();
    }

    public void setAsUnRead() {
        this.read = false;
        this.save();
    }

    private void save() {
        if(this.player == null) {
            Util.log(Level.WARNING, "Attempting to save alert with no specified player");
            return;
        } else {
            if(!this.read) {
                player.setUnreadAlertCount(player.getUnreadAlertCount() + 1);
            }

            try {
                java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("INSERT INTO Alerts (player_id, display_prefix, alert_type, message, read) VALUES (?, ?, ?, ?, ?);");
                s.setInt(1, this.player.getID());
                s.setBoolean(2, this.displayPrefix);
                s.setString(3, this.alertType.name());
                s.setString(4, this.message);
                s.setBoolean(4, this.read);
            } catch (SQLException e) {
                Util.printException("Failed to insert QPlayer into database", e);
            }
        }
    }
}
