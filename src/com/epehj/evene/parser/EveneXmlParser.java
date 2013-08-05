package com.epehj.evene.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.epehj.evene.pojo.Citation;

public class EveneXmlParser extends AsyncTask<Void, Void, List<Citation>> {

	private List<Citation> citations;

	public EveneXmlParser(final Resources resources) {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Citation> doInBackground(final Void... params) {
		final EveneHandler df = new EveneHandler();
		try {

			final InputStream source = // res.openRawResource(R.raw.daily);
			new URL("http://www.evene.fr/rss/citation_jour.xml").openStream();
			final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();

			parser.parse(source, df);
			// citations = df.getCitations();
			// df.getCitations().get(0).getAuteur();
			// System.out.println("Size " + df.getCitations().size());
			// final XPathFactory xPathfactory = XPathFactory.newInstance();
			// final XPath xpath = xPathfactory.newXPath();
			// final XPathExpression expr = xpath
			// .compile("description/item[0]/title/text()");
			// expr.evaluate(source, XPathConstants.STRING);
		}
		// catch (final XPathExpressionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
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
		return df.getCitations();
	}

	@Override
	protected void onPostExecute(final List<Citation> result) {
		citations = result;
	}

	public List<Citation> getCitations() {
		return citations;
	}

}
