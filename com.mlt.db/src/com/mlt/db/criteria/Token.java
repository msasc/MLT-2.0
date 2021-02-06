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

import com.mlt.db.Criteria;

/**
 * A token of a filter expression. A token can be either a logical operator, a condition, a segment
 * or an entire criteria.
 *
 * @author Miquel Sas
 */
public class Token {
	/** Object container. */
	private Object token;

	/**
	 * @param logOp A logical operator.
	 */
	public Token(Logical logOp) {
		token = logOp;
	}
	/**
	 * @param cond A condition.
	 */
	public Token(Condition cond) {
		token = cond;
	}
	/**
	 * @param segment A segment.
	 */
	public Token(Segment segment) {
		token = segment;
	}
	/**
	 * @param criteria A criteria.
	 */
	public Token(Criteria criteria) {
		token = criteria;
	}

	/**
	 * @return The token as a logical operator.
	 */
	public Logical getLogical() {
		if (!isLogical()) throw new IllegalStateException();
		return (Logical) token;
	}
	/**
	 * @return The token as a condition.
	 */
	public Condition getCondition() {
		if (!isCondition()) throw new IllegalStateException();
		return (Condition) token;
	}
	/**
	 * @return The token as a segment.
	 */
	public Segment getSegment() {
		if (!isSegment()) throw new IllegalStateException();
		return (Segment) token;
	}
	/**
	 * @return The token as a criteria.
	 */
	public Criteria getCriteria() {
		if (!isCriteria()) throw new IllegalStateException();
		return (Criteria) token;
	}

	/**
	 * @return A boolean indicating whether this token is a logical operator.
	 */
	public boolean isLogical() { return token instanceof Logical; }
	/**
	 * @return A boolean indicating whether this token is a condition expression.
	 */
	public boolean isCondition() { return token instanceof Condition; }
	/**
	 * @return A boolean indicating whether this token is a segment of a criteria.
	 */
	public boolean isSegment() { return token instanceof Segment; }
	/**
	 * @return A boolean indicating whether this token is a criteria.
	 */
	public boolean isCriteria() { return token instanceof Criteria; }

	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (isLogical()) b.append(" ");
		else b.append("(");
		b.append(token.toString());
		if (isLogical()) b.append(" ");
		else b.append(")");
		return b.toString();
	}
}
