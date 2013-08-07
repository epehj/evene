package com.epehj.evene;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.epehj.evene.parser.EveneXmlParser;
import com.epehj.evene.pojo.Citation;

public class AllCitationsListViewActivity extends ListActivity {

	private List<Citation> citations;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_citations);
		final EveneXmlParser parser = new EveneXmlParser(getResources());
		try {
			citations = (List<Citation>) parser.execute(null, null).get();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}

		final ArrayAdapter<Citation> aa = new ArrayAdapter<Citation>(this, android.R.layout.simple_list_item_1, citations);
		setListAdapter(aa);
	}
}
