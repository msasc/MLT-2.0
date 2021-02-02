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
 * Node of a computational graph.
 * <p>
 * In a forward calculation or pass, a node reads data from the input edges, processes it, and
 * pushes the resulting data to the output edges.
 * <p>
 * In a backward calculation or pass, a node may read data from the output edges, process it, and
 * optionally push backward data to the input edges.
 *
 * @author Miquel Sas
 */
public abstract class Node {

	/**
	 * String uuid.
	 */
	private String uuid;
	/**
	 * List of input edges.
	 */
	protected Queue<Edge> inputEdges = new Queue<>();
	/**
	 * List of output edges.
	 */
	protected Queue<Edge> outputEdges = new Queue<>();

	/**
	 * @return A suitable name for storage.
	 */
	public abstract String getName();
	/**
	 * @return The UUID that uniquely identifies this node.
	 */
	public String getUUID() {
		if (uuid == null) uuid = UUID.randomUUID().toString();
		return uuid;
	}

	/**
	 * @param edge The edge to add. May throw an exception if the operation does not pass the
	 *             internal validation.
	 */
	public abstract void addInputEdge(Edge edge);
	/**
	 * @param edge The edge to add. May throw an exception if the operation does not pass the
	 *             internal validation.
	 */
	public abstract void addOutputEdge(Edge edge);

	/**
	 * Eventually initialize the node internal data. This method is called after a new network is
	 * built, not when a network is restored.
	 */
	public abstract void initialize();
	/**
	 * Validate the internal node structure. This method is called both when a new network is built
	 * or when a network is restored.
	 *
	 * @throws IllegalStateException If the internal noode structure is not valid.
	 */
	public abstract void validate();

	/**
	 * Generally, request values from input edges, apply node calculations and push values to output
	 * edges.
	 */
	public abstract void forward();
	/**
	 * Generally, request values (deltas) from output edges, apply node calculations (parameters
	 * updates) and push results to input edges.
	 */
	public abstract void backward();

	/**
	 * @param values Output values to push to output edges.
	 */
	protected void pushForward(double[] values) {
		outputEdges.forEach(edge -> pushForward(values));
	}
	/**
	 * @param values Input values (deltas) to push to input edges.
	 */
	protected void pushBackward(double[] values) {
		inputEdges.forEach(edge -> pushBackward(values));
	}
}
