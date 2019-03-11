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


// SAMPLE VALUES
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= 15; i++) {
//            // TODO: users and stuff
//            addItem(createAccountsViewItem(i, null, null));
//        }
//    }



    private static AccountsViewItem createAccountsViewItem(Integer id, String accountName, Double bal) {
        return new AccountsViewItem(id, accountName, bal);
    }


    public static Double findBalance(User me, User you) {
        // TODO: query web api for what the balance is
        return 34.91;
    }

    public static class AccountsViewItem {
        // TODO: contact image
        public final Integer id;
        public final String contactName;
        public final Double balance;

        public AccountsViewItem(Integer id, String contactName, Double balance) {
            this.id = id;
            this.contactName = contactName;
            this.balance = balance;
        }
    }
}
