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
 * A Document handles a row in a relational or column database such as PostgreSQL or Hadoop, and a
 * document in a document database such as MongoDB.
 *
 * @author Miquel Sas
 */
public interface Document {
	/**
	 * @param name The name or key of the field.
	 * @return The field with the given name or key.
	 */
	Field getField(String name);
	/**
	 * @param name The name or key of the field.
	 * @return The value of the given field within the document.
	 */
	Value getValue(String name);
}
