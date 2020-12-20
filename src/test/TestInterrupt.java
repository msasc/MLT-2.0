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

package test;

public class TestInterrupt {
	static class Test extends Thread {
		@Override
		public void run() {
			try {
				while (true) { Thread.sleep(60000); }
			} catch (InterruptedException go) {
//				Thread.interrupted();
				System.out.println("Test interrupted " + System.currentTimeMillis());
			}
		}
	}
	public static void main(String[] args) {
		try {
			Test test = new Test();
			test.interrupt();
			test.start();
			Thread.sleep(100);
			System.out.println("Interrupting test " + System.currentTimeMillis());
			while (test.isInterrupted()) {
				System.out.println("Wow");
			}
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
	}
}
