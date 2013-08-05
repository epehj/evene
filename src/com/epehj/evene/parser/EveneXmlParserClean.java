package com.epehj.evene.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.res.Resources;
import android.os.AsyncTask;

/**
 * Ressemble beaucoup a EveneXmlParser mais retourne une liste de maps,
 * contenant les citations plutot qu'une liste de citations
 * 
 * @author m.sette
 * 
 */
public class EveneXmlParserClean extends AsyncTask<Void, Void, List<Map<String, String>>> {

	public EveneXmlParserClean(final Resources resources) {
	}

	@Override
	protected List<Map<String, String>> doInBackground(final Void... params) {
		final EveneHandler df = new EveneHandler();
		try {
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
		return df.get();
	}
}
