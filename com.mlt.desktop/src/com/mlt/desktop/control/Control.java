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

package com.mlt.desktop.control;

import com.mlt.desktop.AWT;

import javax.swing.JComponent;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Dimension2D;

/**
 * Base control, essentially a component wrapper, intended to expose only the methods strictly
 * necessary for its functionality.
 *
 * @author Miquel Sas
 */
public class Control {

	/**
	 * @param component The JComponent.
	 * @return The control stored in the component.
	 */
	public static Control getControl(Component component) {
		if (component == null) return null;
		return (Control) AWT.getProperties(component).get("CONTROL");
	}

	/** Internal component. */
	private JComponent component;

	/**
	 * Constructor.
	 */
	protected Control() {}

	/**
	 * @return The control component. Override to return the proper swing component.
	 */
	public JComponent getComponent() {
		return component;
	}
	/**
	 * @param component The swing component.
	 */
	protected final void setComponent(JComponent component) {
		this.component = component;
		/* Store the control. */
		setProperty("CONTROL", this);
	}
	/**
	 * @param key The key.
	 * @return The property.
	 */
	public Object getProperty(String key) {
		return AWT.getProperties(getComponent()).get(key);
	}
	/**
	 * @param key      The key.
	 * @param property The property.
	 */
	public void setProperty(String key, Object property) {
		AWT.getProperties(getComponent()).put(key, property);
	}

	/**
	 * @return The name.
	 */
	public String getName() {
		return component.getName();
	}
	/**
	 * @param name The name.
	 */
	public final void setName(String name) {
		component.setName(name);
	}

	/**
	 * @return The background color.
	 */
	public Color getBackground() {
		return component.getBackground();
	}
	/**
	 * @param background The background color.
	 */
	public final void setBackground(Color background) {
		component.setBackground(background);
	}
	/**
	 * @return The foreground color.
	 */
	public Color getForeground() {
		return component.getForeground();
	}
	/**
	 * @param foreground The foreground color.
	 */
	public void setForeground(Color foreground) {
		component.setForeground(foreground);
	}
	/**
	 * @return The border.
	 */
	public Border getBorder() {
		return component.getBorder();
	}
	/**
	 * @param border The border.
	 */
	public final void setBorder(Border border) {
		component.setBorder(border);
	}
	/**
	 * @return The font.
	 */
	public Font getFont() {
		return component.getFont();
	}
	/**
	 * @param font The font.
	 */
	public void setFont(Font font) {
		component.setFont(font);
	}
	/**
	 * @return The font metrics.
	 */
	public FontMetrics getFontMetrics() {
		return getComponent().getFontMetrics(getFont());
	}

	/**
	 * @return A boolean.
	 */
	public boolean isEnabled() {
		return component.isEnabled();
	}
	/**
	 * @param enabled A boolean.
	 */
	public void setEnabled(boolean enabled) {
		component.setEnabled(enabled);
	}
	/**
	 * @return A boolean.
	 */
	public boolean isOpaque() {
		return component.isOpaque();
	}
	/**
	 * @param opaque A boolean.
	 */
	public final void setOpaque(boolean opaque) {
		component.setOpaque(opaque);
	}
	/**
	 * @return A boolean.
	 */
	public boolean isVisible() {
		return component.isVisible();
	}
	/**
	 * @param visible A boolean.
	 */
	public void setVisible(boolean visible) {
		component.setVisible(visible);
	}

	/**
	 * @param focusable A boolean to set the focusable state.
	 */
	public void setFocusable(boolean focusable) {
		getComponent().setFocusable(focusable);
	}

	/**
	 * @return The maximum size.
	 */
	public Dimension2D getMaximumSize() {
		return component.getMaximumSize();
	}
	/**
	 * @param size The maximum size.
	 */
	public void setMaximumSize(Dimension2D size) {
		component.setMaximumSize(AWT.toDimension(size));
	}
	/**
	 * @return The minimum size.
	 */
	public Dimension2D getMinimumSize() {
		return component.getMinimumSize();
	}
	/**
	 * @param size The minimum size.
	 */
	public void setMinimumSize(Dimension2D size) {
		component.setMinimumSize(AWT.toDimension(size));
	}
	/**
	 * @return The preferred size.
	 */
	public Dimension2D getPreferredSize() {
		return component.getPreferredSize();
	}
	/**
	 * @param size The preferred size.
	 */
	public void setPreferredSize(Dimension2D size) {
		component.setPreferredSize(AWT.toDimension(size));
	}
	/**
	 * @return The size.
	 */
	public Dimension2D getSize() {
		return component.getSize();
	}
	/**
	 * @param size The size.
	 */
	public void setSize(Dimension2D size) {
		component.setSize(AWT.toDimension(size));
	}
}
