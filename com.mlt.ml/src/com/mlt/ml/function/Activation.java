/*
 * Copyright (C) 2018 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.mlt.ml.function;


/**
 * Activation function.
 *
 * @author Miquel Sas
 */
public interface Activation {
	/**
	 * Calculates the output values of the function given the trigger values.
	 *
	 * @param triggers The trigger (weighted sum plus bias) values.
	 * @return The activation outputs .
	 */
	public abstract double[] activations(double[] triggers);
	/**
	 * Calculates the first derivatives of the function, given the outputs.
	 *
	 * @param outputs The outputs obtained applying the triggers to activations.
	 * @return The derivatives.
	 */
	public abstract double[] derivatives(double[] outputs);

	/**
	 * @return A suitable name for storage.
	 */
	String getName();
}
