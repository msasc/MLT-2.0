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

import com.mlt.db.json.JSONDocument;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Metadata definition of a field.
 * @author Miquel Sas
 */
public class Field implements Comparable<Object> {

	/**
	 * The name or key used to access the field within a document or row.
	 */
	private String name;
	/**
	 * The alias by which the field is accessed across documents and schemas.
	 */
	private String alias;
	/**
	 * The type.
	 */
	private Type type;
	/**
	 * The length if applicable or null.
	 */
	private Integer length;
	/**
	 * The number of decimal places if applicable or null.
	 */
	private Integer decimals;

	/**
	 * Initial value.
	 */
	private Value initialValue;
	/**
	 * Maximum value.
	 */
	private Value maximumValue;
	/**
	 * Minimum value.
	 */
	private Value minimumValue;
	/**
	 * Optional list of possible values.
	 */
	private List<Value> possibleValues;

	/**
	 * The document schema when this field type is DOCUMENT.
	 */
	private Schema documentSchema;
	/**
	 * The field that defines the element types when this field is of type LIST.
	 */
	private Field listField;

	/**
	 * A boolean that indicates if the field is required.
	 */
	private boolean required;

	/**
	 * A description of the field.
	 */
	private String description;

	/**
	 * Constructor assingning only the name and the type. Applicable to all types, those that accept
	 * a length are treated as no length limit, and for a decimal the scale is set to zero.
	 * @param name The name.
	 * @param type The type.
	 */
	public Field(String name, Type type) {
		this(name, type, null, null);
	}
	/**
	 * Constructor assingning the name, the type and the length. Applicable to DECIMAL, STRING and
	 * BINARY types.
	 * @param name   The name.
	 * @param type   The type.
	 * @param length The length.
	 */
	public Field(String name, Type type, Integer length) {
		this(name, type, length, null);
	}
	/**
	 * Constructor assingning the name, the type, length and decimals. Applicable to DECIMAL, STRING
	 * and
	 * BINARY types.
	 * @param name     The name.
	 * @param type     The type.
	 * @param length   The length.
	 * @param decimals The number of decimal places.
	 */
	public Field(String name, Type type, Integer length, Integer decimals) {

		/*
		 * Name and type can not be null.
		 */
		if (name == null) {
			throw new NullPointerException("Name can not be null");
		}
		if (type == null) {
			throw new NullPointerException("Type can not be null");
		}
		if (length != null && length <= 0) {
			throw new IllegalArgumentException("Invalid length " + length + " do not accept length");
		}

		/*
		 * Only types DECIMAL, STRING and BINARY accept lengh.
		 */
		if (type != Type.DECIMAL && type != Type.STRING && type != Type.BINARY && length != null) {
			throw new IllegalArgumentException("Type " + type + " do not accept length");
		}

		/*
		 * Only type DECIMAL accepts decimals.
		 */
		if (type != Type.DECIMAL && decimals != null) {
			throw new IllegalArgumentException("Type " + type + " do not accept decimals");
		}

		/*
		 * If type is DECIMAL and decimals is null, set decimals to zero.
		 */
		if (type == Type.DECIMAL && decimals == null) {
			decimals = 0;
		}

		/*
		 * Assign.
		 */
		this.name = name;
		this.type = type;
		this.length = length;
		this.decimals = decimals;
	}
	/**
	 * Constructor of a DOCUMENT field, the schame is required.
	 * @param name           The name.
	 * @param documentSchema The necessary schema.
	 */
	public Field(String name, Schema documentSchema) {
		/* Name and schema can not be null. */
		if (name == null) throw new NullPointerException("Name can not be null");
		if (documentSchema == null) throw new NullPointerException("Schema can not be null");
		/* Assign name, type and schema. */
		this.name = name;
		this.type = Type.DOCUMENT;
		this.documentSchema = documentSchema;
	}
	/**
	 * Constructor of a LIST field.
	 * @param name      The name of this field of type LIST.
	 * @param listField The field that defines the type of the list. Can be recurrent, that is, also
	 *                  a list field.
	 */
	public Field(String name, Field listField) {
		if (name == null) throw new NullPointerException("Name can not be null");
		if (listField == null) throw new NullPointerException("List field can not be null");
		this.name = name;
		this.type = Type.LIST;
	}

