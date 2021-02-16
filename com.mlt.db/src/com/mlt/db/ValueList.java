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

package com.mlt.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list of values of the same type.
 * @author Miquel Sas
 */
public class ValueList implements Iterable<Value> {

	/**
	 * The field that defines the type of this value list.
	 */
	private Field field;
	/**
	 * The collection of values.
	 */
	private List<Value> values;

	/**
	 * Constructor assigning the required field to know the content type of the list.
	 * @param field The field that defines the type of the list.
	 */
	public ValueList(Field field) {
		if (field == null) throw new NullPointerException();
		this.field = field;
		this.values = new ArrayList<>();
	}

	/**
	 * Add a value to the list. Validates that the value type acceptable for the field definition.
	 * @param value The value to add.
	 */
	public void add(Value value) {
		validate(value);
		values.add(value);
	}
	/**
	 * Insert a value into the list. Validates that the value type acceptable for the field
	 * definition.
	 * @param index The index to insert.
	 * @param value The value to insert.
	 */
	public void add(int index, Value value) {
		validate(value);
		values.add(index, value);
	}
	/**
	 * Returns the value at the given index.
	 * @param index The index.
	 * @return The value at the given index.
	 */
	public Value get(int index) {
		return values.get(index);
	}
	/**
	 * Removes the value at the given index.
	 * @param index The index.
	 * @return The removed value.
	 */
	public Value remove(int index) {
		return values.remove(index);
	}
	/**
	 * Returns the size or number of valus in the list.
	 * @return The size or number of valus in the list.
	 */
	public int size() {
		return values.size();
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 * @return an Iterator.
	 */
	@Override
	public Iterator<Value> iterator() {
		return values.iterator();
	}

	private void validate(Value value) {
		if (value == null) {
			throw new NullPointerException("Value can not be null");
		}
		if (value.getType() != field.getType()) {
			throw new IllegalArgumentException("Invalid value type " + value.getType());
		}
	}
}
