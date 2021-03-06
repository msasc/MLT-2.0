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

package com.mlt.desktop.stage;

import com.mlt.desktop.control.Pane;

import javax.swing.JWindow;

/**
 * Window, without frame or menu bar.
 *
 * @author Miquel Sas
 */
public class Window extends Stage {
	/**
	 * @param content The content pane.
	 */
	public Window(Pane content) {
		setAWTWindow(new JWindow());
		setContentPane(content);
	}
	/**
	 * @return The internal JWindow.
	 */
	protected JWindow getJWindow() { return (JWindow) getAWTWindow(); }
}
