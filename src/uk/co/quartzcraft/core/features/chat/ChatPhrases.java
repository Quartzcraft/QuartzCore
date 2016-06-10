package uk.co.quartzcraft.core.features.chat;

import uk.co.quartzcraft.core.systems.chat.QCChat;

public class ChatPhrases {

    public static void phrases() {
        QCChat.addPhrase("test_phrase", "&3This is a test of the phrases system.");

        QCChat.addPhrase("official_prefix", "&8[&6QC&8]");
        QCChat.addPhrase("alert_prefix", "&a[Alert]");

        QCChat.addPhrase("could_not_create_player", "&cYou're player data could not be added to the database!");
        QCChat.addPhrase("to_register_on_the_website_please_visit_web", "&aTo register on the QuartzCraft website, please visit &rhttp://quartzcraft.co.uk");
        QCChat.addPhrase("these_are_the_fields_required_for_website_registration", "&aThese are the values for the required website registration fields:");
        QCChat.addPhrase("your_minecraft_username_is", "&aYour Minecraft username is:&r ");
        QCChat.addPhrase("your_validation_code_is", "&aYour validation code is:&r ");
        QCChat.addPhrase("your_quartzcore_id_is", "&aYour QuartzCraft ID is:&r ");

        QCChat.addPhrase("teleported_you_to_player_X", "&aYou have been teleported to &r ");
        QCChat.addPhrase("X_has_been_teleported_to_you", " &ahas been teleported to you");

        QCChat.addPhrase("no_permission", "&cYou do not have permission to perform this action!");
        QCChat.addPhrase("Unknown_SubCommand", "&cCould not find the specified SubCommand! &aType /help for help.");
        QCChat.addPhrase("Unknown_Command", "&cUnknown Command! &aType /help for help.");
        QCChat.addPhrase("Specify_Subcommand", "&cPlease specify a sub command. &aType /help for help.");
        QCChat.addPhrase("feature_unavalible", "&cThis feature is currently unavailable &aVisit the QuartzCraft Website for information on new features.");
        QCChat.addPhrase("database_error", "&cA database error occurred!");
        QCChat.addPhrase("database_error_try_again", "&cA database error occurred! &aPlease try again");
        QCChat.addPhrase("database_error_contact", "&cA database error occurred! &aPlease contact an administrator on the QuartzCraft website immediately.");
        QCChat.addPhrase("player_use_only", "&4This command can only be used in game by a player.");
        QCChat.addPhrase("specify_username", "&cPlease specify a player!");
        QCChat.addPhrase("specify_online_username", "&cPlease specify an online player!");
        QCChat.addPhrase("specify_arguments", "&cPlease specify some arguments!");

        QCChat.addPhrase("information_on_player_X", "&aInformation on player &r");
        QCChat.addPhrase("group", "&aGroup: &r");
        QCChat.addPhrase("first_join", "&aFirst joined: &r");
        QCChat.addPhrase("last_seen", "&aLast seen: &r");
        QCChat.addPhrase("last_active", "&aLast active: &r");
        QCChat.addPhrase("is_online_now", "&ais online now!");
        QCChat.addPhrase("is_online_now_on_server", "&ais online now on server&r");

        QCChat.addPhrase("you_have_no_unread_alerts", "&aYou have no unseen alerts!");

        QCChat.addPhrase("please_specify_player_to_report", "&cYou must specify a player to report!");
        QCChat.addPhrase("thank_you_for_reporting_user", "Thank you for submitting a report. It is unlikely that we will need contact you.");
        QCChat.addPhrase("error_submitting_report", "&cAn error occurred while submitting your report! Please try again or contact a QuartzCraft administrator.");

        QCChat.addPhrase("you_are_currently_in_world", "&aYou are currently in world ");

        QCChat.addPhrase("Server_Full", "&cServer Full!\n &aIt appears that the server is full. Please try again later, or purchase a supporter rank at http://quartzcraft.co.uk/index.php?upgrade");
        QCChat.addPhrase("Kick_Whitelist", "&cYou are not whitelisted");
        QCChat.addPhrase("you_can_only_be_connected_to_one_server_at_a_time", "&cYou can only be connected to one server at a time!");

        QCChat.addPhrase("promoted_player_yes", "&aThe players group was successfully changed!");
        QCChat.addPhrase("promoted_player_no", "&cThe player failed to move groups!");

        QCChat.addPhrase("could_not_fit_item_dropped", "&cThe item could not fit in your inventory and was dropped on the ground!");

        QCChat.addPhrase("chat_contained_bad_words_blocked", "&cThe chat message you attempted to send contained inappropriate content and was blocked!");
    }
}
