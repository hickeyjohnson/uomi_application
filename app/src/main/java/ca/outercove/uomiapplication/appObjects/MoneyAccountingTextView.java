package ca.outercove.uomiapplication.appObjects;

import android.support.v7.widget.AppCompatTextView;


/**
 * This class is meant for dynamically styling a string representing money
 * so that if the amount is negative, the text will be red, if positive, the amount will be green.
 * It will add separation commas where necessary.
 */
public class MoneyAccountingTextView extends AppCompatTextView {


    public MoneyAccountingTextView() {
        super(null);
        throw new UnsupportedOperationException("Need to specialize TextView for account");
    }

}
