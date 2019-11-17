package com.jeremy.datum.graphics.window;

import javax.swing.*;

import com.jeremy.datum.tools.*;

import java.awt.*;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private Canvas canvas;

	public Window(final String title, final int width, final int height) {
		final Dimension dimensions = new Dimension(width, height);
		this.setTitle(title);
		this.setDefaultCloseOperation(3);
		this.setIconImage(JImage.grab("icons/16.png"));
		this.setResizable(false);
		(this.canvas = new Canvas()).setSize(dimensions);
		this.canvas.setPreferredSize(dimensions);
		this.canvas.setBackground(JColor.BLACK);
		this.add(this.canvas);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);		
	}

	public Canvas getCanvas() {
		return this.canvas;
	}
}
