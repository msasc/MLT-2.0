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

import java.util.ArrayList;
import java.util.List;

public class TestTaskExecutorMult {

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
//				Thread.sleep(5);
				if (shouldCancel()) {
					System.out.println(id + ": CANCELLED");
					break;
				}
				if (exception && i > loops / 2) {
					throw new Exception(id + ": EXCEPTION");
				}
//				if (i % 10000 == 0) {
//					System.out.println(id + ": " + Strings.leftPad(i, 6, "0"));
//				}
			}
			System.out.println(id + ": TERMINATED");
		}
	}

	static class Exec extends Thread {
		int exec;
		TaskExecutor executor;
		List<Task> tasks;
		boolean terminated = false;
		public void run() {
			executor.submitAndWaitForTermination(tasks);
			terminated = true;
		}
	}

	public static void main(String[] args) {
		int parallelism = 10;
		int loops = 10000;
		int start = 1;
		int end = 10000;
		int num_execs = 3;
		TaskExecutor executor = new TaskExecutor(parallelism);

		List<Exec> execs = new ArrayList<>();
		for (int i = 0; i < num_execs; i++) {
			Exec exec = new Exec();
			exec.executor = executor;
			exec.exec = i;
			exec.tasks = getTasks(start, end, loops);
			execs.add(exec);
		}
		for (Exec exec : execs) {
			exec.start();
			try { Thread.sleep(200); } catch (InterruptedException ignore) {}
		}
		while (true) {
			try { Thread.sleep(5); } catch (InterruptedException ignore) {}
			boolean allDone = true;
			for (Exec exec : execs) {
				if (!exec.terminated) {
					allDone = false;
					break;
				}
			}
			if (allDone) break;
		}
		executor.shutdown();
		for (Exec exec : execs) {

		}
	}


	private static List<Task> getTasks(int start, int end, int loops) {
		List<Task> tasks = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			String id = "P" + Strings.leftPad(i, 5, "0");
			Print p = new Print(id, loops);
			if (i % 500 == 0) p.exception = true;
			tasks.add(p);
		}
		return tasks;
	}
}
