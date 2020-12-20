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

package com.mlt.ml.graph.nodes;

import com.mlt.ml.function.collector.CollectorAddition;
import com.mlt.ml.function.collector.CollectorTransfer;
import com.mlt.ml.graph.Edge;

/**
 * A branch addition node. Can have any number of input nodes and only one output node.
 *
 * @author Miquel Sas
 */
public class AdditionNode extends BranchNode {

	/**
	 * Constructor for restore.
	 */
	public AdditionNode() {
		super();
	}
	/**
	 * @param size Size.
	 */
	public AdditionNode(int size) {
		super(size, new CollectorAddition(), new CollectorTransfer());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOutputEdge(Edge edge) {
		super.addOutputEdge(edge);
		if (outputEdges.size() > 1) throw new IllegalStateException("Too many output edges");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() { return "node-addition"; }
}
