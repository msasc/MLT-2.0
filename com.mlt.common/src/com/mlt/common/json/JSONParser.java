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

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

/**
 * JSON parser. Admits standard JSON values (number, string, true, false, null, object and array),
 * and extended types like binary.
 * @author Miquel Sas
 */
public class JSONParser {

	/**
	 * Enum the kind of token.
	 */
	private enum Kind {
		/** End of file/stream. */
		EOF,
		/** Structural char. */
		STRUCT,
		/** Key. */
		KEY,
		/** Value. */
		VALUE,
		/** Unknown. */
		UNKNOWN
	}

	/**
	 * Tokens retrived by the parser.
	 */
	private static class Token {

		/** Kind of token. */
		private Kind kind;
		/** Type of the value. */
		private Type type;
		/** Value. */
		private Object value;

		/**
		 * Constructor.
		 * @param kind  Kind of token.
		 * @param type  Type of the value.
		 * @param value Value.
		 */
		public Token(Kind kind, Type type, Object value) {
			this.kind = kind;
			this.type = type;
			this.value = value;
		}
		/**
		 * Return a string representation.
		 * @return The string representation.
		 */
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(kind);
			b.append(", ");
			b.append(type);
			b.append(", ");
			b.append(value);
			return b.toString();
		}
	}

	/** Reader. */
	private Reader reader;
	/** Document to fill with parsed data. */
	private JSONDoc document;
	/** Next token cached (used only when reading a number). */
	private Token nextToken;

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
		return parse(reader, new JSONDoc());
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
		this.nextToken = null;
		parse();
		return document;
	}
	/**
	 * Parse the source and fill the document.
	 * @throws IOException If an error occurs.
	 */
	private void parse() throws IOException {
		while (true) {
			Token token = nextToken();
			System.out.println(token);
			if (token.kind == Kind.EOF) break;
		}
	}
	/**
	 * If required reads tokens from the stream and return the next token.
	 * @return The next token.
	 * @throws IOException If an error occurs.
	 */
	private Token nextToken() throws IOException {

		// If the queue of tokens is not empty, return the first one.
		if (nextToken != null) {
			Token token = nextToken;
			nextToken = null;
			return token;
		}

		// Read the next neat char.
		int c = nextChar(true);

		// -1, end of stream
		if (c < 0) {
			return new Token(Kind.EOF, null, null);
		}

		// Structural char.
		if ("[]{}:,".indexOf(c) >= 0) {
			return new Token(Kind.STRUCT, Type.STRING, String.valueOf((char) c));
		}

		// '"', start of a string or key. Read chars up to the end of the string and then check
		// if could be a date, time, timestamp or a string.
		if (c == '\"') {
			String str = readString();
			return new Token(Kind.UNKNOWN, Type.STRING, str);
		}

		// Analyze true, false, null or number. Although at this point we do not know if the
		// token is a KEY or a VALUE, we may infer it is a VALUE if it is not a format error.

		// 't' possible start of the token true.
		if (c == 't') {
			StringBuilder b = new StringBuilder();
			b.append((char) c);
			for (int i = 0; i < 3; i++) {
				b.append((char) nextChar(false));
			}
			if (b.toString().equals("true")) {
				return new Token(Kind.VALUE, Type.BOOLEAN, true);
			}
			throw new IOException("Format error");
		}

		// 'f' possible start of the token false.
		if (c == 'f') {
			StringBuilder b = new StringBuilder();
			b.append((char) c);
			for (int i = 0; i < 4; i++) {
				b.append((char) nextChar(false));
			}
			if (b.toString().equals("false")) {
				return new Token(Kind.VALUE, Type.BOOLEAN, false);
			}
			throw new IOException("Format error");
		}

		// 'n' possible start of the token null.
		if (c == 'n') {
			StringBuilder b = new StringBuilder();
			b.append((char) c);
			for (int i = 0; i < 3; i++) {
				b.append((char) nextChar(false));
			}
			if (b.toString().equals("null")) {
				return new Token(Kind.VALUE, Type.NULL, null);
			}
			throw new IOException("Format error");
		}

		// Next token must be a number.
		if ("-0123456789".indexOf(c) >= 0) {
			StringBuilder b = new StringBuilder();
			b.append((char) c);
			while (true) {
				c = nextChar(false);
				if (c <= 32) {
					throw new IOException("Format error");
				}
				if ("+-.0123456789eE".indexOf(c) >= 0) {
					b.append((char) c);
					continue;
				}
				if (":,]}".indexOf(c) >= 0) {
					try {
						BigDecimal dec = new BigDecimal(b.toString());
						nextToken = new Token(Kind.STRUCT, Type.STRING, String.valueOf((char) c));
						return new Token(Kind.VALUE, Type.NUMBER, dec);
					} catch (NumberFormatException exc) { throw new IOException("Format error"); }
				}
			}
		}
		throw new IOException("Format error");
	}
	/**
	 * Read the next string, may be empty.
	 * @return The read string.
	 * @throws IOException If an error occurs.
	 */
	private String readString() throws IOException {
		StringBuilder b = new StringBuilder();
		while (true) {
			int c = nextChar(false);

			// -1, reached end of stream before the string is closed, it is a format error.
			// Can not be 0 to 31, these must be unicode escaped.
			if (c <= 31) throw new IOException("Format error");

			// '"' The end of the string has been reached.
			if (c == '\"') break;

			// '\' escape indicator, read the next char to see whether it is a two-character
			// escape sequence or the start (u) of a potential unicode escape.
			if (c == '\\') {
				int n = nextChar(false);
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
					StringBuilder u = new StringBuilder();
					for (int i = 0; i < 4; i++) {
						int m = nextChar(false);
						if ("0123456789abcdefABCDEF".indexOf(m) >= 0) u.append((char) m);
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
	 * Returns the next char. If the argument neat is set to true, then the next char GT 32 will be
	 * returned. Returns -1 if the end of the stream is reached.
	 * @param neat A boolean that indicates whether next char should be neat, that is, GT 32.
	 * @return The next char, whether neat or not, -1 if the end of the stream is reached.
	 * @throws IOException If an error occurs.
	 */
	private int nextChar(boolean neat) throws IOException {
		while (true) {
			int c = reader.read();
			if (!neat) return c;
			if (c < 0 || c > 32) return c;
		}
	}
}

