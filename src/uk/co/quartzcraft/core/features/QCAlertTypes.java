package uk.co.quartzcraft.core.features;

import uk.co.quartzcraft.core.systems.notifications.AlertArgs;
import uk.co.quartzcraft.core.systems.notifications.AlertType;

public class QCAlertTypes {

    public QCAlertTypes() {

    }

    @AlertType(name = "QC", prefix = "&8[&6QC&8]", requireArgs = false)
    public String QC(AlertArgs args) {
        return "";
    }
}
