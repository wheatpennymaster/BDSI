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
		remove_noise();
		reorder();
		reorder();
		reorder();
	}
	
	public StuffToDraw[] getStuffToDraw()
	{
		return s;
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
	
	public void remove_noise()
	{
		for(int i=0;i<averaged_l.size();i++)
		{
			for(int j=0;j<averaged_l.get(i).size();j++)
			{
				if(averaged_l.get(i).get(j)<.05)
					averaged_l.get(i).set(j, 0.0);
			}
			
			
			//may cause some issues
			int j=0;
			for(j=0;i<averaged_l.get(i).size();j++)
			{
				if(averaged_l.get(i).get(j)>0)	//skip the first set of zeros, before the cluster arises
					break;
			}
			
			for(;j<averaged_l.get(i).size();j++)
			{
				if(averaged_l.get(i).get(j)==0)	//find the first frequency of zero
				{
					break;
				}
			}
			for(;j<averaged_l.get(i).size();j++)
			{
				averaged_l.get(i).set(j, 0.0);
			}
		}
		
		
	}
	
	public boolean same_background(int a, int b)
	{
		boolean r = false;
		if(anti_correlated(a,b))
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
		boolean r=false;
		int c = 0;
		for(int i=0;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)>.95)
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
		for(int i=0;i<averaged_l.get(a).size();i++)
		{
			if(averaged_l.get(a).get(i)>0)
			{
				if(i==0)
					r=generations[0];
				else
					r=generations[i-1];
				break;
			}
		}
		return r;
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
}
