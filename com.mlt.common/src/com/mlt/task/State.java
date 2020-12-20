/*
 * Copyright (C) 2018 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.mlt.task;

/**
 * Task states.
 *
 * @author Miquel Sas
 */
public enum State {
	/**
	 * The task has not yet been executed and is ready, or has been reinitialized after execution.
	 * This is the default initial state of the task.
	 */
	READY,
	/**
	 * The task is running. This is set just immediately prior to the task actually doing its first
	 * bit of work.
	 */
	RUNNING,
	/**
	 * The task has completed successfully.
	 */
	SUCCEEDED,
	/**
	 * The task has been cancelled, usually as a result of an external request.
	 */
	CANCELLED,
	/**
	 * Indicates that this task has failed, usually due to some unexpected condition having
	 * occurred, and an exception can be retrieved.
	 */
	FAILED
}