	/**
	 * Returns the name.
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the type.
	 * @return The type.
	 */
	public Type getType() {
		return type;
	}
	/**
	 * Returns the length or null if not applicable.
	 * @return The length if applicable.
	 */
	public Integer getLength() {
		return length;
	}
	/**
	 * Return the length number of decimal places if applicable.
	 * @return The length number of decimal places if applicable.
	 */
	public Integer getDecimals() {
		return decimals;
	}

	/**
	 * Returns a suitable ddefault value.
	 * @return A suitable ddefault value.
	 */
	public Value getDefaultValue() {
		switch (type) {
		case BOOLEAN:
			return new Value(false);
		case DECIMAL:
			return new Value(BigDecimal.valueOf(0).setScale(decimals, RoundingMode.HALF_UP));
		case DOUBLE:
			return new Value(Double.valueOf(0));
		case INTEGER:
			return new Value(Integer.valueOf(0));
		case LONG:
			return new Value(Long.valueOf(0));
		case DATE:
			return new Value(LocalDate.now());
		case TIME:
			return new Value(LocalTime.now());
		case TIMESTAMP:
			return new Value(LocalDateTime.now());
		case STRING:
			return new Value("");
		case BINARY:
			return new Value((byte[]) null);
		case DOCUMENT:
			return new Value(new Document(documentSchema));
		case LIST:
			return new Value(new ValueList(listField));
		}
		throw new IllegalStateException();
	}

	/**
	 * A boolean indicating whether this field is of BOOLEAN type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isBoolean() {
		return type.isBoolean();
	}

	/**
	 * A boolean indicating whether this field is of DECIMAL type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDecimal() {
		return type.isDecimal();
	}
	/**
	 * A boolean indicating whether this field is of DOUBLE type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDouble() {
		return type.isDouble();
	}
	/**
	 * A boolean indicating whether this field is of INTEGER type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isInteger() {
		return type.isInteger();
	}
	/**
	 * A boolean indicating whether this field is of LONG type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isLong() {
		return type.isLong();
	}
	/**
	 * A boolean indicating whether this field is a number.
	 * @return A boolean to confirm the type.
	 */
	public boolean isNumber() {
		return type.isNumber();
	}

	/**
	 * A boolean indicating whether this field is of DATE type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDate() {
		return type.isDate();
	}
	/**
	 * A boolean indicating whether this field is of TIME type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isTime() {
		return type.isTime();
	}
	/**
	 * A boolean indicating whether this field is of TIMESTAMP type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isTimestamp() {
		return type.isTimestamp();
	}

	/**
	 * A boolean indicating whether this field is of STRING type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isString() {
		return type.isString();
	}

	/**
	 * A boolean indicating whether this field is of BINARY type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isBinary() {
		return type.isBinary();
	}

	/**
	 * A boolean indicating whether this field is of DOCUMENT type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDocument() {
		return type.isDocument();
	}
	/**
	 * A boolean indicating whether this field is of LIST type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isList() {
		return type.isList();
	}

	/**
	 * Returns the alias used to access the field.
	 * @return The alias.
	 */
	public String getAlias() {
		return (alias == null ? getName() : alias);
	}
	/**
	 * Set the alias to access the field.
	 * @param alias The alias.
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * Returns a description of the field.
	 * @return A description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set the description.
	 * @param description A description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Check whether the field is required,
	 * @return A boolean indicating whether the field is required.
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * Set whether the field is required.
	 * @param required A boolean indicating whether the field is required.
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Return the initial value or null.
	 * @return The initial value.
	 */
	public Value getInitialValue() {
		return initialValue;
	}
	/**
	 * Set the initial value.
	 * @param initialValue The initial value.
	 */
	public void setInitialValue(Value initialValue) {
		this.initialValue = initialValue;
	}
	/**
	 * Return the maximum value or null.
	 * @return The maximum value.
	 */
	public Value getMaximumValue() {
		return maximumValue;
	}
	/**
	 * Set the maximum value.
	 * @param maximumValue The maximum value.
	 */
	public void setMaximumValue(Value maximumValue) {
		this.maximumValue = maximumValue;
	}
	/**
	 * Return the minimum value or null.
	 * @return The minimum value.
	 */
	public Value getMinimumValue() {
		return minimumValue;
	}
	/**
	 * Set the minimum value.
	 * @param minimumValue The minimum value.
	 */
	public void setMinimumValue(Value minimumValue) {
		this.minimumValue = minimumValue;
	}

