/*
 * Hierarchical.java
 * Author: Thomas Tavolara
 */

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		
		this.setVisible(false);
		
		BufferedImage bi = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics();
		this.paint(g);  //this == JComponent
		g.dispose();
		//try{ImageIO.write(bi,"png",new File("test.png"));}catch (Exception e) {}
		JFileChooser fileChooser = new JFileChooser();
		/*if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
		    try
		    {
				ImageIO.write(bi, "png", fileChooser.getSelectedFile());
			}
		    catch (IOException e)
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}*/
		
	}
	
	
}
