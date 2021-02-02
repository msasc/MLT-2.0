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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class TestTaskExecutor {

	static class Print extends Task {
		String id;
		int loops;
		Print(String id, int loops) {
			this.id = id;
			this.loops = loops;
		}
		public void execute() throws Throwable {
			for (int i = 1; i <= loops; i++) {
//				Thread.sleep(5);
				if (shouldCancel()) {
					System.out.println(id + ": CANCELLED");
					break;
				}
//				if (i % 10000 == 0) {
//					System.out.println(id + ": " + Strings.leftPad(i, 6, "0"));
//				}
			}
			System.out.println(id + ": TERMINATED");
		}
	}

	public static void main(String[] args) {
		int parallelism = 20;
		int loops = 40000;
		int start = 1;
		int end = 10000;
		LocalDateTime startTimeTE, endTimeTE, startTimeFJ, endTimeFJ;

		TaskExecutor executor = new TaskExecutor(parallelism);
		startTimeTE = LocalDateTime.now();
		executor.submitAndWaitForTermination(getTasks("TE", start, end, loops));
		executor.shutdown();
		endTimeTE = LocalDateTime.now();

		ForkJoinPool pool = new ForkJoinPool(parallelism);
		startTimeFJ = LocalDateTime.now();
		pool.invokeAll(getTasks("FJ", start, end, loops));
		endTimeFJ = LocalDateTime.now();
		pool.shutdown();

		System.out.println("-----------------------------");
		System.out.println(startTimeTE);
		System.out.println(endTimeTE);
		System.out.println(startTimeFJ);
		System.out.println(endTimeFJ);
		System.out.println("-----------------------------");
		long timeTE = startTimeTE.until(endTimeTE, ChronoUnit.MILLIS);
		long timeFJ = startTimeFJ.until(endTimeFJ, ChronoUnit.MILLIS);
		System.out.println(timeTE);
		System.out.println(timeFJ);
		System.out.println(timeFJ-timeTE);


	}

	private static List<Task> getTasks(String prefix, int start, int end, int loops) {
		List<Task> tasks = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			String id = prefix + Strings.leftPad(i, 6, "0");
			tasks.add(new Print(id, loops));
		}
		return tasks;
	}
}
