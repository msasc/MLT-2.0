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

package com.mlt.ml.function.collector;

import com.mlt.common.collections.Queue;
import com.mlt.ml.function.Collector;

/**
 * Addition collector function.
 *
 * @author Miquel Sas
 */
public class CollectorAddition implements Collector {
	/**
	 * Constructor for restore.
	 */
	public CollectorAddition() {}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[] collect(Queue<double[]> vectors) {
		double[] result = null;
		for (double[] vector : vectors) {
			if (result == null) result = new double[vector.length];
			if (vector.length != result.length) throw new IllegalArgumentException("Invalid size.");
			for (int i = 0; i < result.length; i++) result[i] += vector[i];
		}
		return result;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() { return "collector-addition"; }
}
