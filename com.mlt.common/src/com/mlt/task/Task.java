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

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * A task aimed to be executed in a task pool.
 *
 * @author Miquel Sas
 */
public abstract class Task implements Runnable, Callable<Void> {

	/**
	 * The state, READY on creation.
	 */
	private State state = State.READY;
	/**
	 * Time when when execution started.
	 */
	private LocalDateTime timeStart;
	/**
	 * Time when when execution ended, either successfully or not.
	 */
	private LocalDateTime timeEnd;
	/**
	 * A boolean that indicates whether the execution should be cancelled.
	 */
	private Boolean cancelRequested = false;
	/**
	 * Exception thrown when the execution failed.
	 */
	private Throwable exception;

	/**
	 * Execute the task.
	 *
	 * @throws Throwable If any exception occurs during execution.
	 */
	public abstract void execute() throws Throwable;
	/**
	 * Launch the execution tracing state, time and exception.
	 */
	private void executeTask() {
		/* Reinitialize, set state to RUNNING and register start time. */
		reinitialize();
		setState(State.RUNNING);
		timeStart = LocalDateTime.now();
		/* Perform computation and register any exception. */
		try {
			execute();
		} catch (Throwable exc) {
			exception = exc;
		}
		/* Set the final proper state. */
		if (exception != null) {
			setState(State.FAILED);
		} else {
			if (getState() != State.CANCELLED) {
				setState(State.SUCCEEDED);
			}
		}
		/* Register the end of the execution. */
		timeEnd = LocalDateTime.now();
	}

	/**
	 * @return A boolean indicating whether execution should be cancelled.
	 */
	public boolean shouldCancel() {
		synchronized (cancelRequested) {
			return cancelRequested;
		}
	}
	/**
	 * Request the task to cancel execution.
	 */
	public void requestCancel() {
		synchronized (cancelRequested) {
			cancelRequested = true;
		}
	}
	/**
	 * Indicate that the task has been already cancelled. Extenders should call this method when
	 * acquainted of a request of cancel, and immediately exit the main loop.
	 */
	protected void setCancelled() {
		setState(State.CANCELLED);
	}

	/**
	 * @return The state of the task.
	 */
	private State getState() {
		synchronized (state) {
			return state;
		}
	}
	/**
	 * @param state The new state.
	 */
	private void setState(State state) {
		synchronized (this.state) {
			this.state = state;
		}
	}

	public boolean isReady() { return getState() == State.READY; }
	public boolean isRunning() { return getState() == State.RUNNING; }
	public boolean hasSucceded() { return getState() == State.SUCCEEDED; }
	public boolean wasCancelled() { return getState() == State.CANCELLED; }
	public boolean hasFailed() { return getState() == State.FAILED; }

	/**
	 * @return A boolean indicating that the task has terminated.
	 */
	public boolean hasTerminated() {
		State state = getState();
		return (state == State.SUCCEEDED || state == State.CANCELLED || state == State.FAILED);
	}

	/**
	 * @return The time when execution started.
	 */
	public LocalDateTime getTimeStart() {
		synchronized (timeStart) { return timeStart; }
	}
	/**
	 * @return The time when execution ended.
	 */
	public LocalDateTime getTimeEnd() {
		synchronized (timeEnd) { return timeEnd; }
	}
	/**
	 * Reinitialize internal member to leave the task ready to execute.
	 */
	private void reinitialize() {
		synchronized (this) {
			cancelRequested = false;
			exception = null;
			state = State.READY;
			timeEnd = null;
			timeStart = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		executeTask();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void call() throws Exception {
		executeTask();
		return null;
	}
}