	/**
	 * Add a possible value.
	 * @param value The value to add to the list of possible values.
	 */
	public void addPossibleValue(Value value) {
		validateType(value);
		validateMaximumValue(value);
		validateMinimumValue(value);
		if (possibleValues == null) possibleValues = new ArrayList<>();
		if (!possibleValues.contains(value)) possibleValues.add(value);
	}
	/**
	 * Return the list of possible values, empty if none is defined.
	 * @return The list of possible values, empty if none is defined.
	 */
	public List<Value> getPossibleValues() {
		if (possibleValues == null) return Collections.emptyList();
		return Collections.unmodifiableList(possibleValues);
	}

	/**
	 * Validates the value type, minum, maximum and possible values.
	 * @param value The value to validate.
	 */
	public void validate(Value value) {
		validateType(value);
		validateMinimumValue(value);
		validateMaximumValue(value);
		validatePossibleValues(value);
	}
	/**
	 * Validate the value type.
	 * @param value The value to validate the type.
	 */
	public void validateType(Value value) {
		if (type != value.getType()) {
			throw new IllegalArgumentException("Types do not match " + getType() + "/" + value.getType());
		}
	}
	/**
	 * Validates that the value is LE the maximum accepted value.
	 * @param value The value to validate for maximum.
	 */
	public void validateMaximumValue(Value value) {
		if (maximumValue != null) {
			if (value.compareTo(maximumValue) > 0) {
				throw new IllegalArgumentException("Value GT maximum value " + value + "/" + maximumValue);
			}
		}
	}
	/**
	 * Validates that the value is GE the minimum accepted value.
	 * @param value The value to validate for minimum.
	 */
	public void validateMinimumValue(Value value) {
		if (minimumValue != null) {
			if (value.compareTo(minimumValue) < 0) {
				throw new IllegalArgumentException("Value LT minimum value " + value + "/" + minimumValue);
			}
		}
	}
	/**
	 * Validates that the value is in the list of possible values if any.
	 * @param value The value to validate.
	 */
	public void validatePossibleValues(Value value) {
		if (possibleValues != null) {
			if (!possibleValues.contains(value)) {
				throw new IllegalArgumentException("Value " + value + " must be one of possible values");
			}
		}
	}

	/**
	 * Compares this object with the specified object for order. Returns a negative integer, zero,
	 * or a positive integer as this object is less than, equal to, or greater than the specified
	 * object.
	 * @param obj the object to be compared.
	 * @return negative integer, zero, or a positive integer as this object is less than, equal to,
	 * or greater than the specified object.
	 */
	@Override
	public int compareTo(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		if (!(obj instanceof Field)) {
			throw new UnsupportedOperationException("Not comparable type: " + obj.getClass());
		}
		Field field = (Field) obj;
		int compare = getName().compareTo(field.getName());
		if (compare != 0) return compare;
		compare = getAlias().compareTo(field.getAlias());
		return compare;
	}
	/**
	 * Returns a hash code.
	 * @return a hash code.
	 */
	@Override
	public int hashCode() {
		StringBuilder b = new StringBuilder();
		b.append(getName());
		b.append(getAlias());
		return b.toString().hashCode();
	}
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare.
	 * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Field f = (Field) obj;
		if (!getName().equals(f.getName())) return false;
		if (!getAlias().equals(f.getAlias())) return false;
		if (isNumber()) {
			if (!f.isNumber()) return false;
		} else {
			if (getType() != f.getType()) return false;
		}
		if (documentSchema != null) {
			if (!documentSchema.equals(f.documentSchema)) return false;
		}
		if (listField != null) {
			if (!listField.equals(f.listField)) return false;
		}
		return true;
	}
	/**
	 * Returns a string representation of the field.
	 * @return a string representation of the field.
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(getName());
		if (!getAlias().equals(getName())) {
			b.append(", ");
			b.append(getAlias());
		}
		b.append(", ");
		b.append(getType());
		return b.toString();
	}
	/**
	 * Returns a JSON representation of this field.
	 * @return A JSON representation of this field.
	 */
	public JSONDocument toJSONDocument() {
		JSONDocument doc = new JSONDocument();
		doc.setString("name", getName());
		if (!getAlias().equals(getName())) {
			doc.setString("alias", getAlias());
		}
		doc.setString("type", getType().toString());
		if (getLength() != null) {
			doc.setInteger("length", getLength());
		}
		if (getDecimals() != null) {
			doc.setInteger("decimals", getDecimals());
		}
		if (isRequired()) {
			doc.setBoolean("required", isRequired());
		}
		if (getDescription() != null) {
			doc.setString("description", getDescription());
		}
		return doc;
	}
}
