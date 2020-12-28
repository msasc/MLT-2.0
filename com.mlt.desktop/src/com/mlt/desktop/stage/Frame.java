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

package com.mlt.desktop.stage;

import com.mlt.desktop.control.Control;
import com.mlt.desktop.control.MenuBar;
import com.mlt.desktop.control.Pane;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * Frame extension of window.
 *
 * @author Miquel Sas
 */
public class Frame extends Stage {
	/**
	 * @param content The content pane.
	 */
	public Frame(Pane content) {
		setAWTWindow(new JFrame());
		setContentPane(content);
		getJFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	/**
	 * @return The internal JFrame.
	 */
	protected JFrame getJFrame() { return (JFrame) getAWTWindow(); }
	/**
	 * @return The menu bar or null.
	 */
	public MenuBar getMenuBar() {
		JMenuBar menuBar = getJFrame().getJMenuBar();
		if (menuBar != null) return (MenuBar) Control.getControl(menuBar);
		return null;
	}
	/**
	 * @param menuBar The menu bar.
	 */
	public void setMenuBar(MenuBar menuBar) {
		getJFrame().setJMenuBar(menuBar.getComponent());
	}
	/**
	 * @return The frame title.
	 */
	public String getTitle() {
		return getJFrame().getTitle();
	}
	/**
	 * @param title The title.
	 */
	public void setTitle(String title) {
		getJFrame().setTitle(title);
	}
}
