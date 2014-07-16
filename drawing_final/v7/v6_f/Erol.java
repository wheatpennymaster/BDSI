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

public class Erol
{
	public Erol(Determiner d, double[]gen)
	{
		DrawStuff dd = new DrawStuff(d, gen);

		BufferedImage bi = new BufferedImage(1050, 320, BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics();

		
		dd.paint(g);
		
		g.dispose();
		try{ImageIO.write(bi,"png",new File("muller.png"));}catch (Exception e) {}

		
		//JFileChooser fileChooser = new JFileChooser();
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
