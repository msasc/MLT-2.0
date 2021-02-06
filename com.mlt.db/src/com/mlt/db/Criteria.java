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

package com.mlt.db;

import com.mlt.common.collections.Queue;
import com.mlt.db.criteria.Condition;
import com.mlt.db.criteria.Logical;
import com.mlt.db.criteria.Segment;
import com.mlt.db.criteria.Token;

/**
 * A criteria to filter documents.
 *
 * @author Miquel Sas
 */
public class Criteria {

	/**
	 * The list of tokens, only logical operators and segments.
	 */
	private Queue<Token> tokens = new Queue<>();

	/**
	 * Constructor, appends the first empty segment token.
	 */
	public Criteria() {
		tokens.add(new Token(new Segment()));
	}

	/**
	 * Add a condition to the current segment, that must be empty.
	 *
	 * @param cond The condition.
	 */
	public void add(Condition cond) {
		add(null, new Token(cond));
	}
	/**
	 * Add a condition to the current segment, concatenating it with the last condition or criteria
	 * using the argument logical operator.
	 *
	 * @param logOpStr The logical operator.
	 * @param cond     The condition.
	 */
	public void add(String logOpStr, Condition cond) {
		add(logOpStr, new Token(cond));
	}
	/**
	 * Add a criteria to the current segment, that must be empty.
	 *
	 * @param criteria The criteria.
	 */
	public void add(Criteria criteria) {
		add(null, new Token(criteria));
	}
	/**
	 * Add a criteria to the current segment, concatenating it with the last condition or criteria
	 * using the argument logical operator.
	 *
	 * @param logOpStr The logical operator.
	 * @param criteria The criteria.
	 */
	public void add(String logOpStr, Criteria criteria) {
		add(logOpStr, new Token(criteria));
	}

	/**
	 * @param field Field.
	 * @param cmpOpStr Comparison operator.
	 * @param values List of accepted values.
	 */
	public void add(Field field, String cmpOpStr, Value... values) {
		add(null, field, cmpOpStr, values);
	}
	/**
	 * @param logOpStr Logical operator.
	 * @param field Field.
	 * @param cmpOpStr Comparison operator.
	 * @param values List of accepted values.
	 */
	public void add(String logOpStr, Field field, String cmpOpStr, Value... values) {
		Condition cond = new Condition(field, cmpOpStr, values);
		add(logOpStr, new Token(cond));
	}

	/**
	 * Add a new empty segment to fill with filter expressions, concatenating it with the last
	 * segment using the argument logical operator.
	 *
	 * @param logOpStr The logical string operator.
	 */
	public void addSegment(String logOpStr) {
		/* Last segment can not be empty. */
		if (segment().isEmpty()) {
			throw new IllegalStateException();
		}
		/* Logical operator must exist. */
		Logical logOp = Logical.get(logOpStr);
		if (logOp == null) {
			throw new IllegalArgumentException("Invalid logical operator: " + logOpStr);
		}
		/* Append tokens. */
		if (logOp != null) tokens.add(new Token(logOp));
		tokens.add(new Token(new Segment()));
	}

	/**
	 * Add a token concatenating it with the previous token using the logical operator.
	 *
	 * @param logOpStr The logical operator string.
	 * @param token    The token, either a condition or a criteria.
	 */
	private void add(String logOpStr, Token token) {
		/* The token argument can not be null. */
		if (token == null) throw new NullPointerException();
		/* The token argument can not be a logical operator. */
		if (token.isLogical()) {
			throw new IllegalArgumentException("Token argument can not be a logical operator");
		}
		/*
		 * If the last segment is empty, the logical operator must be null because it will be the
		 * first token within the segment.
		 */
		if (logOpStr != null && segment().isEmpty()) {
			throw new IllegalStateException("Last segment is empty and the logical operator must be null");
		}
		/*
		 * If the last segment is not empty, the logical operator can not be null and must be
		 * a valid logical operator.
		 */
		Logical logOp = null;
		if (!segment().isEmpty()) {
			logOp = Logical.get(logOpStr);
			if (logOp == null) {
				throw new IllegalArgumentException("Invalid logical operator: " + logOpStr);
			}
		}
		/* Push tokens. */
		if (logOp != null) segment().add(new Token(logOp));
		segment().add(token);
	}

	/**
	 * @return A boolean indicating whether the criteria is empty.
	 */
	public boolean isEmpty() {
		return tokens.size() == 1 && segment().isEmpty();
	}
	/**
	 * @return a boolean indicating whether the criteria is valid. A criteria is valid if the last
	 * segment is not empty.
	 */
	public boolean isValid() {
		return !segment().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		tokens.forEach(token -> b.append(token.toString()));
		return b.toString();
	}

	/**
	 * @return The last token segment which is the active segment to append filter expressions,
	 * either conditions or criterias.
	 */
	private Segment segment() {
		return tokens.getLast().getSegment();
	}
}
