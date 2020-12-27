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

import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

;

/**
 * A canvas implemented by a JPanel, with managed double buffering to be able to save and restore
 * the canvas area when required by the drawing.
 *
 * @author Miquel Sas
 */
public abstract class Canvas extends JPanel {

	/**
	 * The graphics context to paint on.
	 */
	public class Context {
		/** The graphics object. */
		private Graphics2D g2d;
		/** List of saved rendering hints. */
		private List<Hint> hints = new ArrayList<>();
		/** The buffered image used to paint on. */
		private BufferedImage img;
		/** Indicates whether the context is used in an immediate repaint. */
		private boolean immediateRepaint = false;
		/** The parent graphics. */
		private Graphics2D parent;

		/**
		 * Constructor.
		 */
		private Context() {
			super();
		}

		/**
		 * @param paint The color.
		 */
		public void clear(Paint paint) {
			Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
			fill(rect, paint);
		}
		/**
		 * @param drawing The drawing to stroke the outline.
		 */
		public void draw(Drawing drawing) {
			Stroke stroke = drawing.getStroke();
			if (stroke == null) return;
			saveAntSetHints(drawing.getHints());
			Shape shape = drawing.getShape();
			Paint paint = drawing.getDrawPaint();
			draw(stroke, shape, paint);
			restoreHints();
		}
		/**
		 * @param stroke The stroke to use to to draw the outline.
		 * @param shape  The shape.
		 * @param paint  The paint to use in the outline.
		 */
		public void draw(Stroke stroke, Shape shape, Paint paint) {
			g2d.setStroke(stroke);
			g2d.setPaint(paint);
			g2d.draw(shape);
		}
		/**
		 * @param drawing The drawing.
		 */
		public void fill(Drawing drawing) {
			saveAntSetHints(drawing.getHints());
			fill(drawing.getShape(), drawing.getFillPaint());
			restoreHints();
		}
		/**
		 * @param drawing The drawing.
		 */
		public void fill(Shape shape, Paint paint) {
			g2d.setPaint(paint);
			g2d.fill(shape);
		}
		/**
		 * Flush the content.
		 */
		private void flush() {
			parent.drawImage(gc.img, 0, 0, null);
		}
		/**
		 * @return The current font.
		 */
		public Font getFont() {
			return g2d.getFont();
		}
		/**
		 * @return The font rendering context.
		 */
		public FontRenderContext getFontRenderContext() {
			return g2d.getFontRenderContext();
		}
		/**
		 * @return The height.
		 */
		public double getHeight() {
			return getSize().getHeight();
		}
		/**
		 * @return The width of the drawing area.
		 */
		public double getWidth() {
			return getSize().getWidth();
		}
		/**
		 * @return A boolean indicating whether this context is used in an immediate repaint.
		 */
		public boolean isImmediateRepaint() {
			return immediateRepaint;
		}
		/**
		 * Refresh this context to match the underlying canvas size.
		 */
		private void refresh() {
			Dimension2D sz = getSize();
			if (img == null ||
				g2d == null ||
				img.getWidth() < sz.getWidth() ||
				img.getHeight() < sz.getHeight()) {
				int width = (int) Numbers.round(sz.getWidth(), 0);
				int height = (int) Numbers.round(sz.getHeight(), 0);
				img = parent.getDeviceConfiguration().createCompatibleImage(width, height);
				g2d = img.createGraphics();
			}
		}
		/**
		 * Restore the save hints.
		 */
		private void restoreHints() {
			for (Hint hint : hints) {
				g2d.setRenderingHint(hint.getKey(), hint.getValue());
			}
			hints.clear();
		}
		/**
		 * Save the current rendering hints and set the argument ones.
		 *
		 * @param drawingHints The drawing hints.
		 */
		private void saveAntSetHints(Collection<Hint> drawingHints) {
			if (!drawingHints.isEmpty()) {
				hints.clear();
				for (Hint hint : drawingHints) {
					RenderingHints.Key key = hint.getKey();
					Object value = g2d.getRenderingHint(key);
					hints.add(new Hint(key, value));
					g2d.setRenderingHint(key, hint.getValue());
				}
			}
		}

		/**
		 * @param x x coord.
		 * @param y y coord.
		 * @return The pixel at.
		 */
		public int getRGB(int x, int y) {
			return img.getRGB(x, y);
		}
		/**
		 * Set the pixel color.
		 *
		 * @param x   x coord.
		 * @param y   y coord.
		 * @param rgb RGB (sRGB) color.
		 */
		public void setRGB(int x, int y, int rgb) {
			img.setRGB(x, y, rgb);
		}
	}

	/** The canvas context used to paint. */
	private Context gc;

	/**
	 * Unique constructor, without any layout manager.
	 */
	public Canvas() {
		super(false);
		gc = new Context();
	}
	/**
	 * @return The font render context.
	 */
	public FontRenderContext getFontRenderContext() {
		return gc.getFontRenderContext();
	}
	/**
	 * Force an immediate repaint.
	 */
	public void repaintImmediately() {
		gc.immediateRepaint = true;
		paintImmediately(getBounds());
		gc.immediateRepaint = false;
	}

	/**
	 * Paint. This is the method to override.
	 *
	 * @param gc The context.
	 */
	protected abstract void paintCanvas(Context gc);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void paint(Graphics g) {
		gc.parent = (Graphics2D) g;
		gc.refresh();
		paintCanvas(gc);
		gc.flush();
		paintBorder(g);
	}
}
