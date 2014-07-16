/*
 * Hierarchical.java
 * Author: Thomas Tavolara
 */

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JFileChooser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// By extending JFrame we have our applet

public class Erol extends JFrame
{
	public Erol(Determiner d, double[]gen)
	{
		DrawStuff dd = new DrawStuff(d, gen);

		BufferedImage bi = new BufferedImage((int)gen[gen.length-1]+50, 320, BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics();

		
		dd.paint(g);
		
		g.dispose();
		//try{ImageIO.write(bi,"png",new File("muller.png"));}catch (Exception e) {}

		JFileChooser fileChooser = new JFileChooser();		
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
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
		}	
		


	}
	
	
}
