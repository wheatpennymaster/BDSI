package drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;

import javax.swing.JComponent;
@SuppressWarnings("serial")

public class DrawStuff extends JComponent
{
	Determiner d;
	double[]gen;
	Color[] colors = {Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA};
	// Graphics is the base class that allows for drawing on components
	public DrawStuff(Determiner d, double[]gen)
	{
		this.d=d;
		this.gen=gen;
	}
	
	public void paint(Graphics g)
	{
		// Extends graphics so you can draw dimensional shapes and images
		
		Graphics2D graph2 = (Graphics2D)g;
		
		// Sets preferences for rendering
		// KEY_ANTIALIASING reduces artifacts on shapes
		// VALUE_ANTIALIAS_ON will clean up the edges
		
		graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graph2.setColor(Color.LIGHT_GRAY);
		graph2.fillRect(0, 0, this.getWidth(), this.getHeight());

		graph2.setColor(Color.BLACK);
		graph2.setFont(new Font("Serif", Font.BOLD, 10));
		for(int i=0;i<gen.length;i++)
		{
			graph2.drawString(Integer.toString((int)gen[i]), (int)gen[i], 290);
		}
		d.doEverthing();
		StuffToDraw[]s = d.s;
		
		for(int i=0;i<4;i++)
		{
			if(s[i].fix)
			{
				//System.out.println("Here6");
				graph2.setPaint(colors[i]);
				graph2.draw(s[i].bottom);
				graph2.draw(s[i].top);
				Path2D path2D = new Path2D.Double();

				path2D.append(s[i].bottom, false);
				path2D.lineTo(1000, 276);
				path2D.lineTo(1000, 0);
				path2D.lineTo(s[i].top.getX1(), 0);
				path2D.append(s[i].top, false);
				path2D.closePath();
				// fill constructed path		
				graph2.fill(path2D);
			}
			if(!s[i].fix)
			{
				//System.out.println("Here7");
				graph2.setPaint(colors[i]);
				graph2.draw(s[i].bottom);
				graph2.draw(s[i].top);
				Path2D path2D2 = new Path2D.Double();

				path2D2.append(s[i].bottom, false);
				path2D2.append(s[i].top, false);
				path2D2.closePath();
				// fill constructed path		
				graph2.fill(path2D2);
			}
		}

		/*
		double x1=-1, x2=-1, x3=-1, x4=-1, y1=-1, y2=-1, y3=-1, y4=-1;
		for(int i=0;i<d.clustered_names.size();i++)
		{
			for(int j=0;j<d.averaged_l.get(i).size();j++)
			{
				if((d.averaged_l.get(i).get(j)>.01) && (x1==-1))
				{
					x1=gen[j];
					y1=138;
				}
				if((d.averaged_l.get(i).get(j)>.90) && (x4==-1))
				{
					x4=gen[j];
					y4=276;
				}
				if(x1!=-1 && x4!=-1)
				{
					x2 = x1+(x4-x1)/2;
					x3 = x1+(x4-x1)/2;
					y2 = 138;
					y3 = 276;
					
					graph2.setPaint(colors[i]);
					CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
					cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);	
					graph2.draw(cubicCurve);
					CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
					cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
					graph2.draw(cubicCurve2);
					
					
					Path2D path2D = new Path2D.Double();
					path2D.append(cubicCurve, false);
					path2D.lineTo(1000, 276);
					path2D.lineTo(1000, 0);
					path2D.lineTo(x4, 0);
					path2D.append(cubicCurve2, false);
					path2D.closePath();
					// fill constructed path		
					graph2.fill(path2D);
					graph2.setPaint(Color.BLACK);
					graph2.drawString(d.clustered_names.get(i), (int)x2+70, (int)y3-50);
					break;
					
				}
			}
			x1=-1; x2=-1; x3=-1; x4=-1; y1=-1; y2=-1; y3=-1; y4=-1;
		}
		
		graph2.setPaint(Color.black);
		CubicCurve2D c = new CubicCurve2D.Double();
		c.setCurve(200, 276, 100, 138, 100, 138, 0, 138);
		graph2.draw(c);
		*/
		graph2.setPaint(Color.BLACK);
		graph2.drawLine(0, 0, 0, 276);
		graph2.drawLine(0, 276, 1000, 276);
		graph2.drawLine(1000,276,1000,0);
		graph2.drawLine(1000,0,0,0);
		
		
		/*graph2.setPaint(Color.MAGENTA);	


		CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
		cubicCurve.setCurve(0, 138,   50, 138,   50, 276,   100, 276);	
		graph2.draw(cubicCurve);
		
		CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
		cubicCurve2.setCurve(100, 0, 50, 0, 50, 138, 0, 138);
		graph2.draw(cubicCurve2);

		Path2D path2D = new Path2D.Double();
		path2D.append(cubicCurve, false);
		path2D.lineTo(700, 276);
		path2D.lineTo(700, 0);
		path2D.lineTo(100, 0);
		path2D.append(cubicCurve2, false);
		path2D.closePath();
		// fill constructed path		
		graph2.fill(path2D);
	
		
		
		
		graph2.setPaint(Color.CYAN);
		cubicCurve.setCurve(50, 138, 100, 138, 100, 276, 150, 276);	
		graph2.draw(cubicCurve);
		cubicCurve2.setCurve(150, 0, 100, 0, 100, 138, 50, 138);
		graph2.draw(cubicCurve2);
		Path2D path2D2 = new Path2D.Double();
		path2D2.append(cubicCurve, false);
		path2D2.lineTo(700, 276);
		path2D2.lineTo(700, 0);
		path2D2.lineTo(150, 0);
		path2D2.append(cubicCurve2, false);
		path2D2.closePath();
		graph2.fill(path2D2);
		*/
	}
	
}
