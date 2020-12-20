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

package com.mlt.task;

import com.mlt.collections.Queue;

import java.util.Collection;

/**
 * An executor service that executes each submitted task using one of several pooled threads.
 *
 * @author Miquel Sas
 */
public class TaskExecutor {
	/**
	 * Dispatcher thread.
	 */
	private class Dispatcher extends Thread {
		/**
		 * The normal state of the dispatcher is sleeping. When tasks are submitted, the dispatcher
		 * is awakened and then starts dispatching pending tasks until the pending queue is empty.
		 */
		@Override
		public void run() {
			while (true) {
				/* Check shutdown before sleeping. */
				if (shutdown) break;
				/* Effectively enter the sleeping state. */
				try { while (true) { Thread.sleep(60000); } } catch (InterruptedException go) {}
				/* Dispatch pending tasks until all done or shutdown required. */
				while (!shutdown) {
					Thread.yield();
					synchronized (pendingTasks) {
						if (pendingTasks.isEmpty()) break;
						Worker worker = null;
						synchronized (iddleWorkers) {
							if (!iddleWorkers.isEmpty()) {
								worker = iddleWorkers.removeFirst();
							}
						}
						if (worker == null) {
							synchronized (workers) {
								if (workers.size() < parallelism) {
									worker = new Worker();
									worker.setName(root + "-Worker-" + workers.size());
									worker.start();
									workers.addLast(worker);
								}
							}
						}
						if (worker != null) {
							worker.task = pendingTasks.removeFirst();
							worker.interrupt();
						}
					}
				}
			}
		}
	}

	/**
	 * Pooled worker thread.
	 */
	private class Worker extends Thread {
		/**
		 * The task that this worker should execute when freed from its sleep state.
		 */
		private Task task;
		/**
		 * The normal state of the thread is sleeping while waiting for a task to execute. The
		 * dispatcher first sets the task and then interrupts the threat to force exit the sleeping
		 * state.
		 */
		@Override
		public void run() {
			while (true) {
				/* Check exit before sleeping. */
				if (shouldTerminate()) break;
				/* Effectively enter the sleeping state. */
				try { while (true) { Thread.sleep(60000); } } catch (InterruptedException go) {}
				/* Check exit after sleeping. */
				if (shouldTerminate()) break;
				/* Run the eventual task. */
				if (task != null) {
					task.run();
					task = null;
				}
				/* Check exit after running the task. */
				if (shouldTerminate()) break;
				/* Add to the queue of iddle workers. */
				synchronized (iddleWorkers) { iddleWorkers.addLast(Worker.this); }
			}
			synchronized (workers) { workers.remove(Worker.this); }
		}
		/**
		 * @return A boolean indicating whether this worker should terminate.
		 */
		private boolean shouldTerminate() {
			if (shutdown && task == null) return true;
			if (shutdown && task.hasTerminated()) return true;
			if (shutdown && task.shouldCancel()) return true;
			return false;
		}
	}

	/**
	 * The maximum number of threads to execute tasks in parallel. Worker threads are created on
	 * demand until the maximum is reached.
	 */
	private int parallelism;
	/**
	 * A boolean that indicates that pooled thread should orderly terminate.
	 */
	private Boolean shutdown = false;
	/**
	 * Queue of tasks pending to execute.
	 */
	private Queue<Task> pendingTasks = new Queue<>();
	/**
	 * Queue with all available workers, up to the maximum parallelism.
	 */
	private Queue<Worker> workers = new Queue<>();
	/**
	 * Queue with iddle workers.
	 */
	private Queue<Worker> iddleWorkers = new Queue<>();
	/**
	 * Dispatcher thread.
	 */
	private Dispatcher dispatcher;
	/**
	 * Root name of all threads invoked in the executor.
	 */
	private String root;
	/**
	 * Counter of executors instantiated.
	 */
	private static Integer executors = 0;

	/**
	 * Default constructor. Parallelism is set to available processors.
	 */
	public TaskExecutor() {
		this(Runtime.getRuntime().availableProcessors());
	}
	/**
	 * @param parallelism The maximum number of concurrrent worker threads.
	 */
	public TaskExecutor(int parallelism) {
		if (parallelism < 1) throw new IllegalArgumentException();
		synchronized (executors) {
			root = "TE" + (executors++);
		}
		this.parallelism = parallelism;
		this.dispatcher = new Dispatcher();
		this.dispatcher.setName(root + "-Dispatcher");
		this.dispatcher.start();
	}

	/**
	 * @param tasks One or more tasks submitted for execution.
	 */
	public void submit(Task... tasks) {
		if (tasks == null) throw new NullPointerException();
		Queue queue = new Queue();
		for (Task task : tasks) queue.addLast(task);
		submit(queue);
	}
	/**
	 * @param tasks Collection of tasks submitted for execution.
	 */
	public void submit(Collection<? extends Task> tasks) {
		/* Add to the queue of pending tasks. */
		synchronized (pendingTasks) { pendingTasks.addAll(tasks); }
		/* Ensure that the dispatcher has been awakened if it was sleeping. */
		dispatcher.interrupt();
	}

	public void waitForTermination(Collection<? extends Task> tasks) {
		while (true) {
			Thread.yield();
			boolean allTerminated = true;
			for (Task task : tasks) {
				if (!task.hasTerminated()) {
					allTerminated = false;
					break;
				}
			}
			if (allTerminated) break;
		}
	}

	/**
	 * Request an orderly shutdown.
	 */
	public void shutdown() {
		shutdown = true;
		while (true) {
			Thread.yield();
			dispatcher.interrupt();
			synchronized (workers) {
				if (workers.isEmpty()) break;
				for (Worker worker : workers) {
					Task task = worker.task;
					if (task != null) task.requestCancel();
					worker.interrupt();
				}
			}
		}
		pendingTasks.forEach(task -> task.setCancelled());
		pendingTasks.clear();
	}

	public int getCurrentWorkers() { return workers.size(); }
}
