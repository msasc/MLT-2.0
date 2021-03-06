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

package com.mlt.common.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A basic list implementation with add, get, remove and set methods, and iterable functionality.
 * @author Miquel Sas
 */
public class BasicList<E> implements Iterable<E> {

	/**
	 * Internal list of elements.
	 */
	private List<E> items = new ArrayList<>();

	public void add(E item) {
		items.add(item);
	}
	public E get(int index) {
		return items.get(index);
	}
	public E remove(int index) {
		return items.remove(index);
	}
	public void set(int index, E item) {
		items.set(index, item);
	}

	public int size() {
		return items.size();
	}

	@Override
	public Iterator<E> iterator() {
		return items.iterator();
	}
}
