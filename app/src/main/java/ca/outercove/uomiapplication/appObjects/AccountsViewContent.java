package ca.outercove.uomiapplication.appObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is meant for creating AccountsViewItems and content for the list of
 * accounts in the AccountViewFragment View.
 */
public class AccountsViewContent {

    public static final List<AccountsViewItem> ITEMS = new ArrayList<>();
    public static final Map<Integer, AccountsViewItem> ITEM_MAP = new HashMap<>();

    private static void addItem(AccountsViewItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    static {
        // Add some sample items.
        for (int i = 1; i <= 15; i++) {
            // TODO: users and stuff
            addItem(createAccountsViewItem(i, null, null));
        }
    }

    private static AccountsViewItem createAccountsViewItem(Integer id, User me, User you) {
        return new AccountsViewItem(id, "Nathaniel Johnson", findBalance(me, you));
    }


    public static String findBalance(User me, User you) {
        // TODO: query web api for what the balance is
        return "$1,000.00";
    }

    public static class AccountsViewItem {
        // TODO: contact image
        public final Integer id;
        public final String contactName;
        public final String balance;

        public AccountsViewItem(Integer id, String contactName, String balance) {
            this.id = id;
            this.contactName = contactName;
            this.balance = balance;
        }
    }
}
