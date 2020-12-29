/*
 * Copyright (C) 2018 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.mlt.desktop.control;

import com.mlt.desktop.layout.Orientation;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 * A slider control.
 *
 * @author Miquel Sas
 */
public class Slider extends Control {

	/**
	 * Constructor.
	 */
	public Slider() {
		setComponent(new JSlider());
	}
	/**
	 * @param orientation The orientation.
	 */
	public Slider(Orientation orientation) {
		setComponent(new JSlider(orientation.getOrientation()));
	}

	/**
	 * @param listener The change listener.
	 */
	public void addChangeListener(ChangeListener listener) {
		getComponent().addChangeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSlider getComponent() {
		return (JSlider) super.getComponent();
	}

	/**
	 * @return The current value.
	 */
	public int getValue() {
		return getComponent().getValue();
	}
	/**
	 * @param value The current value.
	 */
	public void setValue(int value) {
		getComponent().setValue(value);
	}
	/**
	 * @param maximum The maximum value.
	 */
	public void setMaximum(int maximum) {
		getComponent().setMaximum(maximum);
	}
	/**
	 * @param minimum The minimum value.
	 */
	public void setMinimum(int minimum) {
		getComponent().setMinimum(minimum);
	}

	/**
	 * @return The orientation.
	 */
	public Orientation getOrientation() {
		return Orientation.get(getComponent().getOrientation());
	}
	/**
	 * @param orientation The orientation.
	 */
	public void setOrientation(Orientation orientation) {
		getComponent().setOrientation(orientation.getOrientation());
	}
	/**
	 * @param inverted A boolean that indicates whether behavior should be inverted.
	 */
	public void setInverted(boolean inverted) {
		getComponent().setInverted(inverted);
	}

}
