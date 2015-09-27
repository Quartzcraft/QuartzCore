package uk.co.quartzcraft.core.systems.notifications;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlertType {

    /**
     * The name of the alert type.
     *
     * @return
     */
    public String name();

    /**
     * Gets the required permission to receive this type of alert
     *
     * @return
     */
    public String permission() default "";

    /**
     * The prefix displayed in front of the alert so that the user receiving knows what plugin or subsystem sent the alert
     *
     * @return
     */
    public String prefix() default "";

    /**
     * Determine whether certain arguments must be passed to the alert. If this is set to false, only the message and prefix will be sent.
     *
     * @return
     */
    public boolean requireArgs() default false;
}
