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

import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJSONDate {
	public static void main(String[] args) {
		JSONObject o = new JSONObject();
		o.put("date", LocalDate.now());
		o.put("date-time", LocalDateTime.now());
		o.put("is-good", true);
		o.put("decimal", BigDecimal.valueOf(0.345));
		o.put("integer", Long.valueOf(1000000000000000L));
		Map<String, Double> map = new HashMap<>();
		map.put("one", 1.0);
		map.put("two", 2.0);
		o.put("map", map);
		List<String> list = new ArrayList<>();
		list.add("item_01");
		list.add("item_02");
		o.put("list", list);
		byte[] bytes = new byte[]{ (byte) 0, (byte) 1 };
		o.put("bytes", bytes);
		o.put("string", "This is your name: \"NAME\"");
		System.out.println(o);
		JSONObject c = new JSONObject(o.toString());
		System.out.println(c.get("date").getClass());
		System.out.println(c.get("date-time").getClass());
		System.out.println(c.get("is-good").getClass());
		System.out.println(c.get("decimal").getClass());
		System.out.println(c.get("integer").getClass());
		System.out.println(c.get("map").getClass());
		System.out.println(c.get("list").getClass());
		System.out.println(c.get("bytes").getClass());
		System.out.println(c.get("string"));
	}
}
