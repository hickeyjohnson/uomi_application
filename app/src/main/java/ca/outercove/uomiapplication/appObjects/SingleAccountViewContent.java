package ca.outercove.uomiapplication.appObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class creates TransactionItems and content for the list of transactions in
 * the SingleAccountFragment View.
 */
public class SingleAccountViewContent {


    public static final List<TransactionItem> ITEMS = new ArrayList<>();
    public static final Map<Integer, TransactionItem> ITEM_MAP = new HashMap<>();

    private static void addItem(TransactionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static TransactionItem createTransactionViewItem(Integer id, String transName, String transPayer, Double transAmnt) {
        return new TransactionItem(id, transName, transPayer, transAmnt);
    }

    public static class TransactionItem {
        // TODO: contact image
        public final Integer id;
        public final String transactionName;
        public final String transactionPayer;
        public final Double value;

        public TransactionItem(Integer id, String transactionName, String transactionPayer, Double value) {
            this.id = id;
            this.transactionName = transactionName;
            this.transactionPayer = transactionPayer;
            this.value = value;
        }
    }
}
