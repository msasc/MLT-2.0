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

package com.mlt.desktop;

import com.mlt.lang.Numbers;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.awt.geom.Dimension2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AWT utilities.
 *
 * @author Miquel Sas
 */
public class AWT {

	/**
	 * Tricky class to put a properties map into a component, hidden in a property change listener.
	 */
	private static class ComponentProperties implements PropertyChangeListener {
		private Map<String, Object> properties = new HashMap<>();
		@Override
		public void propertyChange(PropertyChangeEvent evt) {}
	}
	/**
	 * @param component The source component.
	 * @return The properties stored in the component.
	 */
	public static Map<String, Object> getProperties(Component component) {
		ComponentProperties properties = null;
		PropertyChangeListener[] listeners = component.getPropertyChangeListeners();
		for (PropertyChangeListener listener : listeners) {
			if (listener instanceof ComponentProperties) {
				properties = (ComponentProperties) listener;
				break;
			}
		}
		if (properties == null) {
			properties = new ComponentProperties();
			component.addPropertyChangeListener(properties);
		}
		return properties.properties;
	}

	/**
	 * Fills the list with the all the components contained in the parent component and its
	 * sub-components.
	 *
	 * @param cmps   The list to fill.
	 * @param parent The parent component.
	 */
	public static void fillComponentList(Component parent, Collection<Component> cmps) {
		cmps.add(parent);
		if (parent instanceof Container) {
			Container cnt = (java.awt.Container) parent;
			for (int i = 0; i < cnt.getComponentCount(); i++) {
				Component child = cnt.getComponent(i);
				fillComponentList(child, cmps);
			}
		}
	}
	/**
	 * Returns the list of all components contained in a component and its subcomponents.
	 *
	 * @param parent The parent component.
	 * @return The list of components.
	 */
	public static Collection<Component> getAllChildComponents(Component parent) {
		List<Component> list = new ArrayList<>();
		fillComponentList(parent, list);
		return list;
	}
	/**
	 * Returns the list of all components contained in the tres that contains the
	 * component.
	 *
	 * @param parent The starting component.
	 * @return The list of components.
	 */
	public static Collection<Component> getAllComponents(Component parent) {
		return getAllChildComponents(getTopComponent(parent));
	}
	/**
	 * Return the top component, JFrame, JDialog or JWindow, or null if none.
	 *
	 * @param cmp The component to start the search.
	 * @return The top component, JFrame, JDialog or JWindow, or null if none available.
	 */
	public static Component getTopComponent(Component cmp) {
		while (cmp != null) {
			if (cmp instanceof JFrame || cmp instanceof JDialog || cmp instanceof JWindow) {
				return cmp;
			}
			cmp = cmp.getParent();
		}
		return null;
	}

	/**
	 * @param window The window.
	 * @return The graphics device that should apply to a window.
	 */
	private static GraphicsDevice getGraphicsDevice(Window window) {
		if (window != null) return window.getGraphicsConfiguration().getDevice();
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	}
	/**
	 * @param window The window.
	 * @return The screen size containing the argument window or the primary screen if current
	 * window is not selected.
	 */
	public static Dimension2D getScreenSize(Window window) {
		return getGraphicsDevice(window).getConfigurations()[0].getBounds().getSize();
	}

	/**
	 * @param window The window to center on the screen.
	 */
	public static void centerOnScreen(Window window) {
		Dimension2D szWnd = window.getSize();
		Dimension2D szScr = getScreenSize(window);
		double x = (szScr.getWidth() > szWnd.getWidth() ? (szScr.getWidth() - szWnd.getWidth()) / 2 : 0);
		double y = (szScr.getHeight() > szWnd.getHeight() ? (szScr.getHeight() - szWnd.getHeight()) / 2 : 0);
		window.setLocation((int) x, (int) y);
	}
	/**
	 * Set the window applying a width and/or height factor relative to the screen dimension.
	 *
	 * @param window       The window.
	 * @param widthFactor  The width factor relative to the screen (0 &lt; factor &lt;= 1).
	 * @param heightFactor The height factor relative to the screen (0 &lt; factor &lt;= 1).
	 */
	public static void setSize(Window window, double widthFactor, double heightFactor) {
		Dimension2D d = getScreenSize(window);
		double width = d.getWidth() * widthFactor;
		double height = d.getHeight() * heightFactor;
		window.setSize((int) width, (int) height);
	}

	/**
	 * @param size The Dimension2D to convert to simple integer Dimension.
	 * @return The Dimension.
	 */
	public static Dimension toDimension(Dimension2D size) {
		int width = (int) Numbers.round(size.getWidth(), 0);
		int height = (int) Numbers.round(size.getHeight(), 0);
		return new Dimension(width, height);
	}
}
