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
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AWT utilities.
 *
 * @author Miquel Sas
 */
public class AWT {
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
	 * @param size The Dimension2D to convert to simple integer Dimension.
	 * @return The Dimension.
	 */
	public static Dimension toDimension(Dimension2D size) {
		int width = (int) Numbers.round(size.getWidth(), 0);
		int height = (int) Numbers.round(size.getHeight(), 0);
		return new Dimension(width, height);
	}
}
