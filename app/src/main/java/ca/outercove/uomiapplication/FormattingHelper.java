package ca.outercove.uomiapplication;

import org.json.JSONArray;
import org.json.JSONException;


public class FormattingHelper {

    public static String commaSeparate(JSONArray items) {
        StringBuilder formatted = new StringBuilder();

        // Iterate through the itemset adding ", " after each element except the last
        for (int i = 0; i < items.length() - 1; i++) {
            try {
                formatted.append(items.getString(i)).append(", ");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Add in the last item with no separator
        try {
            formatted.append(items.getString(items.length() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return formatted.toString();
    }
}
