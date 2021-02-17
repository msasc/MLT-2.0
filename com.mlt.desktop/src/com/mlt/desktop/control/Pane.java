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

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.LayoutManager;

/**
 * Pane extension with a default flow layout.
 *
 * @author Miquel Sas
 */
public class Pane extends Control {

	/**
	 * Constructor.
	 */
	public Pane() {
		setComponent(new JPanel());
		setFocusable(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final JPanel getComponent() {
		return (JPanel) super.getComponent();
	}

	/**
	 * @param control The control to add.
	 */
	protected void add(Control control) {
		getComponent().add(control.getComponent());
	}
	/**
	 * @param control The control to remove.
	 */
	protected void remove(Control control) {
		getComponent().remove(control.getComponent());
	}
	/**
	 * Remove all components.
	 */
	protected void removeAll() {
		getComponent().removeAll();
	}
	/**
	 * @param manager The layout manager.
	 */
	protected final void setLayout(LayoutManager manager) {
		getComponent().setLayout(manager);
	}

	/**
	 * @param index The index of the control.
	 * @return The control.
	 */
	public Control getControl(int index) {
		JComponent component = (JComponent) getComponent().getComponent(index);
		return Control.getControl(component);
	}
	/**
	 * @return A boolean that indicates whether the pane has any control.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * @return The size or number of components.
	 */
	public int size() {
		return getComponent().getComponentCount();
	}
}
