package com.udacity.stockhawk.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by ahmed on 12/8/2016.
 */

public class FormatUtils {

    public static String formatPrice(double price){
        DecimalFormat dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
        dollarFormat.setCurrency(Currency.getInstance(Locale.US));
        return dollarFormat.format(price);
    }

    public static String formatChangePercentage(double changePercentage){
        DecimalFormat percentageFormat
                = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
        return percentageFormat.format(changePercentage);
    }

    public static String formatChangeAmout(double change){
        DecimalFormat dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
        dollarFormatWithPlus.setPositivePrefix("+");
        return dollarFormatWithPlus.format(change);
    }
}

