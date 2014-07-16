/*
 * Hierarchical.java
 * Author: Thomas Tavolara
 */


import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Vector;

public class Determiner
{
	ArrayList<String>names = new ArrayList<String>();
	ArrayList<Integer>clusters = new ArrayList<Integer>();
	ArrayList<Vector<Double>>l = new ArrayList<Vector<Double>>();
	ArrayList<String>clustered_names = new ArrayList<String>();
	ArrayList<Vector<Double>>averaged_l = new ArrayList<Vector<Double>>();
	boolean[][]background;
	//ArrayList<ArrayList<String>>properties = new ArrayList<ArrayList<String>>();
	StuffToDraw[]s;
	double[]generations;
	
	public Determiner(ArrayList<String>names, ArrayList<Integer>clusters, ArrayList<Vector<Double>>l, double[]generations)
	{
		this.generations=generations;
		this.clusters=clusters;
		this.names=names;
		this.l=l;
		for(int i=0;i<clusters.size();i++)
		{
			String temp = "";
			for(int j=0;j<clusters.size();j++)
			{
				if(clusters.get(j)==i)
					temp = temp + names.get(j) + " ";
			}
			if(!(temp.compareTo("")==0))
				clustered_names.add(temp);
		}
		for(int i=0;i<clusters.size();i++)
		{
			int count = 0;
			Vector<Double> temp = new Vector<Double>();
			temp.setSize(l.get(0).size());
			for(int j=0;j<temp.size();j++) //reset temp
			{
				temp.set(j, 0.0);
			}
			
			for(int j=0;j<l.size();j++)
			{
				if(clusters.get(j)==i)
				{
					add_vectors(temp,l.get(j));
					count++;
				}
			}
			
			for(int j=0;j<temp.size();j++)
			{
				temp.set(j, temp.get(j)/count);
			}
			if(count!=0)
				averaged_l.add(temp);
		}
		background = new boolean[clustered_names.size()][clustered_names.size()];
		s = new StuffToDraw[clustered_names.size()];
		trim();
		for(int i=0;i<averaged_l.size();i++)
			reorder();
	}
	
	public StuffToDraw[] getStuffToDraw()
	{
		return s;
	}
	
