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

package com.mlt.db.criteria;

import com.mlt.db.Field;
import com.mlt.db.Type;
import com.mlt.db.Value;

import java.util.List;

/**
 * A condition in a criteria.
 *
 * @author Miquel Sas
 */
public class Condition {

	/** Field. */
	private Field field;
	/** Comparison operator. */
	private Comparison cmp;
	/** List of values. */
	private Value[] values;
	/** An optional string formula, database dependent. */
	private String formula;

	/**
	 * @param formula String formula.
	 */
	public Condition(String formula) {
		assignAndValidate(null, null, null, formula);
	}
	/**
	 * @param field  Field.
	 * @param cmpStr Comparison operator string id.
	 */
	public Condition(Field field, String cmpStr) {
		assignAndValidate(field, cmpStr, null, null);
	}
	/**
	 * @param field   Field.
	 * @param cmpStr  Comparison operator string id.
	 * @param formula String formula.
	 */
	public Condition(Field field, String cmpStr, String formula) {
		assignAndValidate(field, cmpStr, null, formula);
	}
	/**
	 * @param field  Field.
	 * @param cmpStr Comparison operator string id.
	 * @param values List of values.
	 */
	public Condition(Field field, String cmpStr, Value... values) {
		assignAndValidate(field, cmpStr, values, null);
	}
	/**
	 * @param field  Field.
	 * @param cmpStr Comparison operator string id.
	 * @param values List of values.
	 */
	public Condition(Field field, String cmpStr, List<Value> values) {
		assignAndValidate(field, cmpStr, values.toArray(new Value[values.size()]), null);
	}

	/** @return The field. */
	public Field getField() { return field; }
	/** @return The comparison operator. */
	public Comparison getComparison() { return cmp; }
	/** @return The list of values to compare with. */
	public Value[] getValues() { return values; }
	/** @return The database dependent formula. */
	public String getFormula() { return formula; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (formula != null && field == null && cmp == null && values == null) {
			b.append(formula);
		} else {
			b.append(field.getName());
			b.append(" ");
			b.append(cmp.toString());
			b.append(" ");
			if (formula != null) {
				b.append(formula);
			}
			if (values != null && values.length > 0) {
				if (cmp.size() == 1) {
					b.append(toString(values[0]));
				} else {
					b.append("(");
					for (int i = 0; i < values.length; i++) {
						if (i > 0) b.append(", ");
						b.append(toString(values[i]));
					}
					b.append(")");
				}
			}
		}
		return b.toString();
	}

	/**
	 * @param field   Field.
	 * @param cmpId   Comparison string id.
	 * @param formula String formula.
	 * @param values  List of values.
	 */
	private void assignAndValidate(Field field, String cmpId, Value[] values, String formula) {
		this.field = field;
		this.cmp = (cmpId != null ? Comparison.get(cmpId) : null);
		this.values = values;
		this.formula = formula;
		validate();
	}
	/**
	 * Do validate already assigned members.
	 */
	private void validate() {

		/* Only a string formula, nothing to validate. */
		if (formula != null && field == null && cmp == null && values == null) return;

		/* Any other configuration requires the field and the operator. */
		if (field == null) throw new NullPointerException("Field can not be null");
		if (cmp == null) throw new NullPointerException("Operator can not be null");

		/* A string formula, nothing more to validate. */
		if (formula != null) return;

		/* If values are null, the operator must require zero values. */
		if (values == null && cmp.size() == 0) return;

		/* Values are required. */
		if (values == null) throw new IllegalArgumentException("Values are required");

		/* If a fixed number of values is required check it. */
		if (cmp.size() >= 0 && values.length != cmp.size()) {
			throw new IllegalArgumentException("Invalid number of values");
		}

		/* All values must be of the same type. */
		Type type = null;
		for (Value value : values) {
			if (type == null) type = value.getType();
			if (type != value.getType()) {
				throw new IllegalArgumentException("All values must have the same type");
			}
		}
		/* The type of the values and field must match. */
		if (field.getType().isNumber() && type.isNumber()) return;
		if (field.getType() != type) throw new IllegalArgumentException("Not compatible types");
	}

	/**
	 * @param value A value.
	 * @return A suitable string representation within a criteria.
	 */
	private static String toString(Value value) {
		StringBuilder b = new StringBuilder();
		if (value.isNull()) {
			b.append(value.toString());
		} else if (value.isBoolean()) {
			b.append(value.getBoolean() ? "'Y'" : "'N");
		} else {
			b.append("'");
			b.append(value.toString());
			b.append("'");
		}
		return b.toString();
	}
}
