

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
@SuppressWarnings("serial")

public class DrawStuff extends JComponent
{
	Determiner d;
	double[]gen;
	Color[] colors = getColors();
	// Graphics is the base class that allows for drawing on components
	public DrawStuff(Determiner d, double[]gen)
	{
		this.d=d;
		this.gen=gen;
	}
	
	public Color[] getColors()
	{
		Color[]c = {Color.ORANGE, Color.PINK, Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLACK, Color.DARK_GRAY};
		for(int i=0;i<(int)(Math.random()*50);i++)
		{
			int index_1 = (int)(Math.random()*c.length);
			int index_2 = (int)(Math.random()*c.length);
			
			Color temp = c[index_1];
			c[index_1] = c[index_2];
			c[index_2] = temp;
		}
		
		
		return c;
	}
	
	public int[] getBox(Path2D path, int num)
	{
		int[]a=new int[4];
		a[0]=0;
		a[1]=0;
		a[2]=0;
		a[3]=0;
		
		int n=0;
		while(n<num)
		{
			for(int i=0;i<this.getWidth();i++)
			{
				for(int j=0;j<this.getHeight();j++)
				{
					a[0]=i;
					a[1]=j;
					a[2]=30;
					a[3]=10*(num-n);
					if(path.contains(a[0], a[1], a[2], a[3]))
					{
						a[0]=a[0]+10;
						a[1]=a[1]+10;
						return a;	
					}
				}
			}
			a[0]=0;
			a[1]=0;
			a[2]=0;
			a[3]=0;
			n++;
		}
		
		
		return a;
	}
	