	public void doEverything3_0()
	{
		boolean top = false;
		for(int i=0;i<averaged_l.size();i++)
		{
			int b = my_background(i);
			
			if(b==-1)	//case of no background (three of them in RMS1-H08)
			{
				if(fixed(i))
				{
					System.out.println("Lol 0");
					System.out.println(" I am: " + i + " My background is: " + b);
					//I don't have a background
					//I fix
					if(i==0)	//first iteration
					{
						//System.out.println("hi");
						double x1 = get_start(i);
						double y1 = 138;
						double x4 = get_end_fixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 276;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, true);
					}
					else if((!fixed(i-1)) && (extinct(i-1)))
					{
						if(top)	//previous was on bottom
						{
							double x1 = s[i-1].top.getX2();
							double y1 = s[i-1].top.getY2();
							double x4 = s[i-1].top.getX1();
							double y4 = s[i-1].top.getY1();
							double x2 = s[i-1].top.getCtrlX2();
							double y2 = s[i-1].top.getCtrlY2();
							double x3 = s[i-1].top.getCtrlX1();
							double y3 = s[i-1].top.getCtrlY1();
							
							CubicCurve2D f1 = new CubicCurve2D.Double();
							f1.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
							
							double st = x1;
							double en = x4;
							double f = get_start(i);
							double t = (f-st)/(en-st);
							double x = Math.pow(1-t,3)*x1 + 3*Math.pow(1-t,2)*t*x2 + 3*(1-t)*Math.pow(t, 2)*x3 + Math.pow(t, 3)*x4;
							double y = Math.pow(1-t,3)*y1 + 3*Math.pow(1-t,2)*t*y2 + 3*(1-t)*Math.pow(t, 2)*y3 + Math.pow(t, 3)*y4;
							
							CubicCurve2D f2 = new CubicCurve2D.Double();
							f2.setCurve(x4, 0, x3, 0, x+100, y, x, y);
							
							CubicCurve2D f3 = new CubicCurve2D.Double();
							f3.setCurve(x, y, x2, y, x3, y3, x4, y4);
							
							s[i]=new StuffToDraw(clustered_names.get(i), f3, f2, true);
							
						}
						else	//previous is on the top
						{
							double x1 = s[i-1].bottom.getX1();
							double y1 = s[i-1].bottom.getY1();
							double x4 = s[i-1].bottom.getX2();
							double y4 = s[i-1].bottom.getY2();
							double x2 = s[i-1].bottom.getCtrlX1();
							double y2 = s[i-1].bottom.getCtrlY1();
							double x3 = s[i-1].bottom.getCtrlX2();
							double y3 = s[i-1].bottom.getCtrlY2();
							
							double st = x1;
							double en = x4;
							double f = get_start(i);
							double t = (f-st)/(en-st);
							double x = Math.pow(1-t,3)*x1 + 3*Math.pow(1-t,2)*t*x2 + 3*(1-t)*Math.pow(t, 2)*x3 + Math.pow(t, 3)*x4;
							double y = Math.pow(1-t,3)*y1 + 3*Math.pow(1-t,2)*t*y2 + 3*(1-t)*Math.pow(t, 2)*y3 + Math.pow(t, 3)*y4;
							//System.out.println(x + " " + y);
							
							CubicCurve2D f2 = new CubicCurve2D.Double();
							f2.setCurve(x4,y4,x3,y3,x2,y,x,y);
							
							CubicCurve2D f3 = new CubicCurve2D.Double();
							f3.setCurve(x, y, x+100, y, x3, 276, x4, 276);
							
							s[i]=new StuffToDraw(clustered_names.get(i), f3, f2, true);
						}
					}
				}
				else
				{
					if(extinct(i))
					{
						System.out.println("Lol 0.1");
						System.out.println(" I am: " + i + " My background is: " + b);
						//I don't have a background
						//I don't fix
						//I go extinct
						if(top)	//draw on the top
						{
							double x1 = get_start(i);
							double y1 = 138;
							double x4 = get_end_notfixed(i);
							double y4 = 0;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							//double y2 = y1;
							double y3 = y4;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, (max_dying(i)+.1)*276, x3, (max_dying(i)+.1)*276, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, y4, x3, y3, x2, y3, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
							
							top = false;
						}
						else	//draw on the bottom
						{
							double x1 = get_start(i);
							double y1 = 138;			//must find a way to determine this so that there is not an overlap
							double x4 = get_end_notfixed(i);
							double y4 = 276;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							//double y2 = y1;
							double y3 = y4;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, y4, x3, (1-max_dying(i)-.15)*276, x2, (1-max_dying(i)-.15)*276, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
							
							top = true;
						}
						
					}
					else
					{
						System.out.println("Lol 0.2");
						System.out.println(" I am: " + i + " My background is: " + b);
						//I don't have a background
						//I don't fix
						//I don't go extinct 
						
						// *** THIS IS A PLACE TO HANDLE THE COMPETING POPULATIONS THAT DON'T KNOCK ONE ANOTHER OUT (BYS1-D07) ***
					}
				}
			}
			
			else if(fixed(b))
			{
				if(fixed(i))
				{
					System.out.println("Lol 1");
					System.out.println(" I am: " + i + " My background is: " + b);
					//my background fixes
					//I fix
					if(b==i-1)	//the background is the previous cluster
					{
						double x1 = get_start(i);
						double y1 = get_start_h(i);
						double x4 = get_end_fixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = y1;
						double y3 = 276;
						
/* Terrible solution */	if(fixed(i-2))	//no competition
						{
/* Terrible solution */		CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
/* Terrible solution */		cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, true);
						}
/* Terrible solution */	else	//some mutations arise at the same time, which changes the start height, so we need to adjust
						{
							double diff = x1 - get_start(b);
/* Terrible solution */		CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y2, x3+diff, y3, x4+40, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
/* Terrible solution */		s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, true);
						}
					}
					else	//the fixed background is not previous cluster, so fixed cluster "i" out-competes something
					{
						for(int nf=b+1;nf<i;nf++)
						{
							if(!fixed(nf))	//looking for a case where the fixed "i" out-competes with some previous cluster "nf"
							{
								if(bottom(nf))	//cluster nf has been plotted on the bottom
								{
									System.out.println(nf);
									double x1 = s[nf].top.getX2();
									double y1 = get_start_h(i);
									double x4 = s[nf].top.getX1();
									double y4 = s[nf].top.getY1();
									double x2 = s[nf].top.getCtrlX2();
									double y2 = s[nf].top.getCtrlY2();
									double x3 = s[nf].top.getCtrlX1();
									double y3 = s[nf].top.getCtrlY1();
									
									CubicCurve2D f1 = new CubicCurve2D.Double();
									f1.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
									
									double st = x1;
									double en = x4;
									double f = get_start(i);
									double t = (f-st)/(en-st);
									double x = Math.pow(1-t,3)*x1 + 3*Math.pow(1-t,2)*t*x2 + 3*(1-t)*Math.pow(t, 2)*x3 + Math.pow(t, 3)*x4;
									double y = Math.pow(1-t,3)*y1 + 3*Math.pow(1-t,2)*t*y2 + 3*(1-t)*Math.pow(t, 2)*y3 + Math.pow(t, 3)*y4;
									
									CubicCurve2D f2 = new CubicCurve2D.Double();
									f2.setCurve(x4, 0, x3, 0, x+100, y, x, y);
									
									CubicCurve2D f3 = new CubicCurve2D.Double();
									f3.setCurve(x, y, x2, y+10, x3, y3, x4, y4);
									
									s[i]=new StuffToDraw(clustered_names.get(i), f3, f2, true);
									break;
								}
								else	//cluster nf has been plotted on the top
								{
									double x1 = s[nf].bottom.getX1();
									double y1 = get_start_h(i);
									double x4 = s[nf].bottom.getX2();
									double y4 = s[nf].bottom.getY2();
									double x2 = s[nf].bottom.getCtrlX1();
									double y2 = s[nf].bottom.getCtrlY1();
									double x3 = s[nf].bottom.getCtrlX2();
									double y3 = s[nf].bottom.getCtrlY2();
									
									double st = x1;
									double en = x4;
									double f = get_start(i);
									double t = (f-st)/(en-st);
									double x = Math.pow(1-t,3)*x1 + 3*Math.pow(1-t,2)*t*x2 + 3*(1-t)*Math.pow(t, 2)*x3 + Math.pow(t, 3)*x4;
									double y = Math.pow(1-t,3)*y1 + 3*Math.pow(1-t,2)*t*y2 + 3*(1-t)*Math.pow(t, 2)*y3 + Math.pow(t, 3)*y4;
									//System.out.println(x + " " + y);
									
									CubicCurve2D f2 = new CubicCurve2D.Double();
									f2.setCurve(x4,y4,x3,y3,x2,y-10,x,y);
									
									CubicCurve2D f3 = new CubicCurve2D.Double();
									f3.setCurve(x, y, x+100, y, x3, 276, x4, 276);
									
									s[i]=new StuffToDraw(clustered_names.get(i), f3, f2, true);
									break;
								}
							}
						}
					}
				}
				else
				{
					if(extinct(i))
					{
						System.out.println("Lol 2");
						System.out.println(" I am: " + i + " My background is: " + b);
						//my background fixes
						//I do not fix
						//I go extinct
						if(top)	//draw on the top
						{
							double x1 = get_start(i);
							double y1 = get_start_h(i);
							double x4 = get_end_notfixed(i);
							double y4 = 0;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							//double y2 = y1;
							double y3 = y4;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, (max_dying(i)+.1)*276, x3, (max_dying(i)+.1)*276, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, y4, x3, y3, x2, y3, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
							
							top = false;
						}
						else	//draw on the bottom
						{
							double x1 = get_start(i);
							double y1 = get_start_h(i);	
							double x4 = get_end_notfixed(i);
							double y4 = 276;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							//double y2 = y1;
							double y3 = y4;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, y4, x3, (1-max_dying(i)-.15)*276, x2, (1-max_dying(i)-.15)*276, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
							top = true;
						}
					}
					else
					{
						System.out.println("Lol 3");
						System.out.println(" I am: " + i + " My background is: " + b);
						//my background fixes
						//I do not fix
						//I do not go extinct
						if(top)	//draw on the top
						{
							double x1 = get_start(i);
							double y1 = get_start_h(i);
							double x4 = get_end_notfixed(i);
							double y4 = 0;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							//double y2 = y1;
							double y3 = y4;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, (max_dying(i)+.1)*276, x3, (max_dying(i)+.1)*276, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
/* Terrible solution */		cubicCurve2.setCurve(x4, y4, x3, y3, x2, y3+30, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
							
							top = false;
						}
						else	//draw on the bottom
						{
							double x1 = get_start(i);
							double y1 = get_start_h(i);	
							double x4 = get_end_notfixed(i);
							double y4 = 276;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							//double y2 = y1;
							double y3 = y4;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, y4, x3, (1-max_dying(i)-.15)*276, x2, (1-max_dying(i)-.15)*276, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
							top = true;
						}
					}
				}
			}
			else
			{
				System.out.println("Lol 4");
				System.out.println(" I am: " + i + " My background is: " + b);
				//my background does not fix, therefore I don't fix
				
				if(bottom(b))	//background is on bottom
				{
					double x1 = get_start(i);
					double y1 = 138;			//must find a way to determine this so that there is not an overlap
					double x4 = get_end_notfixed(i);
					double y4 = 276;
					double x2 = x1+(x4-x1)/2;
					double x3 = x1+(x4-x1)/2;
					//double y2 = y1;
					double y3 = y4;
					CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
					cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
					CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
					cubicCurve2.setCurve(x4, y4, x3, (1-max_dying(i)-.15)*276, x2, (1-max_dying(i)-.15)*276, x1, y1);
					s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
				}
				else	//background is on top
				{
					double x1 = get_start(i);
					double y1 = get_start_h(i);
					double x4 = get_end_notfixed(i);
					double y4 = 0;
					double x2 = x1+(x4-x1)/2;
					double x3 = x1+(x4-x1)/2;
					//double y2 = y1;
					double y3 = y4;
					CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
					cubicCurve.setCurve(x1, y1, x2, (max_dying(i)+.1)*276, x3, (max_dying(i)+.1)*276, x4, y4);
					CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
					cubicCurve2.setCurve(x4, y4, x3, y3, x2, y3, x1, y1);
					s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
				}
			}
		}
	}
	
	public void doEverthing2_0()
	{
		/*
		if(two_populations())
		{
			double x1 = get_start(0);
			double y1 = 100;
			double x4 = generations[generations.length-1];
			double y4 = averaged_l.get(0).get(averaged_l.get(0).size()-3) * 276;
			double x2 = 700;
			double x3 = -200;
			double y2 = 100;
			double y3 = y4;
			CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
			cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
			CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
			cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
			s[0]=new StuffToDraw(clustered_names.get(0),cubicCurve,cubicCurve2, false);
			
			for(int i=1;i<averaged_l.size();i++)
			{
				double x11 = get_start(i);
				double y11 = 220;			//must find a way to determine this so that there is not an overlap
				double x44 = generations[generations.length-1];
				double y44 = 276;
				double x22 = 400;
				double x33 = -100;
				double y22 = 220;
				double y33 = y44;
				CubicCurve2D cubicCurve1 = new CubicCurve2D.Double();	
				cubicCurve1.setCurve(x11, y11, x22, y22, x33, y33, x44, y44);
				CubicCurve2D cubicCurve21 = new CubicCurve2D.Double();
				cubicCurve21.setCurve(x44, y4, x33, y4, x22, y22, x11, y11);
				s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve1,cubicCurve21, false);
				
			}
		}
		*/
		//else
		{
			for(int i=0;i<averaged_l.size();i++)
			{
				if(fixed(i))
				{
					if(i==0) //first iteration
					{
						System.out.println("Here0");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_fixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 276;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, true);
					}
					else if(same_background(i,i-1))
					{
						if((get_start(i)-get_start(i-1))<=100)
						{
							System.out.println("Here1.5");
							double x1 = get_start(i);
							double y1 = 138;			//must find a way to determine this so that there is not an overlap
							double x4 = get_end_fixed(i);
							double y4 = 276;
							double x2 = x1+(x4-x1)/2+100;
							double x3 = x1+(x4-x1)/2;
							double y2 = 138;
							double y3 = 150;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2 ,true);
						}
						else
						{
							System.out.println("Here1");
							double x1 = get_start(i);
							double y1 = 138;			//must find a way to determine this so that there is not an overlap
							double x4 = get_end_fixed(i);
							double y4 = 276;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							double y2 = 138;
							double y3 = 276;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2 ,true);
						}
					}
					else //not same background
					{
						System.out.println("Here2");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_fixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 276;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, true);
					}
				}
	
				else //not fixed
				{
					if(i==0) //first iteration
					{
						System.out.println("Here3");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_notfixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 276;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
					}
					else if(same_background(i,i-1))
					{
						if(i%2==1)
						{
							System.out.println("Here4"); //bottom
							double x1 = get_start(i);
							double y1 = 138;			//must find a way to determine this so that there is not an overlap
							double x4 = get_end_notfixed(i);
							double y4 = 276;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							double y2 = 138;
							double y3 = 276;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
						}
						else
						{
							System.out.println("Here4.5"); //top
							double x1 = get_start(i);
							double y1 = 138;			//must find a way to determine this so that there is not an overlap
							double x4 = get_end_notfixed(i);
							double y4 = 0;
							double x2 = x1+(x4-x1)/2;
							double x3 = x1+(x4-x1)/2;
							double y2 = 138;
							double y3 = 0;
							CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
							cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
							CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
							cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
							s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
						}
					}
					else //not same background
					{
						System.out.println("Here5");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_notfixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 276;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y3-30, x3, y3-30, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
					}
				}
			}
		}
	}
	
	public void doEverthing()
	{
		for(int i=0;i<averaged_l.size();i++)
		{
			if(fixed(i))
			{
				if(i==0) //first iteration
				{
					System.out.println("Here0");
					double x1 = get_start(i);
					double y1 = 138;			//must find a way to determine this so that there is not an overlap
					double x4 = get_end_fixed(i);
					double y4 = 276;
					double x2 = x1+(x4-x1)/2;
					double x3 = x1+(x4-x1)/2;
					double y2 = 138;
					double y3 = 276;
					CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
					cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
					CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
					cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
					s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, true);
				}
				else if(same_background(i,i-1))
				{
					if((get_start(i)-get_start(i-1))<=100)
					{
						System.out.println("Here1.5");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_fixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2+100;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 150;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2 ,true);
					}
					else
					{
						System.out.println("Here1");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_fixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 276;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y2, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2 ,true);
					}
				}
				else //not same background
				{
					System.out.println("Here2");
					double x1 = get_start(i);
					double y1 = 138;			//must find a way to determine this so that there is not an overlap
					double x4 = get_end_fixed(i);
					//double y4 = 276;
					double x2 = x1+(x4-x1)/2;
					double x3 = x1+(x4-x1)/2;
					double y2 = 138;
					//double y3 = 276;
					CubicCurve2D cubicCurve = new CubicCurve2D.Double();
					cubicCurve.setCurve(x1, y1, s[i-1].top.getCtrlX2(), s[i-1].top.getCtrlY2(), s[i-1].top.getCtrlX1(), s[i-1].top.getCtrlY1(),
																																	s[i-1].top.getX1(), s[i-1].top.getY1());
					CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
					cubicCurve2.setCurve(x4, 0, x3, 0, x2, y2, x1, y1);
					s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, true);
				}
			}

			else //not fixed
			{
				if(i==0) //first iteration
				{
					System.out.println("Here3");
					double x1 = get_start(i);
					double y1 = 138;			//must find a way to determine this so that there is not an overlap
					double x4 = get_end_notfixed(i);
					double y4 = 276;
					double x2 = x1+(x4-x1)/2;
					double x3 = x1+(x4-x1)/2;
					double y2 = 138;
					double y3 = 276;
					CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
					cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
					CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
					cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
					s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
				}
				else if(same_background(i,i-1))
				{
					if(i%2==1)
					{
						System.out.println("Here4");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_notfixed(i);
						double y4 = 276;
						double x2 = x1+(x4-x1)/2;
						double x3 = x1+(x4-x1)/2;
						double y2 = 138;
						double y3 = 276;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
					}
					else
					{
						System.out.println("Here4.5");
						double x1 = get_start(i);
						double y1 = 138;			//must find a way to determine this so that there is not an overlap
						double x4 = get_end_notfixed(i);
						double y4 = 0;
						double x2 = get_start(i-1)+(get_end_notfixed(i-1)-get_start(i-1))/2+100;
						double x3 = get_start(i-1)+(get_end_notfixed(i-1)-get_start(i-1))/2+100;
						double y2 = 138;
						double y3 = 0;
						CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
						cubicCurve.setCurve(x1, y1, x2, y3, x3, y3, x4, y4);
						CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
						cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
						s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
					}
				}
				else //not same background
				{
					System.out.println("Here5");
					double x1 = get_start(i);
					double y1 = 138;			//must find a way to determine this so that there is not an overlap
					double x4 = get_end_notfixed(i);
					double y4 = 276;
					double x2 = x1+(x4-x1)/2;
					double x3 = x1+(x4-x1)/2;
					double y2 = 138;
					double y3 = 276;
					CubicCurve2D cubicCurve = new CubicCurve2D.Double();	
					cubicCurve.setCurve(x1, y1, x2, y3-30, x3, y3-30, x4, y4);
					CubicCurve2D cubicCurve2 = new CubicCurve2D.Double();
					cubicCurve2.setCurve(x4, y4, x3, y2, x2, y2, x1, y1);
					s[i]=new StuffToDraw(clustered_names.get(i),cubicCurve,cubicCurve2, false);
				}
			}
		}
	}
	
	public void reorder()
	{
		for(int i=0;i<averaged_l.size();i++)
		{
			int low_j=0;
			int min=averaged_l.get(0).size();
			for(int j=i;j<averaged_l.size();j++)
			{
				for(int k=0;k<min;k++)
				{
					if((averaged_l.get(j).get(k)!=0) && (k<min))
					{
						low_j=j;
						min=k;
						break;
					}	
				}
			}
			Vector<Double>temp = averaged_l.get(i);
			averaged_l.set(i, averaged_l.get(low_j));
			averaged_l.set(low_j, temp);
			String temp2 = clustered_names.get(i);
			clustered_names.set(i, clustered_names.get(low_j));
			clustered_names.set(low_j, temp2);
		}
		
		for(int i=1;i<averaged_l.size();i++)
		{
			for(int j=0;j<averaged_l.get(0).size();j++)
			{
				if((averaged_l.get(i).get(j)>averaged_l.get(i-1).get(j)) && (averaged_l.get(i).get(j-1)==0) && (averaged_l.get(i-1).get(j-1)==0))
				{
					Vector<Double>temp = averaged_l.get(i);
					averaged_l.set(i, averaged_l.get(i-1));
					averaged_l.set(i-1, temp);
					String temp2 = clustered_names.get(i);
					clustered_names.set(i, clustered_names.get(i-1));
					clustered_names.set(i-1, temp2);
					break;
				}
			}
		}
		
	}
	
	
	public ArrayList<String> get_names()
	{
		return clustered_names;
	}
	
	public ArrayList<Vector<Double>> get_averages()
	{
		return averaged_l;
	}
	
	public void print()
	{
		for(int i=0;i<clustered_names.size();i++)
		{
			System.out.println(clustered_names.get(i) + ": " + averaged_l.get(i));
		}
	}
	
	static void add_vectors(Vector<Double>v1, Vector<Double>v2)
	{
		for(int i=0;i<v1.size();i++)
		{
			double temp = 0;
			temp = temp + v1.get(i) + v2.get(i);
			v1.set(i, temp);
		}
	}
	
	public void trim()
	{
		for(int i=0;i<averaged_l.size();i++)
		{
			for(int j=1;j<averaged_l.get(i).size();j++)
			{
				if(averaged_l.get(i).get(0)>.1)
					break;
				double m = (averaged_l.get(i).get(j)-averaged_l.get(i).get(j-1))/
						(generations[j]-generations[j-1]); //slope
				if(m>0.00062)
				{
					//System.out.println("front " + j);
					for(int k=j-1;k>=0;k--)
					{
						averaged_l.get(i).set(k, 0.0);
					}
					break;
				}
			}
		}
		
		for(int i=0;i<averaged_l.size();i++)
		{
			for(int j=averaged_l.get(i).size()-1;j>=1;j--)
			{
				if(averaged_l.get(i).get(averaged_l.get(i).size()-1)>.9)
					break;
				double m = (averaged_l.get(i).get(j)-averaged_l.get(i).get(j-1))/
						(generations[j]-generations[j-1]); //slope
				
				if((m>-0.00062) && (m<0))
				{
					//System.out.println("end " + j);
					for(j=j-2;j<averaged_l.get(i).size();j++)
					{
						averaged_l.get(i).set(j,0.0);
					}
					break;
				}
			}
		}
		
		
	}
	
	public boolean same_background(int a, int b)
	{
		boolean r = false;
		if(fixed(a) && fixed(b))
			return true;
		else if(anti_correlated(a,b))
			return r;
		else
		{
			for(int i=0;i<averaged_l.get(a).size();i++)
			{
				if((averaged_l.get(a).get(i)+averaged_l.get(b).get(i))>1)
				{
					r=true;
					break;
				}
			}
			return r;
		}
	}
	
	public int my_background(int a)
	{
		int r=-1;
		for(int i=a-1;i>=0;i--)
		{
			if(same_background(a,i))
				return i;
		}
		return r;
	}
	
	public boolean extinct(int a)
	{
		boolean r = false;
		int i=0;
		for(i=0;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)>0)	//skip the first set of zeros, before the cluster arises
				break;
		}
		for(;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)==0)	//find the first frequency of zero
			{
				return true;
			}
		}
		return r;
	}
	
	public boolean anti_correlated(int a, int b)
	{
		boolean r = false;

		for(int i=1;i<averaged_l.get(a).size();i++)
		{
			if( ((averaged_l.get(a).get(i-1)>averaged_l.get(b).get(i-1)) && (averaged_l.get(a).get(i)<averaged_l.get(b).get(i))) || 
					((averaged_l.get(a).get(i-1)<averaged_l.get(b).get(i-1)) && (averaged_l.get(a).get(i)>averaged_l.get(b).get(i))) )
			{
				if(i==averaged_l.get(a).size()-1) //end behavior like in the case of BYS2-C06
				{
					r=true;
					break;	
				}
				if( ((averaged_l.get(a).get(i)>averaged_l.get(b).get(i)) && (averaged_l.get(a).get(i+1)<averaged_l.get(b).get(i+1))) || 
					((averaged_l.get(a).get(i)<averaged_l.get(b).get(i)) && (averaged_l.get(a).get(i+1)>averaged_l.get(b).get(i+1))) ) //accounts for situation like BYS1-A04
				{																														//where only one point crosses
					break;
				}
				else
				{
					r=true;
					break;
				}
			}
		}
		return r;
	}

	public boolean fixed(int a) //goes through the cluster at position "int a" and determines if the frequency is greater than 95% for at least three generations
	{
		if(a==-1)	//handle the case of no background
			return true;
		boolean r=false;
		int c = 0;
		for(int i=0;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)>.94)
			{
				c++;
			}
		}
		if(c==3)
			r=true;
		if(averaged_l.get(a).get(averaged_l.get(a).size()-1)>.90) //takes care of end behavior (if something fixes at the last generation)
			r=true;
		return r;
	}
	
	public double get_start(int a) //returns the generations at which the cluster beings to arise
	{							   //highly dependent on the behavior of remove_noise()
		double r=0;
		for(int i=0;i<averaged_l.get(a).size()-1;i++)
		{
			if(averaged_l.get(a).get(i)>0)
			{
				if(i==0)
				{
					double m = (averaged_l.get(a).get(0)-averaged_l.get(a).get(1))/
							(generations[0]-generations[1]); //slope
					double b = averaged_l.get(a).get(0)-(m*generations[0]); //y-intercept
					if(m>0)
					{
						r=-b/m;
						System.out.println(r);
						return r;
					}
					else if(m<0)
					{
						r=(1-b)/m;
						System.out.println(r);
						return r;
					}
					else{System.out.println("m is 0");}
				}
				else
				{
					double m = (averaged_l.get(a).get(i)-averaged_l.get(a).get(i+1))/
							(generations[i]-generations[i+1]); //slope
					double b = averaged_l.get(a).get(i)-(m*generations[i]); //y-intercept
					if(m>0)
					{
						r=-b/m;
						System.out.println(r);
						return r;
					}
					else if(m<0)
					{
						r=(1-b)/m;
						System.out.println(r);
						return r;
					}
					else{System.out.println("m is 0");}
				}
				break;
			}
		}
		System.out.println(r);
		return r;
	}
	
	public double get_start_h(int a)
	{
		double[]start_h = {116,138,160};
		
		if(get_start(a)>get_start(a-1))
			return s[a-1].bottom.getY1();
		else if(get_start(a)==get_start(a-1))
		{	
			if(bottom(a-1))
				return start_h[0];
			else
				return start_h[2];
		}
		else
			return start_h[1];
	}
	
	public double get_end_fixed(int a) //returns the generation at which a fixed cluster fixes
	{
		double r=0;
		for(int i=0;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)>.95)
			{
				r = generations[i];
				break;
			}
		}
		if(r==0) //if no frequency greater than .95 is found, the last generation is assumed to be point of fixation
			r = generations[generations.length-1];
		return r;
	}
	
	public double get_end_notfixed(int a) //returns the generation at which a cluster falls out
	{
		double r = 0;
		int i=0;
		for(i=0;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)>0)	//skip the first set of zeros, before the cluster arises
				break;
		}
		
		for(;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)==0)	//find the first frequency of zero
			{
				r = generations[i];
				break;
			}
		}
		if(r==0)	//the case in which a cluster hasn't fixed, but still has fallen out; tries to predict based on trajectory of last two frequencies
		{
			
			double m = (averaged_l.get(a).get(averaged_l.get(a).size()-1)-averaged_l.get(a).get(averaged_l.get(a).size()-2))/
														(generations[generations.length-1]-generations[generations.length-2]); //slope
			double b = averaged_l.get(a).get(averaged_l.get(a).size()-1)-(m*generations[generations.length-1]); //y-intercept
			if(m>0)
				r=(1-b)/m;
			if(m<0)
				r=-b/m;
		}
		if(r>1200)
		{
			r=1400;
		}
		return r;
	}
	
	public double max_dying(int a)
	{
		double r = 0;
		int i=0;
		for(i=0;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)>r)
			{
				r = averaged_l.get(a).get(i);
			}
		}
		return r;
	}
	
	public boolean two_populations()
	{
		boolean r = false;
		
		for(int i=0;i<averaged_l.size();i++)
		{
			for(int j=i+1;j<averaged_l.size();j++)
			{
				Vector<Double> v = new Vector<Double>();
				v.setSize(averaged_l.get(0).size());
				int c=0;
				for(int k=0;k<averaged_l.get(0).size();k++)
				{
					v.set(k, averaged_l.get(i).get(k)+averaged_l.get(j).get(k));
					if((v.get(k)>.95) && (v.get(k)<1.05))
					{
						if(!(averaged_l.get(i).get(k)>.90) || !(averaged_l.get(j).get(k)>.90))
						{
							if(!anti_correlated(i,j))
								c++;
						}
					}
				}
				if(c>4)
					return true;
			}
		}
		
		
		return r;
	}

	public boolean bottom(int a)
	{
		if(s[a].bottom.getY2()==276)
			return true;
		else
			return false;
	}
}
