package uk.co.quartzcraft.core.systems.chat;

import org.bukkit.Bukkit;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.util.Util;

public class PreparedMessage {

    private String msg;

    public PreparedMessage() {
        this.msg = null;
    }

    public PreparedMessage(String message) {
        this.msg = message;
    }

    public PreparedMessage setMessage(String msg) {
        this.msg = msg;

        return this;
    }

    public PreparedMessage insertPhrase(String phrase_id) {
        if(this.msg != null) {
            this.msg = msg + QCChat.getPhrase(phrase_id);
        } else {
            this.msg = QCChat.getPhrase(phrase_id);
        }

        return this;
    }

    public PreparedMessage append(String message) {
        if(this.msg != null) {
            this.msg = msg + " " + message;
        } else {
            this.msg = message;
        }

        return this;
    }

    public PreparedMessage prefix(String message) {
        if(this.msg != null) {
            this.msg = message + " " + msg;
        } else {
            this.msg = message;
        }

        return this;
    }

    public PreparedMessage setVar(String var, String message) {
        this.msg = message.replaceAll(var, message);

        return this;
    }

    public void sendMessage(QPlayer player) {
        player.sendMessage(this.msg);
    }

    public void sendMessage(String[] players) {
        for(String player : players) {
            Util.sendMsg(Bukkit.getServer().getPlayer(player), this.msg);
        }
    }

    public int broadcastMessage() {
        return Bukkit.getServer().broadcastMessage(this.msg);
    }

    public String getMessage() {
        return this.msg;
    }
}
