package com.udacity.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.FormatUtils;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.StockHistory;
import com.udacity.stockhawk.ui.StockHistoryActivity;

import java.util.List;

/**
 * Created by ahmed on 12/8/2016.
 */
public class WidgetsViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<WidgetListItem> mData;
    private Context mContext;

    public WidgetsViewFactory(Context context, List<WidgetListItem> data) {
        this.mContext = context;
        mData = data;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        // bind the data to the view
        WidgetListItem item = mData.get(position);
        final RemoteViews remoteView = new RemoteViews(
                mContext.getPackageName(), R.layout.list_item_quote);
        remoteView.setTextViewText(R.id.symbol, item.getName());
        remoteView.setTextViewText(R.id.price, FormatUtils.formatPrice(item.getPrice()));
        remoteView.setTextViewText(R.id.change, FormatUtils.formatChangePercentage(item.getChange()/100));
        remoteView.setTextColor(R.id.change,mContext.getResources().getColor(
                item.getChange() > 0 ? R.color.material_green_700 : R.color.material_red_700)
        ) ;

        // add the intent for on click
        StockHistory stockHistory = new StockHistory(item.getName(), item.getHistory());
        Intent intent = StockHistoryActivity.newIntent(null, stockHistory);
        remoteView.setOnClickFillInIntent(R.id.view_container, intent);
        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }
}
