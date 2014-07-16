/*
 * Hierarchical.java
 * Author: Thomas Tavolara
 * Not using this until further notice
 */

import java.util.ArrayList;
import java.util.Vector;

public class K_means 
{
	int k;
	int cur_k;
	ArrayList<String>names = new ArrayList<String>();
	ArrayList<Vector<Double>>l = new ArrayList<Vector<Double>>();
	ArrayList<Integer>clusters = new ArrayList<Integer>();
	
	public K_means(int k, double[][]d, String[]a)
	{
		this.k=k;
		for(int i=0;i<a.length;i++)
			names.add(i, a[i]);
		for(int i=0;i<d.length;i++)
		{
			Vector<Double> v = new Vector<Double>();
			for(int j=0;j<d[0].length;j++)
			{
				v.add(j, d[i][j]);
			}
			l.add(i, v);
			clusters.add(i, i);
		}		
		cur_k=0;
	}
	
	public void clusterdata()
	{
		//create random vectors as centroids
		ArrayList<Vector<Double>> centroids = new ArrayList<Vector<Double>>();
		for(int i=0;i<k;i++)
		{
			Vector<Double> v = new Vector<Double>();
			for(int j=0;j<Globals.NUM_GEN;j++)
			{
				v.add(Math.random());
			}
			centroids.add(v);
			
		}
		
		//randomly assign each gene to a centroid
		for(int i=0;i<clusters.size();i++)
		{
			clusters.set(i, (int)(centroids.size()*Math.random()));
		}
		System.out.println(clusters.toString());

		for(int i=0;i<l.size();i++)
		{
			double dis_ = EuclideanDistance(l.get(i), centroids.get(clusters.get(i)));
			for(int j=0;j<centroids.size();j++)
			{
				double dis = EuclideanDistance(l.get(i),centroids.get(j));
				if(dis_<dis)
				{
					dis=dis_;
					clusters.set(i, j);
				}
			}		
		}
		System.out.println(clusters.toString());
	}
	
	public double EuclideanDistance(Vector<Double>v1, Vector<Double>v2)
	{
		double d = 0;
		for(int i=0;i<v1.size();i++)
		{
			d = d + (v1.get(i)-v2.get(i))*(v1.get(i)-v2.get(i));
		}
		return Math.sqrt(d);
	}
	
	public double ManhattanDistance(Vector<Double>v1, Vector<Double>v2)
	{
		double d = 0;
		for(int i=0;i<v1.size();i++)
		{
			d = d + Math.abs(v1.get(i)-v2.get(i));
		}
		return d;
	}

	
	
}
