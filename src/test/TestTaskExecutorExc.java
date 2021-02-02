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

import com.mlt.common.lang.Strings;
import com.mlt.common.task.Task;
import com.mlt.common.task.TaskExecutor;

import java.util.ArrayList;
import java.util.List;

public class TestTaskExecutorExc {

	static class Print extends Task {
		String id;
		int loops = 10000;
		boolean exception = false;
		Print(String id, int loops) {
			this.id = id;
			this.loops = loops;
		}
		public void execute() throws Throwable {
			for (int i = 1; i <= loops; i++) {
				if (exception) {
					throw new Exception(id + ": EXCEPTION");
				}
//				Thread.sleep(1);
				if (shouldCancel()) {
					setCancelled();
					break;
				}
			}
			System.out.println(id + ": TERMINATED");
		}
	}

	public static void main(String[] args) {
		int parallelism = 100;
		int loops = 25000;
		int start = 1;
		int end = 50000;

		List<Print> tasks = getTasks(start, end, loops);
		tasks.get(7000).exception = true;

		TaskExecutor executor = new TaskExecutor(parallelism);
		executor.submitAndWaitForTermination(tasks);
		executor.shutdown();

		int ready = 0;
		int running = 0;
		int succeeded = 0;
		int cancelled = 0;
		int failed = 0;
		for (Print task : tasks) {
			switch (task.getState()) {
				case READY:
					ready++;
					break;
				case RUNNING:
					running++;
					break;
				case SUCCEEDED:
					succeeded++;
					break;
				case CANCELLED:
					cancelled++;
					break;
				case FAILED:
					failed++;
					break;
			}
		}
		System.out.println("READY:     " + ready);
		System.out.println("RUNNING:   " + running);
		System.out.println("SUCCEEDED: " + succeeded);
		System.out.println("CANCELLED: " + cancelled);
		System.out.println("FAILED:    " + failed);

	}

	private static List<Print> getTasks(int start, int end, int loops) {
		List<Print> tasks = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			String id = "P" + Strings.leftPad(i, 5, "0");
			tasks.add(new Print(id, loops));
		}
		return tasks;
	}
}
