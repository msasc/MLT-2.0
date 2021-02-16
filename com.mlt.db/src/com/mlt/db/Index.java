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

/**
 * An index definition.
 * @author Miquel Sas
 */
public class Index extends Order {

	/**
	 * Name of the index.
	 */
	private String name;
	/**
	 * Unique flag.
	 */
	private boolean unique;

	/**
	 * Default constructor.
	 */
	public Index() {}
	/**
	 * Constructor assigning the name.
	 * @param name   The index name.
	 * @param unique Unique indicator.
	 */
	public Index(String name, boolean unique) {
		this.name = name;
		this.unique = unique;
	}
	/**
	 * Returns the name.
	 * @return The name of the index.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the name.
	 * @param name The name of the index.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns a boolean indicating whether this index is unique.
	 * @return A boolean indicating whether this index is unique.
	 */
	public boolean isUnique() {
		return unique;
	}
	/**
	 * Set that this index should be unique.
	 * @param unique A boolean that indicates that this index should be unique.
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
}
