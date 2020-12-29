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
import java.awt.Insets;

/**
 * Helper class to manage layout constraints.
 *
 * @author Miquel Sas
 */
public class Constraints {

	/** Anchor. */
	public Anchor anchor;
	/** Fill. */
	public Fill fill;
	/** Grid x. */
	public int x;
	/** Grid y. */
	public int y;
	/** Width or number of horizontal cells. */
	public int width;
	/** Height or number of vertical cells. */
	public int height;
	/** Weight x. */
	public double weightx;
	/** Weight y. */
	public double weighty;
	/** Insets. */
	public Insets insets;

	/**
	 * Default constructor.
	 */
	public Constraints() {}
	/**
	 * @param anchor Anchor.
	 * @param fill   Fill.
	 * @param x      Grid x.
	 * @param y      Grid y.
	 * @param insets Insets.
	 */
	public Constraints(Anchor anchor, Fill fill, int x, int y, Insets insets) {
		super();
		this.anchor = anchor;
		this.fill = fill;
		this.x = x;
		this.y = y;
		this.width = 1;
		this.height = 1;
		this.weightx = (fill == Fill.HORIZONTAL || fill == Fill.BOTH ? 1 : 0);
		this.weighty = (fill == Fill.VERTICAL || fill == Fill.BOTH ? 1 : 0);
		this.insets = insets;
	}
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
	 * @return The AWT (GridBagConstraints) constraints.
	 */
	public GridBagConstraints toAWT() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = anchor.getAnchor();
		constraints.fill = fill.getFill();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.insets = insets;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		return constraints;
	}
}
