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

import com.mlt.common.collections.Queue;
import com.mlt.ml.function.Collector;
import com.mlt.ml.graph.Edge;
import com.mlt.ml.graph.Node;

/**
 * A branch node has one or more input edges and one or more output edges. It also has a forward
 * collector function that processes inputs to produce an output that is pushed to all outpu edges,
 * and a backward collector function that processes deltas and produces a delta that is pushed to
 * all input edges.
 * <p>
 * The size is the same for all input and output edges.
 *
 * @author Miquel Sas
 */
public class BranchNode extends Node {

	/** Input-output size. */
	private int size;
	/** Forward function. */
	private Collector forwardFunction;
	/** Backward function. */
	private Collector backwardFunction;

	/**
	 * Constructor used for restore.
	 */
	public BranchNode() {}

	/**
	 * Constructor.
	 *
	 * @param size             The input-output size.
	 * @param forwardFunction  Forward collector.
	 * @param backwardFunction Backward collector.
	 */
	public BranchNode(int size, Collector forwardFunction, Collector backwardFunction) {
		this.size = size;
		this.forwardFunction = forwardFunction;
		this.backwardFunction = backwardFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() { return "node-branch"; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInputEdge(Edge edge) {
		if (edge.size() != size) throw new IllegalStateException("Invalid input edge size");
		edge.setOutputNode(this);
		inputEdges.add(edge);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOutputEdge(Edge edge) {
		if (edge.size() != size) throw new IllegalStateException("Invalid output edge size");
		edge.setInputNode(this);
		outputEdges.add(edge);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() {
		if (inputEdges.size() == 0) throw new IllegalStateException("No input edges");
		for (Edge edge : inputEdges) {
			if (edge.size() != size) throw new IllegalStateException("Invalid input edge size");
		}
		if (outputEdges.size() == 0) throw new IllegalStateException("No output edges");
		for (Edge edge : outputEdges) {
			if (edge.size() != size) throw new IllegalStateException("Invalid output edge size");
		}
		if (backwardFunction == null) throw new IllegalStateException("Backward function is null");
		if (forwardFunction == null) throw new IllegalStateException("Forward function is null");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forward() {
		Queue<double[]> inputValues = new Queue<>();
		for (Edge edge : inputEdges) {
			inputValues.add(edge.getForwardData());
		}
		double[] outputValues = forwardFunction.collect(inputValues);
		pushForward(outputValues);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void backward() {
		Queue<double[]> outputDeltas = new Queue<>();
		for (Edge edge : outputEdges) outputDeltas.add(edge.getBackwardData());
		if (outputDeltas.isEmpty()) outputDeltas.add(new double[size]);
		double[] inputDeltas = backwardFunction.collect(outputDeltas);
		pushBackward(inputDeltas);
	}
}
