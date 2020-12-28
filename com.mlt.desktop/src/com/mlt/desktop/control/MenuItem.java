/*
 * Copyright (C) 2018 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.mlt.desktop.control;

import com.mlt.logging.Logs;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

/**
 * Menu item control.
 *
 * @author Miquel Sas
 */
public class MenuItem extends Control {

	/**
	 * Constructor.
	 */
	public MenuItem() {
		this(JMenuItem.class);
	}

	/**
	 * Constructor used to assign the proper component class (JMenu or JMenuItem)
	 *
	 * @param componentClass The component class.
	 */
	protected MenuItem(Class<? extends JMenuItem> componentClass) {
		try {
			setComponent(componentClass.getDeclaredConstructor().newInstance());
		} catch (InstantiationException |
			IllegalAccessException |
			IllegalArgumentException |
			InvocationTargetException |
			NoSuchMethodException |
			SecurityException e) {
			Logs.catching(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JMenuItem getComponent() {
		return (JMenuItem) super.getComponent();
	}

	/**
	 * @param keyStroke The accelerator keystroke.
	 * @see JMenuItem#setAccelerator(KeyStroke)
	 */
	public void setAccelerator(KeyStroke keyStroke) {
		getComponent().setAccelerator(keyStroke);
	}
	/**
	 * @param actionListener The action.
	 * @see javax.swing.AbstractButton#setAction(javax.swing.Action)
	 */
	public void setAction(ActionListener actionListener) {
		getComponent().addActionListener(actionListener);
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
	public void setIcon(Icon icon) {
		getComponent().setIcon(icon);
	}
	/**
	 * @param mnemonic The mnemonic.
	 */
	public void setMnemonic(int mnemonic) {
		getComponent().setMnemonic(mnemonic);
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
	 * Do click the button.
	 */
	public void doClick() {
		getComponent().doClick();
	}
}
