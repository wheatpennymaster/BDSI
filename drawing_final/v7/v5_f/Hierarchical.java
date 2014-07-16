/*
 * Hierarchical.java
 * Author: Thomas Tavolara
 * Description: Given a set of vectors and number of clusters, this class will cluster them using hierarchical clustering
 * Usage: Using the constructor, build a new Hierarchical object and call clustersdata() on it
 */



import java.util.ArrayList;
import java.util.Vector;

public class Hierarchical
{
	double k;
	int cur_k;
	ArrayList<String>names = new ArrayList<String>();
	ArrayList<Vector<Double>>l = new ArrayList<Vector<Double>>();
	ArrayList<Integer>clusters = new ArrayList<Integer>();
	
	public Hierarchical(double k, double[][]d, String[]a)
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
	
	public void clusterdata(int linkage, boolean distance)	//linkage=0 for single linkage; linkage=1 for complete linkage; linkage=2 for average linkage
	{														//false for Euclidean distance; true for Manhattan distance
		//test cluster
		int low_j = -1;
		int low_i = -1;
		
		if(linkage==0)
		{
			double loki = Double.MAX_VALUE;
			double t = 0.0;
			//while(cur_k<clusters.size()-k)
			while(t<k)
			{
				for(int i=0;i<l.size();i++)
				{
					for(int j=i;j<l.size();j++)
					{
						if((j!=i) && (clusters.get(i)!=clusters.get(j)))
						{
							//System.out.println("i=" + i + "   j=" + j + "\n");
							double temp = single_linkage(i,j,distance);
							if(temp<loki)
							{
								low_i=i;
								low_j=j;
								loki=temp;
							}
						}
					}
				}

				for(int i=0;i<clusters.size();i++)
				{
					if(clusters.get(i)==low_j)
						clusters.set(i, low_i);
					//System.out.println("i=" + i + "   low_j=" + low_j + "   low_i=" + low_i);
				}
				clusters.set(low_j, clusters.get(low_i));
				//System.out.println(clusters.toString());
				//System.out.println("v1: " + names.get(low_i) + l.get(low_i) + "\nv2: " + names.get(low_j) + l.get(low_j) + "\n Euclidean Distance: " + loki);
				t=loki;
				loki = Double.MAX_VALUE;
				cur_k++;
			}
		}
		
		if(linkage==1)
		{
			double loki = 0;
			while(cur_k<9)
			{
				for(int i=0;i<l.size();i++)
				{
					for(int j=i;j<l.size();j++)
					{
						if((j!=i) && (clusters.get(i)!=clusters.get(j)))
						{
							//System.out.println("i=" + i + "   j=" + j + "\n");
							double temp = complete_linkage(i,j,distance);
							if(temp>loki)
							{
								low_i=i;
								low_j=j;
								loki=temp;
							}
						}
					}
				}
				
				for(int i=0;i<clusters.size();i++)
				{
					if(clusters.get(i)==low_j)
						clusters.set(i, low_i);
					//System.out.println("i=" + i + "   low_j=" + low_j + "   low_i=" + low_i);
				}
				clusters.set(low_j, clusters.get(low_i));
				//System.out.println(clusters.toString());
				//System.out.println("v1: " + names.get(low_i) + l.get(low_i) + "\nv2: " + names.get(low_j) + l.get(low_j) + "\n Euclidean Distance: " + loki);
				loki = 0;
				cur_k++;
			}
		}
		
		if(linkage==2)
		{
			double loki = Double.MAX_VALUE;
			while(cur_k<9)
			{
				for(int i=0;i<l.size();i++)
				{
					for(int j=i;j<l.size();j++)
					{
						if((j!=i) && (clusters.get(i)!=clusters.get(j)))
						{
							//System.out.println("i=" + i + "   j=" + j + "\n");
							double temp = average_linkage(i,j,distance);
							if(temp<loki)
							{
								low_i=i;
								low_j=j;
								loki=temp;
							}
						}
					}
				}
				
				for(int i=0;i<clusters.size();i++)
				{
					if(clusters.get(i)==low_j)
						clusters.set(i, low_i);
					//System.out.println("i=" + i + "   low_j=" + low_j + "   low_i=" + low_i);
				}
				clusters.set(low_j, clusters.get(low_i));
				//System.out.println(clusters.toString());
				//System.out.println("v1: " + names.get(low_i) + l.get(low_i) + "\nv2: " + names.get(low_j) + l.get(low_j) + "\n Euclidean Distance: " + loki);
				loki = Double.MAX_VALUE;
				cur_k++;
			}
		}
	}
	
