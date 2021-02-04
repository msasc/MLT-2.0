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

package test;

import com.mlt.common.lang.Strings;

public class TestOut {
	public static void main(String[] args) {
		String s = null;
		for (int i = 0; i < 500; i++) {
			if (s != null) System.out.print(Strings.repeat("\b", s.length()));
			s = "Hola " + Integer.toString(i);
			System.out.print(s);
			try { Thread.sleep(5); } catch (Exception ignore) {}
		}
	}
}
