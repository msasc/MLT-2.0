/*
 * Copyright (c) 2021. Miquel Sas
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

package com.mlt.db.criteria;

import com.mlt.common.collections.Queue;

/**
 * An segment of a filter criteria expression.
 *
 * @author Miquel Sas
 */
public class Segment {
	/** The list of tokens. */
	private Queue<Token> tokens = new Queue<>();
	/** Constructor. */
	public Segment() {}
	/** @param token A token. */
	public void add(Token token) { tokens.add(token); }
	/** @return A boolean indicating whether the segment is empty. */
	public boolean isEmpty() { return tokens.isEmpty(); }

	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		tokens.forEach(token -> b.append(token.toString()));
		return b.toString();
	}
}
