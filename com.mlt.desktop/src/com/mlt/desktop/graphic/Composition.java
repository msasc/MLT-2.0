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

package com.mlt.desktop.graphic;

import com.mlt.desktop.control.Canvas;
import com.mlt.lang.Numbers;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A composition concatenates a list of drawings with fill or draw operations.
 *
 * @author Miquel Sas
 */
public class Composition {

	/** Draw operation. */
	private static final int DRAW = 0;
	/** Fill operation. */
	private static final int FILL = 1;
	/** Paint operation, first fill and then draw. */
	private static final int PAINT = 2;

	/**
	 * Segment structure.
	 */
	class Segment {
		Drawing drawing;
		int operation;
		Segment(Drawing drawing, int operation) {
			this.drawing = drawing;
			this.operation = operation;
		}
	}

	/** List of segments. */
	private List<Segment> segments = new ArrayList<>();

	/** Saved area. */
	private Rectangle2D rect;
	/** Saved pixels. */
	private int[][] pixels;
	/** A boolean that indicates whether the composition is enabled. */
	private boolean enabled = true;

	/**
	 * Constructor.
	 */
	public Composition() {}

	/**
	 * @param drawing The drawing.
	 */
	public void addDraw(Drawing drawing) {
		if (drawing == null) throw new NullPointerException();
		segments.add(new Segment(drawing, DRAW));
	}
	/**
	 * @param drawing The drawing.
	 */
	public void addFill(Drawing drawing) {
		if (drawing == null) throw new NullPointerException();
		segments.add(new Segment(drawing, FILL));
	}
	/**
	 * @param drawing The drawing.
	 */
	public void addPaint(Drawing drawing) {
		if (drawing == null) throw new NullPointerException();
		segments.add(new Segment(drawing, PAINT));
	}

	/**
	 * Clear this composition. This removes previous drawings but not the previously saved area.
	 */
	public void clear() {
		if (!enabled) return;
		segments.clear();
	}
	/**
	 * Clear the saved memory pixels.
	 */
	public void clearSave() {
		pixels = null;
	}

	/**
	 * @return The bounds of the composition.
	 */
	public Rectangle2D getBounds() {
		double startX = Numbers.MAX_DOUBLE;
		double startY = Numbers.MAX_DOUBLE;
		double endX = Numbers.MIN_DOUBLE;
		double endY = Numbers.MIN_DOUBLE;
		for (Segment segment : segments) {
			Rectangle2D r = segment.drawing.getBounds();
			double x = r.getX();
			double y = r.getY();
			double w = r.getWidth();
			double h = r.getHeight();
			if (x < startX) startX = x;
			if (y < startY) startY = y;
			if (x + w > endX) endX = x + w;
			if (y + h > endY) endY = y + h;
		}
		return new Rectangle2D.Double.Double(startX, startY, endX - startX, endY - startY);
	}
	/**
	 * @return The bounds of the composition without exceeding the context bounds and ensuring they
	 * completely cover the composition at pixel (integer-round) level.
	 */
	private Rectangle2D getBounds(Canvas.Context gc) {

		Rectangle2D bounds = getBounds();

		double startX = Numbers.round(bounds.getX(), 0);
		if (startX < 0) startX = 0;
		if (startX > 0) startX -= 1;

		double startY = Numbers.round(bounds.getY(), 0);
		if (startY < 0) startY = 0;
		if (startY > 0) startY -= 1;

		double endX = Numbers.round(bounds.getX() + bounds.getWidth(), 0);
		if (endX >= gc.getWidth()) endX = gc.getWidth() - 1;
		if (endX < gc.getWidth() - 1) endX += 1;

		double endY = Numbers.round(bounds.getY() + bounds.getHeight(), 0);
		if (endY >= gc.getHeight()) endY = gc.getHeight() - 1;
		if (endY < gc.getHeight() - 1) endY += 1;

		return new Rectangle2D.Double(startX, startY, endX - startX + 1, endY - startY + 1);
	}

	/**
	 * @return A boolean.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled A boolean.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Render this composition.
	 *
	 * @param gc The graphics context.
	 */
	public void paint(Canvas.Context gc) {
		if (!enabled) return;
		for (Segment segment : segments) {
			Drawing drawing = segment.drawing;
			int op = segment.operation;
			if (op == FILL || op == PAINT) {
				gc.fill(drawing);
			}
			if (op == DRAW || op == PAINT) {
				gc.draw(drawing);
			}
		}
	}
	/**
	 * Restore the previously stored area.
	 *
	 * @param gc The graphics context.
	 */
	public void restore(Canvas.Context gc) {
		if (!enabled) return;
		if (pixels == null || rect == null) return;

		int startX = (int) rect.getX();
		int startY = (int) rect.getY();
		int width = (int) (rect.getWidth());
		int height = (int) rect.getHeight();
		int endX = startX + width - 1;
		int endY = startY + height - 1;

		int i, j;
		for (int x = startX; x <= endX; x++) {
			i = x - startX;
			for (int y = startY; y <= endY; y++) {
				j = y - startY;
				try {
					gc.setRGB(x, y, pixels[i][j]);
				} catch (Exception exc) {
					System.out.println(error("Rest", x, y, gc, exc));
				}
			}
		}
	}
	/**
	 * Save the area currently under this composition.
	 *
	 * @param gc The graphics context.
	 */
	public void save(Canvas.Context gc) {
		if (!enabled) return;
		rect = getBounds(gc);

		int startX = (int) rect.getX();
		int startY = (int) rect.getY();

		int width = (int) (rect.getWidth());
		int height = (int) rect.getHeight();
		if (width < 0 || height < 0) return;

		int endX = startX + width - 1;
		int endY = startY + height - 1;

		pixels = new int[width][height];

		int i, j;
		for (int x = startX; x <= endX; x++) {
			i = x - startX;
			for (int y = startY; y <= endY; y++) {
				j = y - startY;
				try {
					pixels[i][j] = gc.getRGB(x, y);
				} catch (Exception exc) {
					System.out.println(error("Save", x, y, gc, exc));
				}
			}
		}
	}

	/**
	 * Error helper to trace exceptions thrown during restore/save operations.
	 *
	 * @param prefix
	 * @param x
	 * @param y
	 * @param gc
	 * @param exc
	 * @return
	 */
	private String error(String prefix, int x, int y, Canvas.Context gc, Exception exc) {
		StringBuilder b = new StringBuilder();
		b.append(prefix);
		b.append(": ");
		b.append(x);
		b.append(", ");
		b.append(y);
		b.append(", ");
		b.append(gc.getWidth());
		b.append(", ");
		b.append(gc.getHeight());
		b.append(", ");
		b.append(exc.getMessage());
		return b.toString();
	}
}
