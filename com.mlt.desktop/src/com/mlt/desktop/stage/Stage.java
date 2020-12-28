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

package com.mlt.desktop.stage;

import com.mlt.desktop.AWT;
import com.mlt.desktop.control.Control;
import com.mlt.desktop.control.Pane;
import com.mlt.desktop.event.WindowHandler;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Stage implementation, root of frames, dialog or windows.
 *
 * @author Miquel Sas
 */
public abstract class Stage {

	/**
	 * Stage listener adapter.
	 */
	public static class Adapter implements Listener {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void activated(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void closed(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void closing(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void deactivated(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void deiconified(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void iconified(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void opened(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void focusGained(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void focusLost(Stage stage) {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void stateChanged(Stage stage) {}
	}
	/**
	 * Listener to stage events.
	 */
	interface Listener {
		/**
		 * Stage activated.
		 *
		 * @param stage The stage.
		 */
		void activated(Stage stage);
		/**
		 * Stage closed.
		 *
		 * @param stage The stage.
		 */
		void closed(Stage stage);
		/**
		 * Stage closing.
		 *
		 * @param stage The stage.
		 */
		void closing(Stage stage);
		/**
		 * Stage deactivated.
		 *
		 * @param stage The stage.
		 */
		void deactivated(Stage stage);
		/**
		 * Stage deiconified.
		 *
		 * @param stage The stage.
		 */
		void deiconified(Stage stage);
		/**
		 * Stage iconified.
		 *
		 * @param stage The stage.
		 */
		void iconified(Stage stage);
		/**
		 * Stage opened.
		 *
		 * @param stage The stage.
		 */
		void opened(Stage stage);
		/**
		 * Stage focus gained.
		 *
		 * @param stage The stage.
		 */
		void focusGained(Stage stage);
		/**
		 * Stage focus lost.
		 *
		 * @param stage The stage.
		 */
		void focusLost(Stage stage);
		/**
		 * Stage state changed.
		 *
		 * @param stage The stage.
		 */
		void stateChanged(Stage stage);
	}
	/**
	 * Enumerates window events.
	 */
	private static enum Event {
		/* Widow listener. */
		ACTIVATED,
		CLOSED,
		CLOSING,
		DEACTIVATED,
		DEICONIFIED,
		ICONIFIED,
		OPENED,
		/* Window focus listener. */
		FOCUS_GAINED,
		FOCUS_LOST,
		/* Window state changed. */
		STATE_CHANGED
	}
	/**
	 * Forwarder of window events.
	 */
	private class Forwarder extends WindowHandler {
		/**
		 * Constructor.
		 */
		private Forwarder() {}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowActivated(WindowEvent e) {
			fireWindowEvent(Event.ACTIVATED);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowClosed(WindowEvent e) {
			fireWindowEvent(Event.CLOSED);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			fireWindowEvent(Event.CLOSING);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowDeactivated(WindowEvent e) {
			fireWindowEvent(Event.DEACTIVATED);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowDeiconified(WindowEvent e) {
			fireWindowEvent(Event.DEICONIFIED);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowGainedFocus(WindowEvent e) {
			fireWindowEvent(Event.FOCUS_GAINED);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowIconified(WindowEvent e) {
			fireWindowEvent(Event.ICONIFIED);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowLostFocus(WindowEvent e) {
			fireWindowEvent(Event.FOCUS_LOST);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowOpened(WindowEvent e) {
			fireWindowEvent(Event.OPENED);
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowStateChanged(WindowEvent e) {
			fireWindowEvent(Event.STATE_CHANGED);
		}
	}

	/**
	 * Internal window, either a JWindow, a JDialog or a JFrame.
	 */
	private java.awt.Window awtWindow;
	/**
	 * List of listeners.
	 */
	private List<Listener> listeners = new ArrayList<>();

	/**
	 * Constructor.
	 */
	protected Stage() {}

	/**
	 * @return The internal AWT window.
	 */
	protected java.awt.Window getAWTWindow() {
		return awtWindow;
	}
	/**
	 * @param component The internal window, either a JWindow, a JDialog or a JFrame.
	 */
	protected void setAWTWindow(java.awt.Window window) {
		Forwarder forwarder = new Forwarder();
		this.awtWindow = window;
		this.awtWindow.addWindowListener(forwarder);
		this.awtWindow.addWindowFocusListener(forwarder);
		this.awtWindow.addWindowStateListener(forwarder);
		setProperty("STAGE", this);
	}

	/**
	 * Fire the event.
	 *
	 * @param e The window event.
	 */
	private void fireWindowEvent(Event e) {
		switch (e) {
			case ACTIVATED:
				listeners.forEach(listener -> listener.activated(this));
				break;
			case CLOSED:
				listeners.forEach(listener -> listener.closed(this));
				break;
			case CLOSING:
				listeners.forEach(listener -> listener.closing(this));
				break;
			case DEACTIVATED:
				listeners.forEach(listener -> listener.deactivated(this));
				break;
			case DEICONIFIED:
				listeners.forEach(listener -> listener.deiconified(this));
				break;
			case ICONIFIED:
				listeners.forEach(listener -> listener.iconified(this));
				break;
			case OPENED:
				listeners.forEach(listener -> listener.opened(this));
				break;
			case FOCUS_GAINED:
				listeners.forEach(listener -> listener.focusGained(this));
				break;
			case FOCUS_LOST:
				listeners.forEach(listener -> listener.focusLost(this));
				break;
			case STATE_CHANGED:
				listeners.forEach(listener -> listener.stateChanged(this));
				break;
		}
	}

	/**
	 * @param key The key.
	 * @return The property.
	 */
	public Object getProperty(String key) {
		return AWT.getProperties(awtWindow).get(key);
	}
	/**
	 * @param key      The key.
	 * @param property The property.
	 */
	public void setProperty(String key, Object property) {
		AWT.getProperties(awtWindow).put(key, property);
	}

	/**
	 * @return The content pane.
	 */
	public Pane getContentPane() {
		Container contentPane = null;
		if (awtWindow instanceof JWindow) {
			JWindow window = (JWindow) awtWindow;
			contentPane = window.getContentPane();
		}
		if (awtWindow instanceof JDialog) {
			JDialog dialog = (JDialog) awtWindow;
			contentPane = dialog.getContentPane();
		}
		if (awtWindow instanceof JFrame) {
			JFrame frame = (JFrame) awtWindow;
			contentPane = frame.getContentPane();
		}
		return (Pane) Control.getControl(contentPane);
	}
	/**
	 * @param content The content pane.
	 */
	protected void setContentPane(Pane content) {
		if (awtWindow instanceof JWindow) {
			JWindow window = (JWindow) awtWindow;
			window.setContentPane(content.getComponent());
			return;
		}
		if (awtWindow instanceof JDialog) {
			JDialog dialog = (JDialog) awtWindow;
			dialog.setContentPane(content.getComponent());
			return;
		}
		if (awtWindow instanceof JFrame) {
			JFrame frame = (JFrame) awtWindow;
			frame.setContentPane(content.getComponent());
			return;
		}
	}

	/**
	 * @param image The image.
	 */
	public void setImage(Image image) {
		awtWindow.setIconImage(image);
	}

	/**
	 * Center on screen.
	 */
	public void centerOnScreen() {
		AWT.centerOnScreen(awtWindow);
	}
	/**
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public void setLocation(int x, int y) {
		awtWindow.setLocation(x, y);
	}
	/**
	 * @param size The size.
	 */
	public void setSize(Dimension2D size) {
		awtWindow.setSize((int) size.getWidth(), (int) size.getHeight());
	}
	/**
	 * Set the size as a factor of the screen size.
	 *
	 * @param widthFactor  The width factor.
	 * @param heightFactor The height factor.
	 */
	public void setSize(double widthFactor, double heightFactor) {
		AWT.setSize(awtWindow, widthFactor, heightFactor);
	}

	/**
	 * @param b A boolean.
	 */
	public void setVisible(boolean b) {
		awtWindow.setVisible(b);
	}

	/**
	 * Dispose the required window resources.
	 */
	public void dispose() {
		awtWindow.dispose();
	}
	/**
	 * Pack the window.
	 */
	public void pack() {
		awtWindow.pack();
	}

	/**
	 * {@inheritDoc}
	 */
	public void toBack() {
		awtWindow.toBack();
	}
	/**
	 * {@inheritDoc}
	 */
	public void toFront() {
		awtWindow.toFront();
	}
}
