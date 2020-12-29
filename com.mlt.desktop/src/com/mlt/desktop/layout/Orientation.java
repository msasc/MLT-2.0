/*
 * Copyright (C) 2017 Miquel Sas
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
package com.mlt.desktop.layout;

import javax.swing.SwingConstants;

/**
 * Orientation.
 *
 * @author Miquel Sas
 */
public enum Orientation {
	HORIZONTAL(SwingConstants.HORIZONTAL),
	VERTICAL(SwingConstants.VERTICAL);
	private int orientation;
	/**
	 * @param orientation Swing orientation.
	 */
	private Orientation(int orientation) {
		this.orientation = orientation;
	}
	/**
	 * @return The swing orientation.
	 */
	public int getOrientation() { return orientation; }

	/**
	 * @param orientation The swing orientation.
	 * @return This enum orientation;
	 */
	public static Orientation get(int orientation) {
		if (orientation == HORIZONTAL.getOrientation()) return HORIZONTAL;
		return VERTICAL;
	}
}
