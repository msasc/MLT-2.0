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

import com.mlt.ml.graph.Edge;
import com.mlt.ml.graph.Node;

import java.util.Arrays;

/**
 * A bias node.
 *
 * @author Miquel Sas
 */
public class BiasNode extends Node {
	/** Bias weights. */
	private double[] weights;

	/**
	 * Constructor used for restore.
	 */
	public BiasNode() {}
	/**
	 * @param size The bias size.
	 */
	public BiasNode(int size) { this.weights = new double[size]; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInputEdge(Edge edge) {
		throw new IllegalStateException("No input edges allowed");
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOutputEdge(Edge edge) {
		if (outputEdges.size() > 0) {
			throw new IllegalStateException("More than one output edge");
		}
		if (edge.size() != weights.length) {
			throw new IllegalStateException("Invalid output edge size");
		}
		edge.setInputNode(this);
		outputEdges.addLast(edge);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() { return "node-bias"; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {
		Arrays.fill(weights, 1.0);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() {
		if (inputEdges.size() > 0) {
			throw new IllegalStateException("No input edges allowed");
		}
		if (outputEdges.size() == 0) {
			throw new IllegalStateException("Output edges empty");
		}
		if (outputEdges.size() > 1) {
			throw new IllegalStateException("More than one output edge");
		}
		if (outputEdges.getLast().size() != weights.length) {
			throw new IllegalStateException("Invalid output edge size");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forward() {
		pushForward(weights);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void backward() {}
}
