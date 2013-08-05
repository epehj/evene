package com.epehj.evene.pojo;

public class Citation {
	private String auteur;

	/** reprend la citation evene dans son ensemble, avec le nom de l'auteur */
	private final StringBuffer phrase;

	public Citation() {
		phrase = new StringBuffer();
	}

	public void setCitation(final char[] ch, final int start, final int length) {
		phrase.append(new String(ch, start, length));
	}

	public String getAuteur() {
		if (phrase != null) {
			auteur = phrase.substring(0, phrase.indexOf("-") - 1);
		}
		return auteur;
	}

	public String getCitation() {
		String s = null;
		if (phrase != null) {
			s = phrase.substring(phrase.indexOf("-") + 1);
		}
		return s;
	}

	@Override
	public String toString() {
		return getCitation() + " - " + getAuteur();
	}

}
