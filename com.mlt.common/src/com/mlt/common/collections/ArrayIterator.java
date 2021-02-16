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

import java.util.Iterator;

/**
 * Iterator of an immutable array.
 *
 * @author Miquel Sas
 */
public class ArrayIterator<T> implements Iterator<T>, Iterable<T> {

	private T[] array;
	private int startIndex;
	private int endIndex;

	/**
	 * @param array Source array.
	 */
	public ArrayIterator(T[] array) {
		this(array, 0, array.length - 1);
	}
	/**
	 * @param array      Source array.
	 * @param startIndex Start index.
	 * @param endIndex   End index.
	 */
	public ArrayIterator(T[] array, int startIndex, int endIndex) {
		if (array == null) {
			throw new NullPointerException();
		}
		if (startIndex < 0 || startIndex >= array.length) {
			throw new IllegalArgumentException("Invalid start index: " + startIndex);
		}
		if (endIndex < startIndex) {
			throw new IllegalArgumentException("Invalid end index: " + endIndex);
		}
		if (endIndex >= array.length) {
			throw new IllegalArgumentException("Invalid end index: " + endIndex);
		}
		this.array = array;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	/**
	 * @return A boolean.
	 */
	@Override
	public boolean hasNext() {
		return startIndex <= endIndex;
	}
	/**
	 * @return The next element.
	 */
	@Override
	public T next() {
		return array[startIndex++];
	}
	/**
	 * @return The iterator for the type.
	 */
	@Override
	public Iterator<T> iterator() {
		return this;
	}
}
