package com.jeremy.datum.graphics.window;

import static java.lang.Math.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jeremy.datum.tools.JColor;
import com.jeremy.datum.tools.JImage;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private Canvas canvas;

	public Window(final String title, final int width, final int height) {
		final Dimension dimensions = new Dimension(width, height);
		this.setTitle(title);
		this.setDefaultCloseOperation(3);
		this.setIconImage(JImage.grab("icons/16.png"));
		(this.canvas = new Canvas()).setSize(dimensions);
		this.canvas.setPreferredSize(dimensions);
		this.canvas.setBackground(JColor.BLACK);

		Container contentPane = getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent event) {
				Dimension size = new Dimension();

				float canvasAspectRaio = (float) canvas.getWidth() / canvas.getHeight();
				float containerAspectRaio = (float) contentPane.getWidth() / contentPane.getHeight();

				if (containerAspectRaio > canvasAspectRaio) {
					size.height = contentPane.getHeight();
					size.width = round(size.height * canvasAspectRaio);
				} else {
					size.width = contentPane.getWidth();
					size.height = round(size.width / canvasAspectRaio);
				}

				canvas.setPreferredSize(size);
				canvas.setSize(size);
			}
		});
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		contentPane.add(this.canvas);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		addWindowStateListener(new WindowAdapter() {
			@Override
			public void windowStateChanged(WindowEvent event) {
				SwingUtilities.invokeLater(() -> {
					contentPane.revalidate();
				});
			}
		});
	}

	public Canvas getCanvas() {
		return this.canvas;
	}
}
