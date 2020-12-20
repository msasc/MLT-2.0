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

import com.mlt.lang.Strings;
import com.mlt.task.Task;
import com.mlt.task.TaskExecutor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestTaskExecutor {

	static class Print extends Task {
		String id;
		Print(String id) { this.id = id; }
		public void execute() throws Throwable {
			for (int i = 0; i < 100; i++) {
				Thread.sleep(2);
				System.out.println(id + ": " + Strings.leftPad(i, 4, "0"));
			}
		}
	}

	public static void main(String[] args) {
		List<Task> tasks = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			tasks.add(new Print("P" + Strings.leftPad(i, 4, "0")));
		}
		TaskExecutor executor = new TaskExecutor(50);
		LocalDateTime start = LocalDateTime.now();
		executor.submit(tasks);
		executor.waitForTermination(tasks);
		System.out.println(start);
		System.out.println(LocalDateTime.now());
		System.out.println("Shuting down...");
		executor.shutdown();
	}
}
