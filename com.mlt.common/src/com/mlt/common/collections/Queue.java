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

package com.mlt.common.collections;

import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * A queue of sequentially linked entries.
 * @author Miquel Sas
 */
public class Queue<E> extends AbstractCollection<E> {

	/**
	 * Linked entry.
	 */
	public class Entry {

		private E item;
		private Entry next;
		private Entry prev;

		/**
		 * Private constructor. Entries can only be created through the queue.
		 * @param item The entry item.
		 */
		private Entry(E item) { this.item = item; }

		/**
		 * @return The iem stored in the entry.
		 */
		public E getItem() { return item; }
		/**
		 * @return The next entry or null.
		 */
		public Entry getNext() { return next; }
		/**
		 * @return The previous entry or null.
		 */
		public Entry getPrev() { return prev; }
	}
	/**
	 * Ascending/descending iterator.
	 */
	private class EntryIterator implements Iterator<E> {
		private int count;
		private Entry entry;
		private Entry last;
		private boolean asc;
		private EntryIterator(boolean asc) {
			this.asc = asc;
			this.count = modCount;
			this.entry = (asc ? head : tail);
		}
		@Override
		public boolean hasNext() {
			return (entry != null);
		}
		@Override
		public E next() {
			if (count != modCount) throw new ConcurrentModificationException();
			E item = entry.item;
			last = entry;
			entry = (asc ? entry.next : entry.prev);
			return item;
		}
		@Override
		public void remove() {
			if (count != modCount) throw new ConcurrentModificationException();
			unlink(last);
			count++;
		}
	}

	private int modCount = 0;
	private int size = 0;
	private Entry head;
	private Entry tail;

	@Override
	public boolean add(E e) {
		addLast(e);
		return true;
	}
	/**
	 * @param item The item to add at the begining.
	 */
	public void addFirst(E item) {
		Entry entry = new Entry(item);
		if (head != null) head.prev = entry;
		else tail = entry;
		entry.next = head;
		head = entry;
		size++;
		modCount++;
	}
	/**
	 * @param item The item to add at the end.
	 */
	public void addLast(E item) {
		Entry entry = new Entry(item);
		if (tail != null) tail.next = entry;
		else head = entry;
		entry.prev = tail;
		tail = entry;
		size++;
		modCount++;
	}
	/**
	 * @return The first item.
	 * @throws NoSuchElementException If the queue is empty.
	 */
	public E getFirst() {
		if (head == null) throw new NoSuchElementException();
		return head.item;
	}
	/**
	 * @param index The index of the required item, starting with 0 at the begining.
	 * @return The required item.
	 * @throws IndexOutOfBoundsException If the index is out of bounds.
	 */
	public E getFirst(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		Entry entry = head;
		for (int i = 0; i < index; i++) entry = entry.next;
		return entry.item;
	}
	/**
	 * @return The las item in the queue.
	 * @throws NoSuchElementException If the queue is empty.
	 */
	public E getLast() {
		if (tail == null) throw new NoSuchElementException();
		return tail.item;
	}
	/**
	 * @param index The index of the required item, starting with 0 at the end.
	 * @return The required item.
	 * @throws IndexOutOfBoundsException If the index is out of bounds.
	 */
	public E getLast(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		Entry entry = tail;
		for (int i = 0; i < index; i++) entry = entry.prev;
		return entry.item;
	}
	/**
	 * @param index The index of the required item.
	 * @return The item at the argument index.
	 * @throws NoSuchElementException If the index is out of bounds.
	 */
	public E getItem(int index) { return getEntry(index).item; }
	/**
	 * @return The first item once removed.
	 * @throws NoSuchElementException If the queue is empty.
	 */
	public E removeFirst() {
		if (head == null) throw new NoSuchElementException();
		E item = head.item;
		head = head.next;
		if (head == null) tail = null;
		else head.prev = null;
		size--;
		modCount++;
		return item;
	}
	/**
	 * @return The last item once removed.
	 * @throws NoSuchElementException If the queue is empty.
	 */
	public E removeLast() {
		if (tail == null) throw new NoSuchElementException();
		E item = tail.item;
		tail = tail.prev;
		if (tail == null) head = null;
		else tail.next = null;
		size--;
		modCount++;
		return item;
	}

