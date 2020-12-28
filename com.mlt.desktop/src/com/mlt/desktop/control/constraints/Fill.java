/*
 * Copyright (C) 2017 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.mlt.desktop.control.constraints;

/**
 * Fill options.
 *
 * @author Miquel Sas
 */
public enum Fill {
	NONE, HORIZONTAL, VERTICAL, BOTH;

	/**
	 * @param fills The list of fills.
	 * @return The merged fill.
	 */
	public static Fill merge(Fill... fills) {
		Fill merged = NONE;
		for (Fill fill : fills) {
			if (fill == HORIZONTAL) {
				if (merged == NONE)	merged = HORIZONTAL;
				if (merged == VERTICAL) merged = BOTH;
			}
			if (fill == VERTICAL) {
				if (merged == NONE)	merged = VERTICAL;
				if (merged == HORIZONTAL) merged = BOTH;
			}
			if (fill == BOTH) {
				merged = BOTH;
			}
		}
		return merged;
	}
}
