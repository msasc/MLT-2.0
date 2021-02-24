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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestBigDecimal {
	public static void main(String[] args) {
		BigDecimal b;
		b = new BigDecimal("2.0234856");
		System.out.println(b.toPlainString() + ", dec=" + b.scale());
		b = b.divide(new BigDecimal("10000000"));
		System.out.println(b.toPlainString() + ", dec=" + b.scale());
//		BigDecimal c = b.divide(new BigDecimal("10453.3575308"), 100, RoundingMode.HALF_UP);
//		System.out.println(c);
//		BigDecimal d = c.multiply(new BigDecimal("10000000000000000000000000000000000000000000000000"));
//		System.out.println(d);
//		System.out.println(BigDecimal.valueOf(2.345).scale());
	}
}
