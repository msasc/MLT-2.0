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

import com.mlt.lang.Numbers;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A basic drawing that packs the shape, the stroke, the fill and draw paints, and the hints to be
 * applied.
 *
 * @author Miquel Sas
 */
public class Drawing {

	/**
	 * Return the enclosing bounds of a collection of drawings.
	 *
	 * @param drawings The list of drawings.
	 * @return The bounds
	 */
	public static Rectangle2D getBounds(Collection<Drawing> drawings) {
		double startX = Numbers.MAX_DOUBLE;
		double startY = Numbers.MAX_DOUBLE;
		double endX = Numbers.MIN_DOUBLE;
		double endY = Numbers.MIN_DOUBLE;
		for (Drawing drawing : drawings) {
			Rectangle2D r = drawing.getBounds();
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
	/**
	 * Scale the drawing.
	 *
	 * @param d The drawing.
	 * @param x The x scale.
	 * @param y The y scale.
	 * @return The scaled shape.
	 */
	public static Drawing scale(Drawing d, double x, double y) {
		return transform(d, AffineTransform.getScaleInstance(x, y));
	}
	/**
	 * Translate the drawing.
	 *
	 * @param d The drawing.
	 * @param x The x translation.
	 * @param y The y translation.
	 * @return The scaled shape.
	 */
	public static Drawing translate(Drawing d, double x, double y) {
		return transform(d, AffineTransform.getTranslateInstance(x, y));
	}
	/**
	 * Apply the affine transform to the drawing.
	 *
	 * @param d The drawing.
	 * @param t The affine transform.
	 * @return The new drawing.
	 */
	public static Drawing transform(Drawing d, AffineTransform t) {
		GeneralPath p = new GeneralPath();
		p.append(d.getShape().getPathIterator(t), true);
		return new Drawing(p);
	}

	/** Shape. */
	private Shape shape;
	/** Stroke. */
	private Stroke stroke;
	/** Draw paint. */
	private Paint drawPaint;
	/** Fill paint. */
	private Paint fillPaint;
	/** List of rendering hints. */
	private Collection<Hint> hints;

	/**
	 * @param shape The shape.
	 */
	public Drawing(Shape shape) {
		this.shape = shape;
	}

	/**
	 * @param key   The key.
	 * @param value The value.
	 */
	public void addHint(RenderingHints.Key key, Object value) {
		if (hints == null) hints = new ArrayList<>();
		hints.add(new Hint(key, value));
	}
	/**
	 * @return The list of hints.
	 */
	public Collection<Hint> getHints() {
		if (hints == null) {
			return Hint.EMPTY_HINTS;
		}
		return hints;
	}

	/**
	 * @return The shape.
	 */
	public Shape getShape() {
		return shape;
	}
	/**
	 * @return The stroke, null if none is defined.
	 */
	public Stroke getStroke() {
		return stroke;
	}
	/**
	 * @param stroke The stroke.
	 */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	/**
	 * @return The dra paint, default to black.
	 */
	public Paint getDrawPaint() {
		if (drawPaint == null) return Color.BLACK;
		return drawPaint;
	}
	/**
	 * @param drawPaint The draw paint.
	 */
	public void setDrawPaint(Paint drawPaint) {
		this.drawPaint = drawPaint;
	}
	/**
	 * @return The fill paint, default to white.
	 */
	public Paint getFillPaint() {
		if (fillPaint == null) return Color.WHITE;
		return fillPaint;
	}
	/**
	 * @param fillPaint The fill paint.
	 */
	public void setFillPaint(Paint fillPaint) {
		this.fillPaint = fillPaint;
	}

	/**
	 * Test whether the specified coordinates are inside the boundary of the drawing.
	 *
	 * @param x x coord.
	 * @param y y coord.
	 * @return A boolean.
	 */
	public boolean contains(double x, double y) {
		return getShape().contains(x, y);
	}
	/**
	 * Test whether the specified point is inside the boundary of the drawing.
	 *
	 * @param p The poit to test.
	 * @return A boolean.
	 */
	public boolean contains(Point2D p) {
		return contains(p.getX(), p.getY());
	}
	/**
	 * Test whether the interior of the drawing entirely contains the specified rectangular area.
	 *
	 * @param x      x coord.
	 * @param y      y coord.
	 * @param width  Width.
	 * @param height Height.
	 * @return A boolean.
	 */
	public boolean contains(double x, double y, double width, double height) {
		return getShape().contains(x, y, width, height);
	}
	/**
	 * Test whether the interior of the drawing entirely contains the specified rectangular area.
	 *
	 * @param r The rectangle.
	 * @return A boolean.
	 */
	public boolean contains(Rectangle2D r) {
		return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	/**
	 * Test whether the interior of the drawing intersects the interior of a specified rectangular
	 * area.
	 *
	 * @param x      x coord.
	 * @param y      y coord.
	 * @param width  Width.
	 * @param height Height.
	 * @return A boolean.
	 */
	public boolean intersects(double x, double y, double width, double height) {
		return getShape().intersects(x, y, width, height);
	}
	/**
	 * Test whether the interior of the drawing intersects the interior of a specified rectangular
	 * area.
	 *
	 * @param r The rectangle.
	 * @return A boolean.
	 */
	public boolean intersects(Rectangle2D r) {
		return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}


	/**
	 * Return the bounds of the drawing. If a stroke to outline the shape has been defined, then the
	 * stroke is used to calculated the bounds, otherwise the shape is used.
	 *
	 * @return The effective bounds.
	 */
	public Rectangle2D getBounds() {
		if (stroke == null) return getShape().getBounds2D();
		return stroke.createStrokedShape(getShape()).getBounds2D();
	}
}
