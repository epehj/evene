package com.epehj.evene;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.epehj.evene.parser.EveneXmlParserClean;

public class ListeCitationsClean extends Activity {

	private List<Map<String, String>> citations;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_citations);

		final EveneXmlParserClean parser = new EveneXmlParserClean(getResources());
		try {
			// get permet d'attendre la fin de l'exec du thread
			citations = (List<Map<String, String>>) parser.execute(null, null).get();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}

		if (citations != null) {
			final SimpleAdapter sa = new SimpleAdapter(getBaseContext(), citations, R.layout.affichageitems, new String[] { "auteur", "citation" }, new int[] {
					R.id.auteur, R.id.citation });
			final ListView lv = (ListView) findViewById(R.id.lvComplete);
			lv.setAdapter(sa);
		}
	}
}
