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
package com.mlt.desktop.control.constraints;

import java.awt.Insets;

/**
 * Helper class to manage layout constraints.
 *
 * @author Miquel Sas
 */
public class Constraints {

	/** Anchor. */
	private Anchor anchor;
	/** Fill. */
	private Fill fill;
	/** Grid x. */
	private int x;
	/** Grid y. */
	private int y;
	/** Width or number of horizontal cells. */
	private int width;
	/** Height or number of vertical cells. */
	private int height;
	/** Weight x. */
	private double weightx;
	/** Weight y. */
	private double weighty;
	/** Insets. */
	private Insets insets;

	/**
	 * @param anchor  Anchor.
	 * @param fill    Fill.
	 * @param x       Grid x.
	 * @param y       Grid y.
	 * @param width   Width or number of horizontal cells.
	 * @param height  Height or number of vertical cells.
	 * @param weightx Weight x.
	 * @param weighty Weight y.
	 * @param insets  Insets.
	 */
	public Constraints(
		Anchor anchor,
		Fill fill, 
		int x, 
		int y, 
		int width, 
		int height, 
		double weightx, 
		double weighty, 
		Insets insets) {

		this.anchor = anchor;
		this.fill = fill;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.weightx = weightx;
		this.weighty = weighty;
		this.insets = insets;
	}

	/**
	 * @return The anchor.
	 */
	public Anchor getAnchor() {
		return anchor;
	}
	/**
	 * @return The fill.
	 */
	public Fill getFill() {
		return fill;
	}
	/**
	 * @return The x coord.
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return The y coord.
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return The height.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @return The width.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return The x weight.
	 */
	public double getWeightx() {
		return weightx;
	}
	/**
	 * @return The y weight.
	 */
	public double getWeighty() {
		return weighty;
	}
	/**
	 * @return The insets.
	 */
	public Insets getInsets() {
		return insets;
	}
}
