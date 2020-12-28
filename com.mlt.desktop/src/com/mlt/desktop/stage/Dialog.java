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

import com.mlt.desktop.control.Pane;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Dialog extension of window.
 *
 * @author Miquel Sas
 */
public class Dialog extends Stage {
	/**
	 * @param owner   This dialog owner.
	 * @param content The content pane.
	 */
	public Dialog(Stage owner, Pane content) {
		JDialog dialog = null;
		if (owner == null) {
			dialog = new JDialog();
		} else if (owner instanceof Frame) {
			dialog = new JDialog(((Frame) owner).getJFrame());
		} else if (owner instanceof Dialog) {
			dialog = new JDialog(((Dialog) owner).getJDialog());
		}
		setAWTWindow(dialog);
		setContentPane(content);
		getJDialog().setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	/**
	 * @return The internal JDialog.
	 */
	protected JDialog getJDialog() { return (JDialog) getAWTWindow(); }
}
