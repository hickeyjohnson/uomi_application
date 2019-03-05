package ca.outercove.uomiapplication.appObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleAccountViewContent {


    public static final List<TransactionItem> ITEMS = new ArrayList<>();
    public static final Map<Integer, TransactionItem> ITEM_MAP = new HashMap<>();

    private static void addItem(TransactionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    static {
        // Add some sample items.
        for (int i = 1; i <= 15; i++) {
            // TODO: users and stuff
            addItem(createTransactiomViewItem(i, null, null));
        }
    }

    private static TransactionItem createTransactiomViewItem(Integer id, User me, User you) {
        return new TransactionItem(id, "Pizza Money", findValue(me, you));
    }

    public static Double findValue(User me, User you) {
        // TODO: query web api for what the value is
        return 20.19;
    }

    public static class TransactionItem {
        // TODO: contact image
        public final Integer id;
        public final String transactionName;
        public final Double value;

        public TransactionItem(Integer id, String transactionName, Double value) {
            this.id = id;
            this.transactionName = transactionName;
            this.value = value;
        }
    }
}
