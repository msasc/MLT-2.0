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

import com.mlt.db.json.JSONDocument;

public class TestJSON {
	public static void main(String[] args) {
		JSONDocument c = new JSONDocument();
		c.setString("name", "Bitch");
		c.setDouble("bias", 12.98);
		c.setBoolean("is_valid", true);

		System.out.println(c.toString());
	}
}
