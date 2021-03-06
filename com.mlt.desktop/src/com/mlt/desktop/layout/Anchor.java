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
package com.mlt.desktop.layout;

import java.awt.GridBagConstraints;

/**
 * Anchors of constraints.
 *
 * @author Miquel Sas
 */
public enum Anchor {
	TOP(GridBagConstraints.NORTH),
	TOP_LEFT(GridBagConstraints.NORTHWEST),
	TOP_RIGHT(GridBagConstraints.NORTHEAST),
	LEFT(GridBagConstraints.WEST),
	BOTTOM(GridBagConstraints.SOUTH),
	BOTTOM_LEFT(GridBagConstraints.SOUTHWEST),
	BOTTOM_RIGHT(GridBagConstraints.SOUTHEAST),
	RIGHT(GridBagConstraints.EAST),
	CENTER(GridBagConstraints.CENTER);
	/** AWT anchor. */
	private int anchor;
	/**
	 * @param anchor AWT (GridBagConstraints) anchor.
	 */
	private Anchor(int anchor) {
		this.anchor = anchor;
	}
	/**
	 * @return The AWT (GridBagConstraints) anchor.
	 */
	public int getAnchor() { return anchor; }
}
