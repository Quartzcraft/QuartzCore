package uk.co.quartzcraft.core.systems.notifications;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.data.QServer;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.logging.Level;

public class Alert {

    private String message = null;
    private AlertArgs args = new AlertArgs();
    private boolean displayPrefix = true;
    private AlertType alertType = null;
    private QServer server = null;
    private QPlayer player = null;
    private boolean read = false;

    public Alert(String msg, AlertArgs argss, boolean dP, String type, QServer s) {
        if(msg.equals("") && type.equals("") && argss == null) {
            Util.log(Level.WARNING, "Invalid alert arguments!");
        }
        this.message = msg;
        this.args = argss;
        this.displayPrefix = dP;
        this.alertType = AlertTypeHandler.getAlertType(type);
        this.server = s;
    }

    public Alert(String msg, boolean dP, String type, QServer s) {
        if(!msg.equals("") && !type.equals("")) {
            Util.log(Level.WARNING, "Invalid alert arguments!");
        }
        this.message = msg;
        this.displayPrefix = dP;
        this.alertType = AlertTypeHandler.getAlertType(type);
        this.server = s;
    }

    public Alert(String msg, AlertArgs argss, boolean dP, String type, QServer s, QPlayer receiver) {
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

        TextComponent componentMsg = new TextComponent("Hi");
        TextComponent returnedComponent = new TextComponent("Hi");
        if (displayPrefix) {
            Apre = Apre + QCChat.getPhrase("official_prefix");
        }

        if(alertType.requireArgs()) { //TODO fix an issue here
            Entry<Method, Object> entry = AlertTypeHandler.getAlertTypeMethod(alertType.name());

            if(entry.getKey().getReturnType() == String.class) {
                try {
                    returnedMessage = entry.getKey().invoke(entry.getValue(), this.args);
                } catch (IllegalArgumentException e) {
                    Util.printException(e);
                } catch (IllegalAccessException e) {
                    Util.printException(e);
                } catch (InvocationTargetException e) {
                    Util.printException(e);
                } catch(NullPointerException e) {
                    Util.printException(e);
                }

                msg = Apre + alertType.prefix() + returnedMessage;
            }

            if(entry.getKey().getReturnType() == BaseComponent[].class) {
                try {
                    returnedComponent = (TextComponent) entry.getKey().invoke(entry.getValue(), this.args);
                } catch (IllegalArgumentException e) {
                    Util.printException(e);
                } catch (IllegalAccessException e) {
                    Util.printException(e);
                } catch (InvocationTargetException e) {
                    Util.printException(e);
                }

                componentMsg = new TextComponent(TextComponent.fromLegacyText(Apre));
                componentMsg.addExtra(returnedComponent);

                msg = Apre + alertType.prefix() + this.message;
            }
        } else {
            msg = Apre + alertType.prefix() + this.message;
        }

        if(player.isOnline()) {
            if (msg.equals("")) {
                player.getPlayer().spigot().sendMessage(componentMsg);
                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                this.read = true;
            } else {
                player.sendMessage(msg);
                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                this.read = true;
            }
        }

        this.message = msg;
        this.save();
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
        }

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
            Util.printException("Failed to insert alert into database", e);
        }
    }

    public static Alert[] getAlerts(QPlayer player) {
        int rows = 0;

        try {
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM Alerts WHERE player_id=?;");
            s.setInt(1, player.getID());
            ResultSet res = s.executeQuery();
            if (res.last()) {
                rows = res.getRow();
                // Move to beginning
                res.beforeFirst();
            }

            Alert[] map = new Alert[rows];
            if(res.next()) {
                map[res.getRow()] = new Alert(res.getString("message"), null, res.getBoolean("display_prefix"), res.getString("alert_type"), null, new QPlayer(res.getInt("player_id")));
            }
            return map;
        } catch (SQLException e) {
            Util.printException("Failed to retrieve alerts for player data from database", e);
            return null;
        }
    }
}
