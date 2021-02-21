/*
 * Copyright (c) 2021. Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.mlt.common.json;

import com.mlt.common.lang.Numbers;

import java.io.IOException;
import java.io.Reader;

/**
 * JSON parser. Admits standard JSON values (number, string, true, false, null, object and array),
 * and extended types like binary.
 * @author Miquel Sas
 */
public class JSONParser {

	/**
	 * Reader.
	 */
	private Reader reader;
	/**
	 * Document to fill with parsed data.
	 */
	private JSONDoc document;
	/**
	 * List of invalid key chars, '\"' is not included because it ends the key.
	 */
	private int[] invalidKeyChars = new int[]{
		' ', '[', '{', ']', '}', ':', ',', '\\', '/', '\b', '\f', '\n', '\r', '\t'
	};

	/**
	 * Constructor.
	 */
	public JSONParser() {}

	/**
	 * Parse the source reader and return the result document.
	 * @param reader The source reader.
	 * @return The parsed document.
	 * @throws IOException If an IO or format error occurs.
	 */
	public JSONDoc parse(Reader reader) throws IOException {
		this.reader = reader;
		this.document = new JSONDoc();
		parse();
		return document;
	}
	/**
	 * Parse the source reader and fill the argument document with the result.
	 * @param reader   The source reader.
	 * @param document The document to fill.
	 * @return The filled document.
	 * @throws IOException If an IO or format error occurs.
	 */
	public JSONDoc parse(Reader reader, JSONDoc document) throws IOException {
		this.reader = reader;
		this.document = document;
		parse();
		return document;
	}
	/**
	 * Parse the source and fill the document.
	 * @throws IOException If an error occurs.
	 */
	private void parse() throws IOException {
		// Get the first neat char. Can be be -1 indicating that the source is empty, or '{'
		// indicating that the document has started.
		int c = next(true);
		if (c < 0) return;
		if (c != '{') return;
		// Parse the started document and append it to the document to fill.
		document.append(parseDoc());
	}
	/**
	 * Parse the started document and return it.
	 * @return The read document.
	 * @throws IOException If an error occurs.
	 */
	private JSONDoc parseDoc() throws IOException {
		JSONDoc doc = new JSONDoc();
		// Read key/value pairs and put them to the document.
		while (true) {
			// Read the key.
			String key = readKey();
			if (key.isEmpty()) break;
			// Read the key/value separator ':' as the next neat char.
			int c = next(true);
			if (c != ':') throw new IOException("Format error");
		}
		return doc;
	}
	/**
	 * Read the next key, may be empty if the document ends.
	 * @return The next key.
	 * @throws IOException If an IO or format error occurs.
	 */
	private String readKey() throws IOException {
		StringBuilder b = new StringBuilder();
		// Next neat char must be '\"' if the key starts, or '}' if the document ends,
		// otherwise if is a format error.
		int c = next(true);
		if (c < 0 || (c != '\"' && c != '}')) throw new IOException("Format error");
		if (c == '}') return b.toString();
		// The char is '\"', continue reading chars until the next '\"' or an invalid char
		// or the end of the stream is reached.
		while (true) {
			c = next(false);
			checkValidKeyChar(c);
			if (c == '\"') break;
			b.append((char) c);
		}
		return b.toString();
	}
	/**
	 * Check that the argument char is valid for a key, must be GT 32 and none of the escape chars.
	 * @param c The char to check valid for a key.
	 * @throws IOException If a format error occurs.
	 */
	private void checkValidKeyChar(int c) throws IOException {
		if (c <= 32 || Numbers.in(c, invalidKeyChars)) throw new IOException("Format error");
	}
	/**
	 * Returns the next char, or -1 if the end of the stream is reached.
	 * @return The next chare, or -1 if the end of the stream is reached.
	 * @throws IOException If an IO error occurs.
	 */
	private int next(boolean neat) throws IOException {
		while (true) {
			int c = reader.read();
			if (!neat) return c;
			if (c < 0 || c > 32) return c;
		}
	}
}
