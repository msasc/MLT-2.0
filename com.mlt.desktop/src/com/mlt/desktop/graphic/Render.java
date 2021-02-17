/*
 * Copyright (C) 2018 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.mlt.desktop.graphic;


import com.mlt.common.lang.Numbers;
import com.mlt.desktop.control.Canvas;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A render manages a list of drawings that have to be saved, restored or rendered in a given order.
 *
 * @author Miquel Sas
 */
public class Render {

	/** The list of compositions. */
	private List<Composition> compositions = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public Render() {}
	/**
	 * @param compositions Initial list of compositions.
	 */
	public Render(Composition... compositions) {
		if (compositions == null) throw new NullPointerException();
		Collections.addAll(this.compositions, compositions);
	}


	/**
	 * @param composition The composition.
	 */
	public void add(Composition composition) {
		compositions.add(composition);
	}
	/**
	 * @param index The index.
	 * @return The composition at the given index.
	 */
	public Composition get(int index) {
		return compositions.get(index);
	}
	/**
	 * @param index The index.
	 */
	public void remove(int index) {
		compositions.remove(index);
	}
	/**
	 * @return The size or number of compositions.
	 */
	public int size() {
		return compositions.size();
	}

	/**
	 * @param index   The composition index.
	 * @param drawing The drawing.
	 */
	public void addDraw(int index, Drawing drawing) {
		if (drawing == null) throw new NullPointerException();
		get(index).addDraw(drawing);
	}
	/**
	 * @param index   The composition index.
	 * @param drawing The drawing.
	 */
	public void addFill(int index, Drawing drawing) {
		if (drawing == null) throw new NullPointerException();
		get(index).addFill(drawing);
	}
	/**
	 * @param index   The composition index.
	 * @param drawing The drawing.
	 */
	public void addPaint(int index, Drawing drawing) {
		if (drawing == null) throw new NullPointerException();
		get(index).addPaint(drawing);
	}
	/**
	 * @param index The composition index.
	 */
	public void clear(int index) {
		get(index).clear();
	}
	/**
	 * @param index   The composition index.
	 * @param enabled A boolean.
	 */
	public void setEnabled(int index, boolean enabled) {
		get(index).setEnabled(enabled);
	}

	/**
	 * Paint the list of compositions (restore, save and render).
	 *
	 * @param gc The graphics context.
	 */
	public void render(Canvas.Context gc) {
		restore(gc);
		save(gc);
		paint(gc);
	}
	/**
	 * Restore the list of compositions.
	 *
	 * @param gc The graphics context.
	 */
	public void restore(Canvas.Context gc) {
		compositions.forEach(composition -> composition.restore(gc));
	}
	/**
	 * Save the list of compositions.
	 *
	 * @param gc The graphics context.
	 */
	public void save(Canvas.Context gc) {
		compositions.forEach(composition -> composition.save(gc));
	}
	/**
	 * Render the list of compositions.
	 *
	 * @param gc The graphics context.
	 */
	public void paint(Canvas.Context gc) {
		compositions.forEach(composition -> composition.paint(gc));
	}

	/**
	 * Clear saved canvas by compositions.
	 */
	public void clearSave() {
		compositions.forEach(composition -> composition.clearSave());
	}

	/**
	 * @return The bounds of the render.
	 */
	public Rectangle2D getBounds() {

		double startX = Numbers.MAX_DOUBLE;
		double startY = Numbers.MAX_DOUBLE;
		double endX = Numbers.MIN_DOUBLE;
		double endY = Numbers.MIN_DOUBLE;

		for (Composition composition : compositions) {
			Rectangle2D r = composition.getBounds();
			double x = r.getX();
			double y = r.getY();
			double w = r.getWidth();
			double h = r.getHeight();
			if (x < startX) startX = x;
			if (y < startY) startY = y;
			if (x + w > endX) endX = x + w;
			if (y + h > endY) endY = y + h;
		}

		return new Rectangle2D.Double(startX, startY, endX - startX, endY - startY);
	}
}