	public void paint(Graphics g)
	{
		// Extends graphics so you can draw dimensional shapes and images

		
		Graphics2D graph2 = (Graphics2D)g;
		
        this.setSize((int) (d.generations[d.generations.length-1])+50, 320);
        this.setBackground(new Color(220,220,220));
		
		this.setSize((int) (d.generations[d.generations.length-1])+50, 320);
		this.setBackground(new Color(220,220,220));
		// Sets preferences for rendering
		// KEY_ANTIALIASING reduces artifacts on shapes
		// VALUE_ANTIALIAS_ON will clean up the edges
		
		graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graph2.setColor(new Color(220,220,220));
		graph2.fillRect(0, 0, this.getWidth(), this.getHeight());

		graph2.setColor(Color.BLACK);

		d.doEverything3_0();
		StuffToDraw[]s = d.s;
		
		//System.out.println("Ch3");
		
		//code for competing populations drawing/calculations
		/*
		CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
		cubicCurve.setCurve(100,138,200,276,300,276,350,276);
		CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();	
		cubicCurve2.setCurve(350,276,300,276,200,50,100,138);
		graph2.draw(cubicCurve);
		graph2.draw(cubicCurve2);
		Path2D path2D = new Path2D.Double();
		path2D.append(cubicCurve, false);
		path2D.append(cubicCurve2, false);
		path2D.closePath();
		graph2.fill(path2D);
		
		graph2.setColor(Color.RED);
		double x1 = cubicCurve2.getX2();
		double y1 = cubicCurve2.getY2();
		double x4 = cubicCurve2.getX1();
		double y4 = cubicCurve2.getY1();
		double x2 = cubicCurve2.getCtrlX2();
		double y2 = cubicCurve2.getCtrlY2();
		double x3 = cubicCurve2.getCtrlX1();
		double y3 = cubicCurve2.getCtrlY1();
		
		CubicCurve2D f1 = new CubicCurve2D.Double();
		f1.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
		//graph2.draw(f1);
		
		
		double st = 100;
		double en = 350;
		double f = 150;
		double t = (f-st)/(en-st);
		double x = Math.pow(1-t,3)*x1 + 3*Math.pow(1-t,2)*t*x2 + 3*(1-t)*Math.pow(t, 2)*x3 + Math.pow(t, 3)*x4;
		double y = Math.pow(1-t,3)*y1 + 3*Math.pow(1-t,2)*t*y2 + 3*(1-t)*Math.pow(t, 2)*y3 + Math.pow(t, 3)*y4;
		System.out.println(x + " " + y);

		
		CubicCurve2D f2 = new CubicCurve2D.Double();
		f2.setCurve(350, 0, 300, 0, x+50, y, x, y);
		
		CubicCurve2D f3 = new CubicCurve2D.Double();
		f3.setCurve(x, y, x2, y, x3, y3, x4, y4);
		
		
		Path2D p = new Path2D.Double();
		p.append(f3, false);
		p.lineTo(1000, 276);
		p.lineTo(1000, 0);
		p.lineTo(350,0);
		p.append(f2, true);
		p.closePath();
		graph2.fill(p);
		
		
		CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
		cubicCurve.setCurve(100,138,200,200,300,0,350,0);
		CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();	
		cubicCurve2.setCurve(350,0,300,0,200,50,100,138);
		graph2.draw(cubicCurve);
		graph2.draw(cubicCurve2);
		Path2D path2D = new Path2D.Double();
		path2D.append(cubicCurve, false);
		path2D.append(cubicCurve2, false);
		path2D.closePath();
		graph2.fill(path2D);
		
		graph2.setColor(Color.RED);
		double x1 = cubicCurve.getX1();
		double y1 = cubicCurve.getY1();
		double x4 = cubicCurve.getX2();
		double y4 = cubicCurve.getY2();
		double x2 = cubicCurve.getCtrlX1();
		double y2 = cubicCurve.getCtrlY1();
		double x3 = cubicCurve.getCtrlX2();
		double y3 = cubicCurve.getCtrlY2();
		
		double st = 100;
		double en = 350;
		double f = 150;
		double t = (f-st)/(en-st);
		double x = Math.pow(1-t,3)*x1 + 3*Math.pow(1-t,2)*t*x2 + 3*(1-t)*Math.pow(t, 2)*x3 + Math.pow(t, 3)*x4;
		double y = Math.pow(1-t,3)*y1 + 3*Math.pow(1-t,2)*t*y2 + 3*(1-t)*Math.pow(t, 2)*y3 + Math.pow(t, 3)*y4;
		System.out.println(x + " " + y);
		
		CubicCurve2D f2 = new CubicCurve2D.Double();
		f2.setCurve(x4,y4,x3,y3,x2,y,x,y);
		
		CubicCurve2D f3 = new CubicCurve2D.Double();
		f3.setCurve(x, y, x+50, y, 300, 276, 350, 276);

		Path2D p = new Path2D.Double();
		p.append(f3, false);
		p.lineTo(1000, 276);
		p.lineTo(1000, 0);
		p.lineTo(350,0);
		p.append(f2, true);
		p.closePath();
		graph2.fill(p);
		*/
		
		
		for(int i=0;i<d.s.length;i++)
		{
			if(s[i].fix)
			{
				//System.out.println("Here6");
				graph2.setPaint(colors[i]);
				Path2D path2D = new Path2D.Double();
				path2D.append(s[i].bottom, false);
				path2D.lineTo(gen[gen.length-1], 276);
				path2D.lineTo(gen[gen.length-1], 0);
				path2D.lineTo(s[i].top.getX1(), 0);
				path2D.append(s[i].top, false);
				path2D.closePath();
				// fill constructed path		
				graph2.fill(path2D);
				//System.out.println(path2D.contains(0, 0));
				
				graph2.setPaint(Color.WHITE);
				graph2.setStroke(new BasicStroke(2));
				graph2.draw(s[i].bottom);
				graph2.draw(s[i].top);
				
				graph2.setFont(new Font("SansSerif", Font.BOLD, 8));

				String delim = " ";
				String[]ss = d.clustered_names.get(i).split(delim);
				int[]a=getBox(path2D, ss.length);
				for(int k=0;k<ss.length;k++)
				{
					graph2.drawString(ss[k], a[0],a[1]);
					a[1]=a[1]+10;
				}
			}
			else
			{
				if(d.two_populations())
				{
					//System.out.println("HERE IAM");
					if(i==0)
					{
						graph2.setPaint(colors[i]);
						Path2D path2D = new Path2D.Double();
						path2D.append(s[i].bottom, false);
						path2D.lineTo(gen[gen.length-1], 276);
						path2D.lineTo(gen[gen.length-1], 0);
						path2D.lineTo(s[i].top.getX1(), 0);
						path2D.append(s[i].top, false);
						path2D.closePath();
						// fill constructed path		
						graph2.fill(path2D);
						//System.out.println(path2D.contains(0, 0));
						
						graph2.setPaint(Color.WHITE);
						graph2.setStroke(new BasicStroke(2));
						graph2.draw(s[i].bottom);
						graph2.draw(s[i].top);
						
						graph2.setFont(new Font("SansSerif", Font.BOLD, 8));

						String delim = " ";
						String[]ss = d.clustered_names.get(i).split(delim);
						int[]a=getBox(path2D, ss.length);
						for(int k=0;k<ss.length;k++)
						{
							graph2.drawString(ss[k], a[0],a[1]);
							a[1]=a[1]+10;
						}
					}
					else
					{
						graph2.setPaint(colors[i]);
						Path2D path2D = new Path2D.Double();
						path2D.append(s[i].bottom, false);
						path2D.lineTo(gen[gen.length-1], 276);
						path2D.lineTo(s[i].top.getX1(), s[i].top.getY1());
						path2D.append(s[i].top, false);
						path2D.closePath();
						graph2.fill(path2D);
						//System.out.println(path2D.contains(0, 0));
						
						graph2.setPaint(Color.WHITE);
						graph2.setStroke(new BasicStroke(2));
						graph2.draw(s[i].bottom);
						graph2.draw(s[i].top);
						
						graph2.setFont(new Font("SansSerif", Font.BOLD, 8));

						String delim = " ";
						String[]ss = d.clustered_names.get(i).split(delim);
						int[]a=getBox(path2D, ss.length);
						for(int k=0;k<ss.length;k++)
						{
							graph2.drawString(ss[k], a[0],a[1]);
							a[1]=a[1]+10;
						}
					}
				}
				else
				{
					//System.out.println("Here7");
					graph2.setPaint(colors[i]);
					Path2D path2D2 = new Path2D.Double();
					path2D2.append(s[i].bottom, false);
					path2D2.append(s[i].top, false);
					path2D2.closePath();
					// fill constructed path		
					graph2.fill(path2D2);
					//System.out.println(path2D2.contains(0, 0));
					
					graph2.setPaint(Color.WHITE);
					graph2.setStroke(new BasicStroke(2));
					graph2.draw(s[i].bottom);
					graph2.draw(s[i].top);
					
					graph2.setFont(new Font("SansSerif", Font.BOLD, 8));

					String delim = " ";
					String[]ss = d.clustered_names.get(i).split(delim);
					int[]a=getBox(path2D2, ss.length);
					for(int k=0;k<ss.length;k++)
					{
						graph2.drawString(ss[k], a[0],a[1]);
						a[1]=a[1]+10;
					}
				}
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
		
		graph2.setPaint(new Color(220,220,220));
		graph2.fillRect((int)d.generations[d.generations.length-1], 0, this.getWidth(), this.getHeight());
		
		graph2.setPaint(Color.BLACK);
		graph2.drawLine(0, 0, 0, 276);
		graph2.drawLine(0, 276, (int)d.generations[d.generations.length-1], 276);
		graph2.drawLine((int)d.generations[d.generations.length-1],276,(int)d.generations[d.generations.length-1],0);
		graph2.drawLine((int)d.generations[d.generations.length-1],0,0,0);
		
		graph2.setFont(new Font("Serif", Font.BOLD, 10));
		for(int i=0;i<gen.length;i++)
		{
			graph2.drawString(Integer.toString((int)gen[i]), (int)gen[i], 290);
		}
		
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
