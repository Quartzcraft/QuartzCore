package uk.co.quartzcraft.core.systems.notifications;

import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.data.QServer;

public class AlertBuilder {

    protected String message = "";
    protected AlertArgs arguments = null;
    protected boolean displayPrefix = true;
    protected String alertType = "";
    protected QServer server = null;
    private QPlayer receiver = null;

    public AlertBuilder() {

    }

    public AlertBuilder setMessage(String msg) {
        this.message = msg;
        return this;
    }

    public AlertBuilder setArgs(AlertArgs args) {
        this.arguments = args;
        return this;
    }

    public AlertBuilder displayPrefix(boolean display) {
        this.displayPrefix = display;
        return this;
    }

    public AlertBuilder setType(String type) {
        this.alertType = type;
        return this;
    }

    public AlertBuilder setServer(QServer s) {
        this.server = s;
        return this;
    }

    public AlertBuilder setReceiver(QPlayer player) {
        this.receiver = player;
        return this;
    }

    public Alert getAlert() {
        return new Alert(this.message, this.arguments, this.displayPrefix, this.alertType, this.server, this.receiver);
    }

    public void send() {
        this.getAlert().send();
    }
}
