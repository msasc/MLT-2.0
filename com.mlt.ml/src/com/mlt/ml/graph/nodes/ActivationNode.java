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

import com.mlt.ml.function.Activation;
import com.mlt.ml.graph.Edge;
import com.mlt.ml.graph.Node;

/**
 * An activation node, can have only one input edge and one output edge.
 *
 * @author Miquel Sas
 */
public class ActivationNode extends Node {

	/** Size of values. */
	private int size;
	/** Activation function. */
	private Activation activation;
	/** Flat spot to avoid near zero derivatives. */
	private double flatSpot = 0.01;

	/**
	 * Constructor used to restore.
	 */
	public ActivationNode() {}
	/**
	 * @param size       The size of values that flow throw the node.
	 * @param activation The activation function.
	 */
	public ActivationNode(int size, Activation activation) {
		this.size = size;
		this.activation = activation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInputEdge(Edge edge) {
		if (inputEdges.size() > 0) throw new IllegalStateException("More than one input edge");
		if (edge.size() != size) throw new IllegalStateException("Invalid input edge size");
		edge.setOutputNode(this);
		inputEdges.addLast(edge);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOutputEdge(Edge edge) {
		if (outputEdges.size() > 0) throw new IllegalStateException("More than one output edge");
		if (edge.size() != size) throw new IllegalStateException("Invalid output edge size");
		edge.setInputNode(this);
		outputEdges.addLast(edge);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() { return "node-activation"; }

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
		if (inputEdges.size() == 0) {
			throw new IllegalStateException("Input edges empty");
		}
		if (inputEdges.size() > 1) {
			throw new IllegalStateException("More than one input edge");
		}
		if (inputEdges.getLast().size() != size) {
			throw new IllegalStateException("Invalid input edge size");
		}
		if (outputEdges.size() == 0) {
			throw new IllegalStateException("Output edges empty");
		}
		if (outputEdges.size() > 1) {
			throw new IllegalStateException("More than one output edge");
		}
		if (outputEdges.getLast().size() != size) {
			throw new IllegalStateException("Invalid output edge size");
		}
		if (activation == null) {
			throw new IllegalStateException("Activation is null");
		}
	}

	/**
	 * Apply the activation function and push the output values.
	 */
	@Override
	public void forward() {
		double[] triggerValues = inputEdges.getLast().getForwardData();
		double[] outputValues = activation.activations(triggerValues);
		pushForward(outputValues);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void backward() {
		double[] deltas = outputEdges.getLast().getBackwardData();
		double[] values = outputEdges.getLast().getForwardData();
		double[] derivatives = activation.derivatives(values);
		// Apply derivatives to deltas including a flat spot to avoid near zero	derivatives.
		for (int i = 0; i < size; i++) { deltas[i] = deltas[i] * (derivatives[i] + flatSpot); }
		pushBackward(deltas);
	}
}