	/**
	 * Clear the queue and all of its internal elements.
	 */
	public void clear() {
		for (Entry entry = head; entry != null; ) {
			Entry next = entry.next;
			entry.item = null;
			entry.next = null;
			entry.prev = null;
			entry = next;
		}
		head = tail = null;
		size = 0;
		modCount++;
	}
	/**
	 * @return The size or number of items in the queue.
	 */
	@Override
	public int size() { return size; }

	/**
	 * @return The head entry or null if the queue is empty.
	 */
	public Entry getHead() { return head; }
	/**
	 * @return The tail entry or null if the queue is empty.
	 */
	public Entry getTail() { return tail; }
	/**
	 * @param index The index of the required entry.
	 * @return The entry at the given index.
	 * @throws IndexOutOfBoundsException If the index is out of bounds.
	 */
	public Entry getEntry(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		Entry entry = null;
		if (index < (size >> 1)) {
			entry = head;
			for (int i = 0; i < index; i++) { entry = entry.next; }
		} else {
			entry = tail;
			for (int i = size - 1; i > index; i--) { entry = entry.prev; }
		}
		return entry;
	}

	/**
	 * Implementation of iterable.
	 * @return An ascending iterator.
	 */
	@Override
	public Iterator<E> iterator() { return ascendingIterator(); }
	/**
	 * @return An ascending iterator.
	 */
	public Iterator<E> ascendingIterator() { return new EntryIterator(true); }
	/**
	 * @return A descending iterator.
	 */
	public Iterator<E> descendingIterator() { return new EntryIterator(false); }

	/**
	 * @param a The array to fill with queue items, not null. If the length of the array is less
	 *          than the queue size, only up to array length items are filled, the length of the
	 *          array is not changed.
	 * @return The array filled with queue items starting at the head in ascending order.
	 * @throws NullPointerException If the array is null.
	 */
	@Override
	public <E> E[] toArray(E[] a) { return toArray(a, true); }
	/**
	 * @param a   The array to fill with queue items, not null. If the length of the array is less
	 *            than the queue size, only up to array length items are filled, the length of the
	 *            array is not changed.
	 * @param <E> The queue type.
	 * @return The array filled with queue items starting at the head in ascending order.
	 * @throws NullPointerException If the array is null.
	 */
	public <E> E[] toArrayFromHead(E[] a) { return toArray(a, true); }
	/**
	 * @param a   The array to fill with queue items, not null. If the length of the array is less
	 *            than the queue size, only up to array length items are filled, the length of the
	 *            array is not changed.
	 * @param <E> The queue type.
	 * @return The array filled with queue items starting at the tail in descending order.
	 * @throws NullPointerException If the array is null.
	 */
	public <E> E[] toArrayFromTail(E[] a) { return toArray(a, false); }

	private <E> E[] toArray(E[] a, boolean fromHead) {
		if (a == null) throw new NullPointerException();
		Entry entry = (fromHead ? head : tail);
		int count = (a.length < size ? a.length : size);
		for (int i = 0; i < count; i++) {
			a[i] = (E) entry.item;
			entry = (fromHead ? entry.next : entry.prev);
		}
		return a;
	}

	private void unlink(Entry entry) {
		if (entry == null) return;
		Entry next = entry.next;
		Entry prev = entry.prev;
		if (prev == null) {
			head = next;
		} else {
			prev.next = next;
			entry.prev = null;
		}
		if (next == null) {
			tail = prev;
		} else {
			next.prev = prev;
			entry.next = null;
		}
		entry.item = null;
		size--;
		modCount++;
	}
}
