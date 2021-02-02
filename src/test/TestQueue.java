/*
 * Copyright (c) 2020. Miquel Sas
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

import com.mlt.common.collections.Queue;

import java.util.Iterator;
import java.util.Properties;

public class TestQueue {
	public static void main(String[] args) {
		Queue<String> q = new Queue<>();
//		String[] arrFirst = q.toArrayFirst(3);
//		for (String i : arrFirst) System.out.println(i);
//		String[] arrLast = q.toArrayLast(3);
//		for (String i : arrLast) System.out.println(i);
//		arrLast = q.toArrayLast(8);
//		for (String i : arrLast) System.out.println(i);

		fill(q);
		Iterator<String> asc = q.ascendingIterator();
		while (asc.hasNext()) {
			System.out.println(asc.next());
		}
		System.out.println();
		Iterator<String> desc = q.descendingIterator();
		while (desc.hasNext()) {
			System.out.println(desc.next());
		}
		System.out.println();
		for (String s : q) {
			System.out.println(s);
		}
		System.out.println();
		String[] a = q.toArrayFromTail(new String[4]);
		for (String s : a) {
			System.out.println(s);
		}

		System.out.println();
		Iterator<String> iter = q.ascendingIterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
			iter.remove();
		}

		System.out.println();
		System.out.println(q.size());

		q.add("Hello");
		q.add("my friend");
		q.add("is it me you're looking for");
		String to_string = q.toString();
		System.out.println(to_string);

		q.clear();
		System.out.println(q.size());

		fill(q);
		System.out.println(q.size());

		Queue<String> q2 = new Queue<>();
		q2.add("D");
		q2.add("G");
		q2.add("A");
		q.removeAll(q2);
		System.out.println(q);
		q.remove("B");
		System.out.println(q);

		System.out.println(System.getProperty("java.class.path"));
		Properties p = System.getProperties();
		for (String k : p.stringPropertyNames()) {
			System.out.println(k);
		}

		System.out.println();
		for (String arg : args) {
			System.out.println(arg);
		}

	}

	private static void fill(Queue<String> q) {
		q.addFirst("A");
		q.addFirst("B");
		q.addFirst("C");
		q.addFirst("D");
		q.addLast("E");
		q.addLast("F");
		q.addLast("G");
		q.addLast("H");
	}
}
