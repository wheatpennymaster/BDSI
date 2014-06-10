/*
 * Hierarchical.java
 * Author: Thomas Tavolara
 */
package drawing;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@SuppressWarnings("serial")

// By extending JFrame we have our applet

public class Erol extends JFrame
{
	public Erol(Determiner d, double[]gen)
	{
		this.setSize(1050, 320);
		
		this.setBackground(Color.white);
		
		this.setTitle("Sound like a bunch of... HOOPLA");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new DrawStuff(d, gen), BorderLayout.CENTER);
		
		this.setVisible(true);
		
		BufferedImage bi = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics();
		this.paint(g);  //this == JComponent
		g.dispose();
		try{ImageIO.write(bi,"png",new File("test.png"));}catch (Exception e) {}
		
		
	}
	
	
}
