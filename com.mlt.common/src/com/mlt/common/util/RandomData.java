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

package com.mlt.common.util;

import com.mlt.common.lang.Strings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Utility to generate random data, mainly for tests.
 *
 * @author Miquel Sas
 */
public class RandomData {

	/**
	 * Code pattern. Any character in the pattern not included in the valid pattern characters is
	 * treated literally.
	 * Valid pattern characters are:
	 * <ul>
	 * <li><b>#</b> a digit</li>
	 * <li><b>A</b> an upper case alpha numerical digit or letter</li>
	 * <li><b>a</b> a lower case alpha numerical digit or letter</li>
	 * <li><b>?</b> an alpha numerical digit or letter with random case</li>
	 * <li><b>L</b> an upper case letter</li>
	 * <li><b>l</b> a lower case letter</li>
	 * <li><b>!</b> a letter with random case</li>
	 * </ul>
	 */
	public static class CodePattern {
		/**
		 * Pattern.
		 */
		private String pattern;
		/**
		 * Source letters.
		 */
		private String letters;
		/**
		 * Source digits.
		 */
		private String digits;
		/**
		 * @param pattern Pattern.
		 */
		public CodePattern(String pattern) {
			this(pattern, Strings.LETTERS.toUpperCase(), Strings.DIGITS);
		}
		/**
		 * @param pattern Pattern.
		 * @param letters Source letters.
		 * @param digits  Source digits.
		 */
		public CodePattern(String pattern, String letters, String digits) {
			this.pattern = pattern;
			this.letters = letters;
			this.digits = digits;
		}
		/**
		 * @return The pattern.
		 */
		public String getPattern() {
			return pattern;
		}
		/**
		 * @return The letters.
		 */
		public String getLetters() {
			return letters;
		}
		/**
		 * @return The digits.
		 */
		public String getDigits() {
			return digits;
		}
	}

	/**
	 * Random.
	 */
	private Random random;
	/**
	 * Constructor.
	 */
	public RandomData() {
		this.random = new Random();
	}
	/**
	 * @return A randomly generated boolean.
	 */
	public boolean getBoolean() {
		return (random.nextInt(2) == 1);
	}
	/**
	 *
	 * @param length The total length.
	 * @return A randomly generated number with zero decimals.
	 */
	public BigDecimal getDecimal(int length) {
		return getDecimal(length, 0);
	}
	/**
	 * @param length   The total length.
	 * @param decimals The number of decimal places.
	 * @return A randomly generated number with optional decimal places.
	 */
	public BigDecimal getDecimal(int length, int decimals) {
		int integer = random.nextInt(length - (decimals > 0 ? decimals + 1 : 0)) + 1;
		String integerPart = getString(Strings.DIGITS, integer);
		String decimalPart = getString(Strings.DIGITS, integer);
		String number = integerPart + "." + decimalPart;
		return new BigDecimal(number).setScale(decimals, RoundingMode.HALF_UP);
	}
	/**
	 * @param source The source string.
	 * @return A random char within the source string.
	 */
	public char getChar(String source) {
		int index = random.nextInt(source.length());
		return source.charAt(index);
	}
	/**
	 * @param source The source string from which to extract characters.
	 * @param length The desired length.
	 * @return A randomly generated string.
	 */
	public String getString(String source, int length) {
		StringBuilder b = new StringBuilder(length);
		for (int i = 0; i < length; i++) b.append(getChar(source));
		return b.toString();
	}
	/**
	 * @param length The length of the token.
	 * @return The token mixing vowels and consonants.
	 */
	public String getToken(int length) {
		if (length <= 0) throw new IllegalArgumentException();
		StringBuilder b = new StringBuilder();
		if (length == 1) {
			b.append(getChar(Strings.VOWELS));
		} else if (length == 2) {
			boolean vowel = getBoolean();
			b.append(getChar(vowel ? Strings.VOWELS : Strings.CONSONANTS));
			b.append(getChar(!vowel ? Strings.VOWELS : Strings.CONSONANTS));
		} else {
			boolean vowel = getBoolean();
			for (int i = 0; i < length; i++) {
				b.append(getChar(vowel ? Strings.VOWELS : Strings.CONSONANTS));
				vowel = !vowel;
			}
		}
		return b.toString();
	}
}
