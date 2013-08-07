package com.epehj.evene.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epehj.evene.pojo.Citation;

public class EveneHandler extends DefaultHandler {

	private boolean inItem = false;
	private boolean inTitle = false;
	private List<Citation> citations;
	private Citation citation;

	@Override
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		if (inItem && inTitle && citation != null) {
			citation.setCitation(ch, start, length);
			// citation.append(new String(ch, start, length));
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		inTitle = false;
		inItem = false;
		citation = null;
	}

	@Override
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		if (localName.equals("item")) {
			inItem = false;
		} else if (inItem && localName.equals("title")) {
			citations.add(citation);
			inTitle = false;
			inItem = false;// useless je pense
		}
		citation = null;
	}

	@Override
	public void startDocument() throws SAXException {
		citations = new ArrayList<Citation>();
	}

	@Override
	public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
		if (localName.equals("item")) {
			inItem = true;
		} else if (inItem && localName.equals("title")) {
			inTitle = true;
			citation = new Citation();
		}
	}

	public List<Citation> getCitations() {
		return citations;
	}

	// methode pour adapter le retour a un SimpleAdapter utilisé dans une liste
	public List<Map<String, String>> get() {
		if (citations != null) {
			final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (final Citation c : citations) {
				final Map<String, String> temp = new HashMap<String, String>();
				temp.put("auteur", c.getAuteur());
				temp.put("citation", c.getCitation());
				list.add(temp);
			}
			return list;
		}
		return null;

	}
}
