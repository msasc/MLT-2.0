/*
 * Copyright (C) 2018 Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.mlt.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An order definition.
 *
 * @author Miquel Sas
 */
public class Order {

	/**
	 * An order segment is a small structure to pack order segment information.
	 */
	public static class Segment implements Comparable<Object> {

		/** The field. */
		private Field field;
		/** The ascending flag. */
		private boolean ascending = true;

		/**
		 * @param field The field
		 * @param ascending   The ascending flag
		 */
		public Segment(Field field, boolean ascending) {
			if (field == null) throw new NullPointerException();
			this.field = field;
			this.ascending = ascending;
		}

		/**
		 * @return The field.
		 */
		public Field getField() {
			return field;
		}
		/**
		 * @param field The field.
		 */
		public void setField(Field field) {
			if (field == null) throw new NullPointerException();
			this.field = field;
		}
		/**
		 * @return A boolean indicating whether the segment is ascending.
		 */
		public boolean isAscending() {
			return ascending;
		}
		/**
		 * @param ascending A boolean indicating whether the segment is ascending.
		 */
		public void setAscending(boolean ascending) {
			this.ascending = ascending;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			int hash = 0;
			hash ^= field.hashCode();
			hash ^= Boolean.valueOf(ascending).hashCode();
			return hash;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Segment other = (Segment) obj;
			if (!Objects.equals(this.field, other.field)) return false;
			return this.ascending == other.ascending;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compareTo(Object o) {
			if (o == null) throw new NullPointerException();
			if (!(o instanceof Segment)) {
				throw new UnsupportedOperationException("Not comparable type: " + o.getClass().getName());
			}
			Segment orderSegment = (Segment) o;
			int compare = field.compareTo(orderSegment.field);
			if (compare != 0) return compare * (ascending ? 1 : -1);
			return compare;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder(128);
			if (field != null) b.append(field.toString());
			else b.append("null");
			b.append(" - ");
			if (ascending) b.append("ASC");
			else b.append("DESC");
			return b.toString();
		}

	}

	/** List of segments. */
	private List<Segment> segments = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public Order() {}
	/**
	 * @param order Source order to copy.
	 */
	public Order(Order order) {
		for (Segment segment : order.segments) {
			segments.add(new Segment(segment.field, segment.ascending));
		}
	}

	/**
	 * Add an ascending segment with the given field.
	 *
	 * @param field The field.
	 */
	public void add(Field field) {
		segments.add(new Segment(field, true));
	}
	/**
	 * Add a segment defined by the field and the ascending flag.
	 *
	 * @param field The field
	 * @param ascending   The ascending flag
	 */
	public void add(Field field, boolean ascending) {
		segments.add(new Segment(field, ascending));
	}

	/**
	 * @param field The field.
	 * @return A boolean indicating whether this order contains the argument field.
	 */
	public boolean contains(Field field) {
		for (Segment segment : segments) {
			if (segment.getField().equals(field)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param index The segment index.
	 * @return The segment at the given index.
	 */
	public Segment get(int index) {
		return segments.get(index);
	}
	/**
	 * @param index The index of the field.
	 * @return The field the field at the given index.
	 */
	public Field getField(int index) {
		return get(index).getField();
	}
	/**
	 * @param index The index of the segment.
	 * @return A boolean indicating whether the segment is ascending.
	 */
	public boolean isAscending(int index) {
		return get(index).isAscending();
	}
	/**
	 * @return The number of segments.
	 */
	public int size() {
		return segments.size();
	}

	/**
	 * Invert the ascending flag for each segment.
	 */
	public void invertAscending() {
		for (int i = 0; i < segments.size(); i++) {
			segments.get(i).setAscending(!segments.get(i).isAscending());
		}
	}
	/**
	 * @param A boolean indicating the ascending order for all segments.
	 */
	public void setAscending(boolean ascending) {
		segments.forEach(s -> s.setAscending(ascending));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder(256);
		for (int i = 0; i < segments.size(); i++) {
			b.append(segments.get(i).toString());
			if (i < segments.size() - 1) b.append("; ");
		}
		return b.toString();
	}

	// TODO implement  equals and hashcode
}