	public void print()
	{
		System.out.println(names.toString());
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
	
	//runtime can be improved
	public double single_linkage(int c1, int c2, boolean d) //boolean is for distance measure (Euclidean is 0, Manhattan is 1)
	{
		double min=Double.MAX_VALUE;
		
		if(!d) //Euclidean
		{
			for(int i=0;i<clusters.size();i++)
			{
				for(int j=i;j<clusters.size();j++)
				{
					if((j!=i) && (clusters.get(i)!=clusters.get(j)) && (clusters.get(i)==c1) && clusters.get(j)==c2)
					{
						double temp = EuclideanDistance(l.get(i),l.get(j));
						if(temp<min)
							min=temp;
					}
				}
			}
		}
		
		if(d) //Manhattan
		{
			for(int i=0;i<clusters.size();i++)
			{
				for(int j=i;j<clusters.size();j++)
				{
					if((j!=i) && (clusters.get(i)!=clusters.get(j)) && (clusters.get(i)==c1) && clusters.get(j)==c2)
					{
						double temp = ManhattanDistance(l.get(i),l.get(j));
						if(temp<min)
							min=temp;
					}
				}
			}
		}
		
		/*for(int i=0;i<l.size();i++)
		{
			for(int j=i;j<l.size();j++)
			{
				if(clusters.get(i)!=clusters.get(j)) //don't compare to members of your own cluster
				{
					if(!d) //Euclidean
					{
						double temp = EuclideanDistance(l.get(i),l.get(j));
						if(temp<min)
							min=temp;
					}
					else //Manhattan
					{
						double temp = ManhattanDistance(l.get(i),l.get(j));
						if(temp<min)
							min=temp;						
					}
					
					for(int k=0;k<l.get(i).size();k++)
					{
						for(int m=k;m<l.get(j).size();m++)
						{
							double temp = Math.abs(l.get(i).get(k)-l.get(j).get(m));
							if(temp<min)
							{
								min = Math.abs(l.get(i).get(k)-l.get(j).get(m));	
							}	
						}
					}
					
				}	
			}
		}*/
		return min;
	}
	
	//runtime can be improved
	public double complete_linkage(int c1, int c2, boolean d) //boolean is for distance measure (Euclidean is 0, Manhattan is 1)
	{
		double max=0;
		
		if(!d) //Euclidean
		{
			for(int i=0;i<clusters.size();i++)
			{
				for(int j=i;j<clusters.size();j++)
				{
					if((j!=i) && (clusters.get(i)!=clusters.get(j)) && (clusters.get(i)==c1) && clusters.get(j)==c2)
					{
						double temp = EuclideanDistance(l.get(i),l.get(j));
						if(temp>max)
							max=temp;
					}
				}
			}
		}
		
		if(d) //Manhattan
		{
			for(int i=0;i<clusters.size();i++)
			{
				for(int j=i;j<clusters.size();j++)
				{
					if((j!=i) && (clusters.get(i)!=clusters.get(j)) && (clusters.get(i)==c1) && clusters.get(j)==c2)
					{
						double temp = ManhattanDistance(l.get(i),l.get(j));
						if(temp>max)
							max=temp;
					}
				}
			}
		}
		/*for(int i=0;i<l.size();i++)
		{
			for(int j=i;j<l.size();j++)
			{
				if(clusters.get(i)!=clusters.get(j)) //don't compare to members of your own cluster
				{	
					if(!d) //Euclidean
					{
						double temp = EuclideanDistance(l.get(i),l.get(j));
						if(temp>max)
							max=temp;
					}
					else //Manhattan
					{
						double temp = ManhattanDistance(l.get(i),l.get(j));
						if(temp<max)
							max=temp;						
					}
					
					for(int k=0;k<l.get(i).size();k++)
					{
						for(int m=k;m<l.get(j).size();m++)
						{
							double temp = Math.abs(l.get(i).get(k)-l.get(j).get(m));
							if(temp>max)
							{
								max = Math.abs(l.get(i).get(k)-l.get(j).get(m));	
							}	
						}
					}
					
				}	
			}
		}*/
		return max;
	}
	
	//runtime can be improved
	public double average_linkage(int c1, int c2, boolean d)
	{
		double total=0;
		int comparisons = 0;
		
		if(!d) //Euclidean
		{
			for(int i=0;i<clusters.size();i++)
			{
				for(int j=i;j<clusters.size();j++)
				{
					if((j!=i) && (clusters.get(i)!=clusters.get(j)) && (clusters.get(i)==c1) && clusters.get(j)==c2)
					{
						double temp = EuclideanDistance(l.get(i),l.get(j));
						total = total + temp;
						comparisons++;
					}
				}
			}
		}
		
		if(d) //Manhattan
		{
			for(int i=0;i<clusters.size();i++)
			{
				for(int j=i;j<clusters.size();j++)
				{
					if((j!=i) && (clusters.get(i)!=clusters.get(j)) && (clusters.get(i)==c1) && clusters.get(j)==c2)
					{
						double temp = ManhattanDistance(l.get(i),l.get(j));
						total = total + temp;
						comparisons++;
					}
				}
			}
		}
		/*
		for(int i=0;i<l.size();i++)
		{
			for(int j=i;j<l.size();j++)
			{
				if(clusters.get(i)!=clusters.get(j)) //don't compare to members of your own cluster
				{	
					if(!d) //Euclidean
					{
						double temp = EuclideanDistance(l.get(i),l.get(j));
						total = total + temp;
						comparisons++;
					}
					else //Manhattan
					{
						double temp = EuclideanDistance(l.get(i),l.get(j));
						total = total + temp;
						comparisons++;
					}
					
					for(int k=0;k<l.get(i).size();k++)
					{
						for(int m=k;m<l.get(j).size();m++)
						{
							double temp = Math.abs(l.get(i).get(k)-l.get(j).get(m));

							total = total + temp;	
						}
					}
					
				}	
			}
		}*/
		return total/comparisons;
	}
	
	
}
