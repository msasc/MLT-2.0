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

package test;


import com.mlt.common.collections.Properties;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestProperties {
	public static void main(String[] args) {
		Properties properties = new Properties();

		Boolean boolean_value = false;
		properties.putBoolean("boolean", boolean_value);

		BigDecimal bigDecimal_value = BigDecimal.valueOf(325.096).setScale(6, RoundingMode.HALF_UP);
		properties.putBigDecimal("bigDecimal", bigDecimal_value);

		properties.putDouble("double", (double) 3.25);
		properties.putInteger("integer", 325);
		properties.putLong("long", (long) 325);

		double[][] matrix_value = new double[][]{
			{ 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 }
		};
		properties.putDoubleMatrix("matrix", matrix_value);

		properties.putLocalDate("date", LocalDate.now());
		properties.putLocalDateTime("date-time", LocalDateTime.now());
		properties.putLocalTime("time", LocalTime.now());

		System.out.println();
		System.out.println(properties.toJSON().toString());

		Properties restore = new Properties();
		restore.fromJSON(properties.toJSON());

		System.out.println();
		System.out.println(restore.toJSON().toString());

		System.out.println();
		System.out.println(restore.getBigDecimal("bigDecimal"));

		System.out.println();
		System.out.println(restore.getLocalDate("date"));
		System.out.println(restore.getLocalDateTime("date-time"));
	}
}
