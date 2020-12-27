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

import com.mlt.lang.Numbers;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Base control, essentially a component wrapper, intended to expose only the methods strictly
 * necessary for its functionality.
 *
 * @author Miquel Sas
 */
public class Control {

	/**
	 * Returns the properties installed in the component. Since the JComponent class does not offer
	 * a direct method to install user objects, we use the tricky workaround of using the input map
	 * with a MIN_INTEGER key stroke.
	 *
	 * @param component The JComponent.
	 * @return The properties map.
	 */
	private static Map<String, Object> getProperties(JComponent component) {
		InputMap map = component.getInputMap();
		KeyStroke key = KeyStroke.getKeyStroke(Numbers.MIN_INTEGER, 0);
		Map<String, Object> properties = (Map<String, Object>) map.get(key);
		if (properties == null) {
			properties = new HashMap<>();
			map.put(key, properties);
		}
		return properties;
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
		return getProperties(getComponent()).get(key);
	}
	/**
	 * @param key      The key.
	 * @param property The property.
	 */
	public void setProperty(String key, Object property) {
		getProperties(getComponent()).put(key, property);
	}
}
