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

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Label control.
 *
 * @author Miquel Sas
 */
public class Label extends Control {

	/**
	 * Constructor.
	 */
	public Label() {
		setComponent(new JLabel());
	}
	/**
	 * @param text The text.
	 */
	public Label(String text) {
		this();
		setText(text);
	}
	/**
	 * @param image The icon image.
	 */
	public Label(Icon image) {
		this();
		setIcon(image);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JLabel getComponent() {
		return (JLabel) super.getComponent();
	}

	/**
	 * @return The icon.
	 */
	public Icon getIcon() {
		return getComponent().getIcon();
	}
	/**
	 * @param icon The icon.
	 */
	public final void setIcon(Icon icon) {
		getComponent().setIcon(icon);
	}
	/**
	 * @param iconTextGap The gap.
	 */
	public void setIconTextGap(int iconTextGap) {
		getComponent().setIconTextGap(iconTextGap);
	}
	/**
	 * @return The text.
	 */
	public String getText() {
		return getComponent().getText();
	}
	/**
	 * @param text The text.
	 */
	public void setText(String text) {
		getComponent().setText(text);
	}
	/**
	 * @param alignment The horizontal alignment.
	 */
	public void setHorizontalAlignment(int alignment) {
		boolean horizontal = false;
		horizontal |= alignment == SwingConstants.LEFT;
		horizontal |= alignment == SwingConstants.RIGHT;
		horizontal |= alignment == SwingConstants.CENTER;
		if (!horizontal) throw new IllegalArgumentException();
		getComponent().setHorizontalAlignment(alignment);
	}
	/**
	 * @param alignment The vertical alignment.
	 */
	public void setVerticalAlignment(int alignment) {
		boolean vertical = false;
		vertical |= alignment == SwingConstants.TOP;
		vertical |= alignment == SwingConstants.BOTTOM;
		vertical |= alignment == SwingConstants.CENTER;
		if (!vertical) throw new IllegalArgumentException();
		getComponent().setVerticalAlignment(alignment);
	}
}
