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

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestValues {
	public static void main(String[] args) {
		System.out.println(Boolean.TRUE.toString());
		System.out.println(Double.valueOf(3.250).toString());
		System.out.println(new BigDecimal("3.250").toString());
		System.out.println(LocalDateTime.now().toString());
		System.out.println(String.class.getSimpleName());
		System.out.println(double[][].class.getSimpleName());
	}
}
