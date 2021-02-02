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

package com.mlt.ml.graph;

import com.mlt.common.collections.Queue;

import java.util.UUID;

/**
 * Edge of a computational graph. Data (double[]) flows through the edge in both directions, forward
 * and backward.
 *
 * @author Miquel Sas
 */
public class Edge {

	/**
	 * String uuid.
	 */
	private String uuid;
	/**
	 * Size of the input and output vectors.
	 */
	private int size;

	/**
	 * Backward queue.
	 */
	private Queue<double[]> backwardQueue = new Queue();
	/**
	 * Forward queue.
	 */
	private Queue<double[]> forwardQueue = new Queue();

	/**
	 * Input node, null for an input edge.
	 */
	private Node inputNode;
	/**
	 * Output node, null for an output edge.
	 */
	private Node outputNode;

	/**
	 * @param size The size of the input and output vectors.
	 */
	public Edge(int size) {
		this.size = size;
	}

	/**
	 * @return The UUID that uniquely identifies this edge.
	 */
	public String getUUID() {
		if (uuid == null) uuid = UUID.randomUUID().toString();
		return uuid;
	}
	/**
	 * @return The forward LIFO queue.
	 */
	public Queue getForwardQueue() {
		return forwardQueue;
	}
	/**
	 * @return The backward LIFO queue.
	 */
	public Queue getBackwardQueue() {
		return backwardQueue;
	}

	/**
	 * @return The forward data, normally called values.
	 */
	public double[] getForwardData() {
		if (forwardQueue.isEmpty()) return new double[size];
		return forwardQueue.getFirst();
	}
	/**
	 * @return The backward data, normally called deltas.
	 */
	public double[] getBackwardData() {
		if (backwardQueue.isEmpty()) return new double[size];
		return backwardQueue.getFirst();
	}

	/**
	 * @return The input node.
	 */
	public Node getInputNode() {
		return inputNode;
	}
	/**
	 * @param inputNode The input node.
	 */
	public void setInputNode(Node inputNode) {
		this.inputNode = inputNode;
	}

	/**
	 * @return The output node.
	 */
	public Node getOutputNode() {
		return outputNode;
	}
	/**
	 * @param outputNode the output node.
	 */
	public void setOutputNode(Node outputNode) {
		this.outputNode = outputNode;
	}

	/**
	 * @return The size of the input and output vectors.
	 */
	public int size() {
		return size;
	}

	/**
	 * @param values Vector of input values.
	 */
	public void pushForward(double[] values) {
		if (values.length != size) throw new IllegalArgumentException("Invalid input values size");
		forwardQueue.addFirst(values);
	}
	/**
	 * @param values Vector of output values (deltas).
	 */
	public void pushBackward(double[] values) {
		if (values.length != size) throw new IllegalArgumentException("Invalid input values size");
		backwardQueue.addFirst(values);
	}
}
