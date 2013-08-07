package com.epehj.evene;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.epehj.evene.parser.EveneHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity {

	ProgressDialog dialog;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		checkLastUpdate(true);
	}

	private void checkLastUpdate(final boolean test) {
		// recup des preferences
		final SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		final String lud = appSharedPrefs.getString("lastUpdateDate", null);
		final Date today = new Date();
		final SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
		if (test || lud == null || !lud.equalsIgnoreCase(sf.format(today))) {
			updateQuotesAndDate(appSharedPrefs);
		}
		// recuperer les citations enregistrées dans les prefs et les
		// afficher
		final String jsonCitations = appSharedPrefs.getString("quotes", null);
		if (jsonCitations != null) {
			final Type collectionType = new TypeToken<Collection<Map<String, String>>>() {
			}.getType();
			final List<Map<String, String>> citations = new Gson().fromJson(jsonCitations, collectionType);

			// affichage de la liste
			final SimpleAdapter sa = new SimpleAdapter(getBaseContext(), citations, R.layout.affichageitems, new String[] { "auteur", "citation" }, new int[] {
					R.id.auteur, R.id.citation });
			final ListView lv = (ListView) findViewById(R.id.lvComplete);
			lv.setAdapter(sa);

		}
	}

	private void updateQuotesAndDate(final SharedPreferences pref) {

		final List<Map<String, String>> citations = downloadQuotes();
		final String jsonCitations = new Gson().toJson(citations);
		final Editor editor = pref.edit();
		final Date today = new Date();
		final SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

		editor.putString("lastUpdateDate", sf.format(today));
		editor.putString("quotes", jsonCitations);
		editor.commit();

	}

	// public void getCitations_Clicked(final View v) {
	// // final Intent intent = new Intent(MainActivity.this,
	// // ListeCitationsClean.class);
	// // startActivity(intent);
	// }

	private List<Map<String, String>> downloadQuotes() {
		// Toast.makeText(MainActivity.this,
		// getResources().getString(R.string.downloading),
		// Toast.LENGTH_SHORT).show();
		final InternalEveneXmlParser parser = new InternalEveneXmlParser();
		List<Map<String, String>> citations = null;
		try {
			// get permet d'attendre la fin de l'exec du thread
			citations = (List<Map<String, String>>) parser.execute(null, null).get();

		} catch (final InterruptedException e) {
			e.printStackTrace();
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
		return citations;

	}

	private class InternalEveneXmlParser extends AsyncTask<Void, Void, List<Map<String, String>>> {

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage(getResources().getString(R.string.downloading));
			dialog.show();
		}

		@Override
		protected void onPostExecute(final List<Map<String, String>> result) {
			if (MainActivity.this.dialog.isShowing()) {
				MainActivity.this.dialog.dismiss();
			}

		}

		@Override
		protected List<Map<String, String>> doInBackground(final Void... params) {
			final EveneHandler df = new EveneHandler();
			try {
				// res.openRawResource(R.raw.daily);
				final InputStream source = new URL("http://www.evene.fr/rss/citation_jour.xml").openStream();
				final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
				parser.parse(source, df);
			}

			catch (final MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// df.getCitations();
			return df.get();
		}
	}

}
