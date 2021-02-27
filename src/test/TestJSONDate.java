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

package test;

import com.mlt.common.json.JSONDoc;
import com.mlt.common.json.JSONParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

public class TestJSONDate {
	public static void main(String[] args) {
		JSONDoc o = new JSONDoc();
		o.setDate("date", LocalDate.now());
		o.setTime("time", LocalTime.of(12, 30));
		o.setTimestamp("date-time", LocalDateTime.now());
		o.setBoolean("is-good", true);
		o.setDecimal("decimal", BigDecimal.valueOf(0.345));
		o.setLong("integer", Long.valueOf(1000000000000000L));
		System.out.println(o);
		JSONDoc c = new JSONDoc();
		JSONParser parser = new JSONParser();
		System.out.println(c.getDate("date").getClass());
		System.out.println(c.getTime("time").getClass());
		System.out.println(c.getTimestamp("date-time").getClass());
		System.out.println(c.getBoolean("is-good").getClass());
		System.out.println(c.getDecimal("decimal").getClass());
		System.out.println(c.getInteger("integer").getClass());
		System.out.println(LocalDateTime.parse("2021-02-07 09:28".replace(" ", "T")));
		System.out.println(YearMonth.now());
	}
}
