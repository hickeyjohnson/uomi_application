package ca.outercove.uomiapplication.appObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is meant for creating AccountsViewItems and content for the list of
 * accounts in the AccountViewFragment View.
 */
public class NotificationsContent {

    public static final List<NotificationsListItem> ITEMS = new ArrayList<>();
    public static final Map<Integer, NotificationsListItem> ITEM_MAP = new HashMap<>();

    private static void addItem(NotificationsListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    static {
        // Add some sample items.
        for (int i = 1; i <= 15; i++) {
            // TODO: users and stuff
            addItem(createNotificationsListItem(i, null, null));
        }
    }

    private static NotificationsListItem createNotificationsListItem(Integer id, User me, User you) {
        return new NotificationsListItem(id, "Nathaniel Johnson owes you $1,000");
    }



    public static class NotificationsListItem {
        // TODO: contact image
        public final Integer id;
        public final String notificationText;

        public NotificationsListItem(Integer id, String notificationText) {
            this.id = id;

            this.notificationText = notificationText;
        }
    }
}
