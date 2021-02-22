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

import org.jetbrains.annotations.NotNull;

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
			// Read next value as an entry.
			Entry entry = readEntry();
			// Put data.
			doc.put(key, entry);
		}
		return doc;
	}
	/**
	 * Parse the started list and return it.
	 * @return The read list.
	 * @throws IOException If an error occurs.
	 */
	private JSONList parseList() throws IOException {
		return null;
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
		if (c <= 32 || (c != '\"' && c != '}')) throw new IOException("Format error");
		if (c == '}') return b.toString();
		// The char is '\"', continue reading chars until the next '\"' or an invalid char
		// or the end of the stream is reached.
		while (true) {
			c = next(false);
			if (c <= 31) throw new IOException("Format error");
			if (c == '\"') break;
			b.append((char) c);
		}
		return b.toString();
	}
	/**
	 * Read the next string, may be empty.
	 * @return The read string.
	 * @throws IOException If an error occurs.
	 */
	private String readString() throws IOException {
		StringBuilder b = new StringBuilder();
		while (true) {
			int c = next(false);
			// -1, reached end of stream before the string is closed, it is a format error.
			// Can not be 0 to 31, these must be unicode escaped.
			if (c <= 31) throw new IOException("Format error");
			// '"' The end of the string has been reached.
			if (c == '\"') break;
			// '\' escape indicator, read the next char to see whether it is a two-character
			// escape sequence or the start (u) of a potential unicode escape.
			if (c == '\\') {
				int n = next(false);
				if (n < 0) throw new IOException("Format error");
				// Two-character escape sequences.
				switch (n) {
				case '\"':
				case '\\':
				case '\b':
				case '\f':
				case '\n':
				case '\r':
				case '\t':
					b.append((char) c);
					continue;
				}
				// Potential unicode escape. Read 4 more chars, that must be digits or letters
				// a to f, or A to F. Any other char is a format error.
				if (n == 'u') {
					String hex = "0123456789abcdefABCDEF";
					StringBuilder u = new StringBuilder();
					for (int i = 0; i < 4; i++) {
						int m = next(false);
						if (hex.indexOf(m) >= 0) u.append((char) m);
						else throw new IOException("Format error");
					}
					b.append((char) Integer.parseInt(u.toString(), 16));
					continue;
				}
				throw new IOException("Format error");
			}
			// Valid non escaped char, just accept.
			b.append((char) c);
		}
		return b.toString();
	}
	/**
	 * Read a value as an entry.
	 * @return The value as an entry.
	 * @throws IOException If an error occurs.
	 */
	private Entry readEntry() throws IOException {
		// Next neat char will determine the type of entry that should be read.
		// '{' -> the entry is a nested document that must be parsed.
		// '[' -> the entry is an rray/list that must be parsed.
		// '"' -> the entry is a string, date, time or timestamp field.
		// A digit (0123456789) or the letters 't', 'f' or 'n', that denote a number
		// if properly formatted, or the start of the tokens true, false or null.
		// Any other char is a format error.
		int c = next(true);
		// Document, parse it.
		if (c == '{') {
			return new Entry(Type.DOCUMENT, parseDoc());
		}
		// List, parse it.
		if (c == '[') {
			return new Entry(Type.LIST, parseList());
		}
		// String, first read it and then check whether it could be a date, time or timestamp.
		if (c == '\"') {
			String str = readString();
		}
		return null;
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
