/*
 * Copyright (c) 2021. Miquel Sas
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
package com.mlt.common.logging;

import com.mlt.common.lang.Strings;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Manage logs using the java.logging module. Uses the default global logger in this first approach, while in future
 * releases it may use others.
 * <p>
 * This class of static methods is mainly a delegator to reduce verbosity.
 *
 * @author Miquel Sas
 */
public class Logs {

	/**
	 * Global MLT logger.
	 */
	private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * @param handler The handler.
	 * @throws SecurityException
	 */
	public static void addHandler(Handler handler) throws SecurityException {
		LOGGER.addHandler(handler);
	}
	/**
	 * @param msg The message.
	 */
	public static void severe(String msg) {
		LOGGER.severe(msg);
	}
	/**
	 * @param msg The message.
	 */
	public static void warning(String msg) {
		LOGGER.warning(msg);
	}
	/**
	 * @param msg The message.
	 */
	public static void info(String msg) {
		LOGGER.info(msg);
	}
	/**
	 * @param msg The message.
	 */
	public static void config(String msg) {
		LOGGER.config(msg);
	}
	/**
	 * @param msg The message.
	 */
	public void fine(String msg) {
		LOGGER.fine(msg);
	}
	/**
	 * @param msg The message.
	 */
	public static void finer(String msg) {
		LOGGER.finer(msg);
	}
	/**
	 * @param thrown The throwable.
	 */
	public static void catching(Throwable thrown) {
		StringBuilder msg = new StringBuilder();
		msg.append("CATCHING");
		msg.append("\n");
		msg.append(thrown.getMessage());
		msg.append("\n");
		msg.append(Strings.getStackTrace(thrown));
		severe(msg.toString());
	}
}
