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

package com.mlt.db;

/**
 * Types, strictly raw, supported by the database module.
 *
 * @author Miquel Sas
 */
public enum Types {
	BOOLEAN,
	STRING,
	DECIMAL,
	DOUBLE,
	INTEGER,
	LONG,
	DATE,
	TIME,
	DATETIME,
	BYTEARRAY;

	public boolean isBoolean() { return equals(BOOLEAN); }

	public boolean isString() { return equals(STRING); }
	public boolean isDecimal() { return equals(DECIMAL); }
	public boolean isDouble() { return equals(DOUBLE); }
	public boolean isInteger() { return equals(INTEGER); }
	public boolean isLong() { return equals(LONG); }
	public boolean isNumber() { return isDecimal() || isDouble() || isInteger() || isLong(); }
	public boolean isFloatingPoint() { return isDouble(); }

	public boolean isDate() { return equals(DATE); }
	public boolean isTime() { return equals(TIME); }
	public boolean isDateTime() { return equals(DATETIME); }

	public boolean isByteArray() { return equals(BYTEARRAY); }
}
