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
package com.mlt.desktop.control;

import com.mlt.desktop.layout.Anchor;
import com.mlt.desktop.layout.Constraints;
import com.mlt.desktop.layout.Fill;
import com.mlt.desktop.layout.Position;

import java.awt.BorderLayout;
import java.awt.Insets;

/**
 * A border pane with top, left, bottom, right and center controls.
 *
 * @author Miquel Sas
 */
public class BorderPane extends Pane {

	/** Top control. */
	private Control top;
	/** Left control. */
	private Control left;
	/** Bottom control. */
	private Control bottom;
	/** Right control. */
	private Control right;
	/** Center control. */
	private Control center;

	/**
	 * Constructor.
	 */
	public BorderPane() {
		setLayout(new BorderLayout());
	}

	/**
	 * @return The top control.
	 */
	public Control getTop() {
		return getControl(Position.TOP);
	}
	/**
	 * @return The left control.
	 */
	public Control getLeft() {
		return getControl(Position.LEFT);
	}
	/**
	 * @return The bottom control.
	 */
	public Control getBottom() {
		return getControl(Position.BOTTOM);
	}
	/**
	 * @return The right control.
	 */
	public Control getRight() {
		return getControl(Position.RIGHT);
	}
	/**
	 * @return The center control.
	 */
	public Control getCenter() {
		return getControl(Position.CENTER);
	}

	/**
	 * Remove the top control.
	 */
	public void removeTop() {
		removePosition(Position.TOP);
	}
	/**
	 * Remove the left control.
	 */
	public void removeLeft() {
		removePosition(Position.LEFT);
	}
	/**
	 * Remove the bottom control.
	 */
	public void removeBottom() {
		removePosition(Position.BOTTOM);
	}
	/**
	 * Remove the right control.
	 */
	public void removeRight() {
		removePosition(Position.RIGHT);
	}
	/**
	 * Remove the center control.
	 */
	public void removeCenter() {
		removePosition(Position.CENTER);
	}

	/**
	 * @param control The top control.
	 */
	public void setTop(Control control) {
		setTop(control, null);
	}
	/**
	 * @param control The top control.
	 * @param insets  Insets.
	 */
	public void setTop(Control control, Insets insets) {
		setControl(control, insets, Position.TOP);
	}
	/**
	 * @param control The left control.
	 */
	public void setLeft(Control control) {
		setLeft(control, null);
	}
	/**
	 * @param control The left control.
	 * @param insets  Insets.
	 */
	public void setLeft(Control control, Insets insets) {
		setControl(control, insets, Position.LEFT);
	}
	/**
	 * @param control The bottom control.
	 */
	public void setBottom(Control control) {
		setBottom(control, null);
	}
	/**
	 * @param control The bottom control.
	 * @param insets  Insets.
	 */
	public void setBottom(Control control, Insets insets) {
		setControl(control, insets, Position.BOTTOM);
	}
	/**
	 * @param control The right control.
	 */
	public void setRight(Control control) {
		setRight(control, null);
	}
	/**
	 * @param control The right control.
	 * @param insets  Insets.
	 */
	public void setRight(Control control, Insets insets) {
		setControl(control, insets, Position.RIGHT);
	}
	/**
	 * @param control The center control.
	 */
	public void setCenter(Control control) {
		setCenter(control, null);
	}
	/**
	 * @param control The center control.
	 * @param insets  Insets.
	 */
	public void setCenter(Control control, Insets insets) {
		setControl(control, insets, Position.CENTER);
	}

	/**
	 * @param position The position.
	 * @return The control at the given position.
	 */
	private Control getControl(Position position) {
		if (position == Position.TOP) return top;
		if (position == Position.LEFT) return left;
		if (position == Position.BOTTOM) return bottom;
		if (position == Position.RIGHT)	return right;
		if (position == Position.CENTER) return center;
		return null;
	}
	/**
	 * Set the control with insets at the position.
	 * 
	 * @param control  The control.
	 * @param insets   The insets.
	 * @param position The position.
	 */
	private void setControl(Control control, Insets insets, Position position) {
		if (control == null) {
			removePosition(position);
			return;
		}
		if (insets == null) insets = new Insets(0, 0, 0, 0);
		GridBagPane pane = new GridBagPane();
		pane.add(control, new Constraints(Anchor.CENTER, Fill.BOTH, 0, 0, insets));
		control.setProperty("BORDER_PANE_CONTAINER", pane);
		getComponent().add(pane.getComponent(), getBorderLayoutPosition(position));
		if (position == Position.TOP) top = control;
		if (position == Position.LEFT) left = control;
		if (position == Position.BOTTOM) bottom = control;
		if (position == Position.RIGHT) right = control;
		if (position == Position.CENTER) center = control;
	}
	/**
	 * @return The border layout position.
	 */
	private String getBorderLayoutPosition(Position position) {
		if (position == Position.TOP) return BorderLayout.NORTH;
		if (position == Position.LEFT) return BorderLayout.WEST;
		if (position == Position.BOTTOM) return BorderLayout.SOUTH;
		if (position == Position.RIGHT) return BorderLayout.EAST;
		if (position == Position.CENTER) return BorderLayout.CENTER;
		return BorderLayout.CENTER;
	}
	/**
	 * @param position The position to clear.
	 */
	private void removePosition(Position position) {
		Control control = getControl(position);
		if (control == null) return;
		GridBagPane pane = (GridBagPane) control.getProperty("BORDER_PANE_CONTAINER");
		remove(pane);
		if (position == Position.TOP) top = null;
		if (position == Position.LEFT) left = null;
		if (position == Position.BOTTOM) bottom = null;
		if (position == Position.RIGHT) right = null;
		if (position == Position.CENTER) center = null;
	}
}
