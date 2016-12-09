package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.StockHistoryActivity;

/**
 * Created by ahmed on 12/8/2016.
 */

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            RemoteViews remoteViews = getListRemoteView(context, id);
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews getListRemoteView(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.layout_list_widger);

        // add template intent
        Intent templateIntent = new Intent(context, StockHistoryActivity.class);
        PendingIntent templatPendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(templateIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.list_view, templatPendingIntent);


        // call the service to put data into the list
        Intent updateServiceIntent = new Intent(context, WidgetService.class);
        updateServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        updateServiceIntent.setData(Uri.parse(updateServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(R.id.list_view, updateServiceIntent);

        return remoteViews;
    }
}
