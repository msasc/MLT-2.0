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

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class TestJSON {
	public static void main(String[] args) {
		JSONObject o = new JSONObject();
		JSONArray a = new JSONArray();
		BigDecimal d = new BigDecimal(200.0).setScale(4, RoundingMode.HALF_UP);
		a.put(d);
		a.put(d.multiply(new BigDecimal(4)));
		o.put("values", a);
		System.out.println(o);
		JSONObject c = new JSONObject(o.toString());
		c.put("name", "Bitch");
		c.put("bias", 12.98);
		c.put("parent", o);
		JSONArray ks = new JSONArray();
		ks.put(o);
		ks.put(o);
		ks.put(o);
		ks.put(o);
		c.put("parents", ks);
		System.out.println(c);
		System.out.println(c.getJSONArray("parents").get(0));

		JSONObject t = new JSONObject();
		double[] vector = new double[]{ 1.0, 2.0, 3.0, 4.0, 5.0, };
		double[][] matrix = new double[][]{ { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		t.put("vector", vector);
		t.put("matrix", matrix);
		System.out.println(t);
		JSONObject u = new JSONObject(t.toString());
		System.out.println(u);
		System.out.println(u.getJSONArray("matrix").length());

		JSONObject x = new JSONObject();
		BigDecimal bx = new BigDecimal(2).setScale(6, RoundingMode.HALF_UP);
		x.put("value", bx);
		System.out.println(x);
		System.out.println(bx);
		JSONObject y = new JSONObject(x.toString());
		BigDecimal by = y.getBigDecimal("value");
		System.out.println(by.scale());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 500; i++) {
			sb.append("9");
		}
		sb.append(".");
		for (int i = 0; i < 100; i++) {
			sb.append("9");
		}

		BigDecimal bt = new BigDecimal(sb.toString());
		System.out.println(bt.precision());
		System.out.println(bt.scale());
		System.out.println(bt);


	}
}
