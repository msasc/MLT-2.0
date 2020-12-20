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

import com.mlt.launch.Argument;
import com.mlt.launch.ArgumentManager;

import java.io.File;

public class TestResources {
	public static void main(String[] args) {
		ArgumentManager argMngr = new ArgumentManager();
		Argument arg = new Argument("res", "Resources", true, true, false);
		argMngr.add(arg);
		if (!argMngr.parse(args)) {
			System.exit(1);
		}
		System.out.println(argMngr.getValue("res"));
		File path = new File(argMngr.getValue("res"));
		System.out.println(path.exists());
	}
}
