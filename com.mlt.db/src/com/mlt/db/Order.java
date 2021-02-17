/*
 * Copyright (C) 2018 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.mlt.db;

import com.mlt.common.lang.Strings;
import com.mlt.db.json.JSONDocument;
import com.mlt.db.json.JSONList;

import java.util.ArrayList;

/**
 * An order definition.
 * @author Miquel Sas
 */
public class Order extends ArrayList<FieldKey> {

	/**
	 * Default constructor.
	 */
	public Order() {}

	/**
	 * Add an ascending field to the list of field keys.
	 * @param field The field to add.
	 */
	public void add(Field field) {
		add(new FieldKey(field, true));
	}
	/**
	 * Add a field to the list of field keys.
	 * @param field The field to add.
	 * @param asc   The ascending indicator.
	 */
	public void add(Field field, boolean asc) {
		add(new FieldKey(field, asc));
	}
	/**
	 * Returns a JSON representation of this order.
	 * @return A JSON representation of this order.
	 */
	public JSONDocument toJSONDocument() {
		JSONList keys = new JSONList();
		for (FieldKey fieldKey : this) {
			JSONList key = new JSONList();
			key.addString(fieldKey.getField().getName());
			key.addString(Strings.toBooleanString(fieldKey.isAsc(), "asc", "desc"));
			keys.addList(key);
		}
		JSONDocument doc = new JSONDocument();
		doc.setList("keys", keys);
		return doc;
	}
}
