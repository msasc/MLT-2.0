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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A criteria used to filter collections of documents, either in memory or by the underlying
 * database. Accepted condition operators are:
 * <ul>
 *     <li>"EQ", "GT", "GE", "LT", "LE", "NE"</li>
 *     <li>"STARTS WITH", "CONTAINS", "ENDS WITH"</li>
 *     <li>"IN", "NOT IN"</li>
 *     <li>"IS NULL", "IS NOT NULL"</li>
 *     <li>"BETWEEN", "NOT BETWEEN"</li>
 * </ul>
 *
 * @author Miquel Sas
 */
public class Criteria {

	/**
	 * Operator identifiers.
	 */
	private enum Id {
		EQ, GT, GE, LT, LE, NE,
		STARTS_WITH, CONTAINS, ENDS_WITH,
		IN, NOT_IN,
		IS_NULL, IS_NOT_NULL,
		BETWEEN, NOT_BETWEEN
	}


	/**
	 * Operator properties.
	 */
	private static class Operator {
		/**
		 * Operator id.
		 */
		private Id id;
		/**
		 * Number of required values, -1 for a list of one or more values.
		 */
		private int requiredValues;
		/**
		 * List of accepted supertypes.
		 */
		private String[] types;
		/**
		 * @param id             Operatir identifier.
		 * @param requiredValues Number of required values.
		 * @param types          Accepted types.
		 */
		public Operator(Id id, int requiredValues, String... types) {
			this.requiredValues = requiredValues;
			this.types = types;
		}
	}
	/**
	 * Map of operators, states for each operator its properties.
	 */
	private static Map<String, Operator> mapOperators = new HashMap<>();
	/**
	 * Load accepted operators.
	 */
	static {
		mapOperators.put("EQ", new Operator(Id.EQ, 1, "BOOL", "NUM", "DATE", "STR"));
		mapOperators.put("GT", new Operator(Id.GT, 1, "NUM", "DATE", "STR"));
		mapOperators.put("GE", new Operator(Id.GE, 1, "NUM", "DATE", "STR"));
		mapOperators.put("LT", new Operator(Id.LT, 1, "NUM", "DATE", "STR"));
		mapOperators.put("LE", new Operator(Id.LE, 1, "NUM", "DATE", "STR"));
		mapOperators.put("NE", new Operator(Id.NE, 1, "BOOL", "NUM", "DATE", "STR"));

		mapOperators.put("STARTS WITH", new Operator(Id.STARTS_WITH, 1, "STR"));
		mapOperators.put("CONTAINS", new Operator(Id.CONTAINS, 1, "STR"));
		mapOperators.put("ENDS WITH", new Operator(Id.ENDS_WITH, 1, "STR"));

		mapOperators.put("IN", new Operator(Id.IN, 1, "BOOL", "NUM", "DATE", "STR"));
		mapOperators.put("NOT IN", new Operator(Id.NOT_IN, 1, "BOOL", "NUM", "DATE", "STR"));

		mapOperators.put("IS NULL", new Operator(Id.IS_NULL, 1, "DATE", "STR"));
		mapOperators.put("IS NOT NULL", new Operator(Id.IS_NOT_NULL, 1, "DATE", "STR"));

		mapOperators.put("BETWEEN", new Operator(Id.BETWEEN, 1, "NUM", "DATE", "STR"));
		mapOperators.put("NOT BETWEEN", new Operator(Id.NOT_BETWEEN, 1, "NUM", "DATE", "STR"));
	}


	/**
	 * A filtering condition.
	 */
	private static class Condition {
		/**
		 * Field.
		 */
		private Field field;
		/**
		 * Operator.
		 */
		private Operator operator;
		/**
		 * List of values.
		 */
		private Value[] values;
		/**
		 * An optional string condition, database dependent.
		 */
		private String condition;
		/**
		 * @param field    Field.
		 * @param operator Operator.
		 */
		private Condition(Field field, String operator) {
			assignAndValidate(field, operator, null, null);
		}
		/**
		 * @param field    Field.
		 * @param operator Operator.
		 * @param values   List of values.
		 */
		private Condition(Field field, String operator, Value... values) {
			assignAndValidate(field, operator, values, null);
		}
		/**
		 * @param field    Field.
		 * @param operator Operator.
		 * @param values   List of values.
		 */
		private Condition(Field field, String operator, List<Value> values) {
			assignAndValidate(field, operator, values.toArray(new Value[values.size()]), null);
		}

		/**
		 * @param field     The field.
		 * @param operator  The operator.
		 * @param values    The list of values.
		 * @param condition The special string condition.
		 */
		private void assignAndValidate(Field field, String operator, Value[] values, String condition) {
			this.field = field;
			this.operator = mapOperators.get(operator);
			this.values = values;
			this.condition = condition;
			validate();
		}
		private void validate() {

			/* Only a string condition, nothing to validate. */
			if (condition != null && field == null && operator == null && values == null) return;

			/* Any other configuration requires the field and the operator. */
			if (field == null) throw new NullPointerException("Field can not be null");
			if (operator == null) throw new NullPointerException("Operator can not be null");

			/* A string condition, nothing more to validate. */
			if (condition != null) return;

			/* If values are null, the operator must require zero values. */
			if (values == null && operator.requiredValues == 0) return;

			/* Values are required. */
			if (values == null) throw new IllegalArgumentException("Values are required");

			/* If a fixed number of values is required check it. */
			if (operator.requiredValues >= 0 && values.length != operator.requiredValues) {
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
	}
}
