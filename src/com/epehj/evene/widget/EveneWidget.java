package com.epehj.evene.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.epehj.evene.R;

public class EveneWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(final Context context, final AppWidgetManager appWidgetManager,
			final int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int n = appWidgetIds.length;
		for (int i = 0; i < n; i++) {
			updateAppWidgets(context, appWidgetManager, appWidgetIds[i]);
		}
	}

	private void updateAppWidgets(final Context context, final AppWidgetManager appWidgetManager,
			final int i) {
		final RemoteViews rv = new RemoteViews(context.getPackageName(),
				R.layout.evene_widget_layout);
		rv.setTextViewText(R.id.evene_widget_id, "My First Widget");
		appWidgetManager.updateAppWidget(i, rv);
	}

}
