package com.udacity.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by ahmed on 12/8/2016.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        // read the list of stocks from the database
        List<WidgetListItem> items = new ArrayList<>();
        Cursor cursor = getContentResolver()
                .query(
                        Contract.Quote.uri,
                        Contract.Quote.QUOTE_COLUMNS,
                        null,
                        null,
                        null
                );
        if (cursor.moveToFirst())
            do {
                String symbol = cursor.getString(Contract.Quote.POSITION_SYMBOL);
                double price = cursor.getDouble(Contract.Quote.POSITION_PRICE);
                double change = cursor.getDouble(Contract.Quote.POSITION_PERCENTAGE_CHANGE);
                String history = cursor.getString(Contract.Quote.POSITION_HISTORY);
                items.add(new WidgetListItem(symbol, price, change, history));
            } while (cursor.moveToNext());


        return new WidgetsViewFactory(getApplicationContext(), items);
    }
}
