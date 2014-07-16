/*
 * Hierarchical.java
 * Author: Thomas Tavolara
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class driver
{

	static void write(Mutation[]input) throws IOException
	{
		BufferedWriter writer;
		File file = new File("data.txt");
	    file.createNewFile();
	    writer = new BufferedWriter(new FileWriter(file));
	    
	    writer.write("Population" + "\t" + "Gene" + "\t" + "0" + "\t" + "140" + "\t" + "240" + "\t" + "335" + "\t" + "415" + "\t" + "505" + "\t" + "585" + "\t" + "665"
	    + "\t" + "745" + "\t" + "825" + "\t" + "910" + "\t" + "1000" + "\n");
	    for(int i=0;i<input.length;i++)
		{
			writer.write(input[i].pop + "\t" + input[i].gene + "\t" + input[i].gen0 + "\t" + input[i].gen140 + "\t" + input[i].gen240 + "\t" + input[i].gen335 + "\t"
					 + input[i].gen415 + "\t" + input[i].gen505 + "\t" + input[i].gen585 + "\t" + input[i].gen665 + "\t" + input[i].gen745 + "\t" + input[i].gen825 
					 + "\t" + input[i].gen910 + "\t" + input[i].gen1000 + "\n");
		}
		writer.flush();
	    writer.close();
	}
	static Mutation[] read_mutations_old(String filename) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		int count=0;
		while ((line = br.readLine()) != null)
		{
			if(line.equals("\n"));
			else
			{
				count++;	
			}
			
		}
		br.close();

		
		Mutation[] r = new Mutation[count];
		BufferedReader br_r = new BufferedReader(new FileReader(filename));
		String line_r;
		int count_r=0;
		while ((line_r = br_r.readLine()) != null)
		{
			if(line_r.equals("\n"));
			else
			{
				String[]temp = line_r.split("\t");
				/*for(int i=11;i<23;i++)
				{
					if(temp[i].compareTo("")==0)
						System.out.println("Null @ line " + count_r + " and temp[" + i + "]");
				}*/
				r[count_r] = new Mutation();
				r[count_r].pop=temp[0];
				//r[count_r].chromosome=temp[1];
				//r[count_r].position=temp[2];
				//r[count_r].class_=temp[3];
				//r[count_r].mutation=temp[4];
				r[count_r].gene=temp[5];
				//r[count_r].aa=temp[6];
				//r[count_r].type=temp[7];
				//r[count_r].aa_change=temp[8];
				//r[count_r].nearest_downstream_gene=temp[9];
				//r[count_r].distance=temp[10];
				r[count_r].gen0=Double.parseDouble(temp[11]);
				r[count_r].gen140=Double.parseDouble(temp[12]);
				r[count_r].gen240=Double.parseDouble(temp[13]);
				r[count_r].gen335=Double.parseDouble(temp[14]);
				r[count_r].gen415=Double.parseDouble(temp[15]);
				r[count_r].gen505=Double.parseDouble(temp[16]);
				r[count_r].gen585=Double.parseDouble(temp[17]);
				if(temp[18].compareTo("")==0)
					r[count_r].gen665=(r[count_r-1].gen745 + r[count_r-1].gen665)/2;
				else
					r[count_r].gen665=Double.parseDouble(temp[18]);
				r[count_r].gen745=Double.parseDouble(temp[19]);
				r[count_r].gen825=Double.parseDouble(temp[20]);
				r[count_r].gen910=Double.parseDouble(temp[21]);
				r[count_r].gen1000=Double.parseDouble(temp[22]);
				count_r++;
			}		
		}
		br_r.close();
		return r;
	}
	static void determine_sig_old(Mutation[]a, double x)
	{
		for(int i=0;i<a.length;i++)
		{
			if((a[i].gen0>x)||(a[i].gen140>x)||(a[i].gen240>x)||(a[i].gen335>x)||(a[i].gen415>x)||(a[i].gen505>x)||
					(a[i].gen585>x)||(a[i].gen665>x)||(a[i].gen745>x)||(a[i].gen825>x)||(a[i].gen910>x)||(a[i].gen1000>x))
				a[i].sig=true;
		}
	}
	static String[] convertgenes4kmeans_old(Mutation[]a, String pop)
	{
		int c=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				c++;
			}
		}
		
		String[]r = new String[c];
		int b=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				r[b]=a[i].gene;
				b++;
			}
		}
		
		return r;
	}
	static double[][] convertnumbers4kmeans_old(Mutation[]a, String pop)
	{
		int c=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				c++;
			}
		}
		
		double[][]r = new double[c][12];
		int b=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				
				r[b][0]=a[i].gen0;
				r[b][1]=a[i].gen140;
				r[b][2]=a[i].gen240;
				r[b][3]=a[i].gen335;
				r[b][4]=a[i].gen415;
				r[b][5]=a[i].gen505;
				r[b][6]=a[i].gen585;
				r[b][7]=a[i].gen665;
				r[b][8]=a[i].gen745;
				r[b][9]=a[i].gen825;
				r[b][10]=a[i].gen910;
				r[b][11]=a[i].gen1000;
				b++;
			}
		}
		
		return r;
	}
	static void print_mutations_old(Mutation[]a)
	{
		for(int i=0;i<a.length;i++)
		{
			System.out.println(a[i].pop + "\t" + a[i].gene + "\t\t" + a[i].gen0 + "\t" + a[i].gen140 + "\t" + a[i].gen240 + "\t" + a[i].gen335 +
					"\t" + a[i].gen415 + "\t" + a[i].gen505 + "\t" + a[i].gen585 + "\t" + a[i].gen665 + "\t" + a[i].gen745 + "\t" + a[i].gen825 + 
					"\t" + a[i].gen910 + "\t" + a[i].gen1000);
		}
	}
	static void matlab_test_old(Mutation[]a)
	{
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo("BYB1-G07")==0) && a[i].sig)	
			System.out.println(a[i].gene + " " + a[i].gen0 + "," + a[i].gen140 + "," + a[i].gen240 + "," + a[i].gen335 +
					"," + a[i].gen415 + "," + a[i].gen505 + "," + a[i].gen585 + "," + a[i].gen665 + "," + a[i].gen745 + "," + a[i].gen825 + 
					"," + a[i].gen910 + "," + a[i].gen1000);
		}
	}

	static Mutation2[] read_mutations(String filename) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		int count=0;
		while ((line = br.readLine()) != null)
		{
			count++;	
		}
		br.close();
		
		Mutation2[] r = new Mutation2[count];
		BufferedReader br_r = new BufferedReader(new FileReader(filename));
		String line_r;
		int count_r = 0;
		
		while ((line_r = br_r.readLine()) != null)
		{
			r[count_r] = new Mutation2();
			String[]temp = line_r.split("\t");
			r[count_r].pop=temp[0];
			r[count_r].gene=temp[1];
			for(int i=2;i<temp.length;i++)
			{
				r[count_r].freq.add(Double.parseDouble(temp[i]));
			}
			count_r++;
		}
		br_r.close();
		
		return r;
	}
	
	static void print_mutations(Mutation2[]a)
	{
		for(int i=0;i<a.length;i++)
		{
			System.out.println(a[i].pop + "\t" + a[i].gene + "\t" + a[i].freq.toString());
		}
	}
	
	static void print_specific(double[]generations, String[]genes, double[][]freq)
	{
		System.out.print("Population\t" + "Gene\t");
		for(int i=0;i<generations.length;i++)
		{
			System.out.print(generations[i] + "\t");
		}
		System.out.println();
		for(int i=0;i<genes.length;i++)
		{
			System.out.print("BYB1-BO1\t" + genes[i] + "\t");
			for(int j=0;j<freq[i].length;j++)
			{
				System.out.print(freq[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	static void determine_sig(Mutation2[]a, double above, int num_gen) //changes the .sig of a mutation to true if its frequency is greater than above for num_gen generations
	{
		for(int i=1;i<a.length;i++) //start at 1 to pass pop, gene, and generations
		{
			for(int j=num_gen;j<a[i].freq.size();j++) //assumes that the total number of generations is greater than num_gen
			{
				boolean temp = true;
				for(int k=j;k>j-num_gen;k--)
				{
					//System.out.println("i=" + i + "\tj=" + j + "\t k=" +k);
					if(a[i].freq.get(k)<above)
					{
						temp = false;
						break;
					}
				}
				if(temp)
				{
					a[i].sig=true;
					break;
				}
			}
		}
	}
	
	static double[] get_gen(Mutation2[]a)
	{
		double[]r = new double[a[0].freq.size()];
		for(int i=0;i<r.length;i++)
			r[i] = a[0].freq.get(i);
		return r;
	}
	
	static String[] get_genes(Mutation2[]a, String pop)
	{
		int c=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				c++;
			}
		}
		
		String[]r = new String[c];
		int b=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				r[b]=a[i].gene;
				b++;
			}
		}
		
		return r;
	}
	
	static double[][] get_freq(Mutation2[]a, String pop)
	{
		int c=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				c++;
			}
		}
		
		double[][]r = new double[c][a[0].freq.size()];
		int b=0;
		for(int i=0;i<a.length;i++)
		{
			if((a[i].pop.compareTo(pop)==0) && a[i].sig)
			{
				for(int j=0;j<a[i].freq.size();j++)
					r[b][j]=a[i].freq.get(j);

				b++;
			}
		}
		
		return r;
	}
	
	
	public static void main(String[] args) throws IOException
	{
        //new GUI();
        String filename = args[0];
        double f1 = Double.parseDouble(args[1]);
        double g1 = Integer.parseInt(args[2]);
        double f2 = Double.parseDouble(args[3]);
        double g2 = Integer.parseInt(args[4]);
        boolean dist = Boolean.parseBoolean(args[5]);
        int link = Integer.parseInt(args[6]);
        String clustering = args[7];


        //for(int i=0;i<aa.length;i++)
        
                Mutation2[] input = read_mutations(args[0]);
                String pop = args[9];
                //print_mutations(input);
                determine_sig(input, Double.parseDouble(args[1]), Integer.parseInt(args[2]));
                determine_sig(input, Double.parseDouble(args[3]), Integer.parseInt(args[4]));
                double[]generations = get_gen(input);
                String[]genes = get_genes(input,pop);
                double[][]freq = get_freq(input,pop);
                //print_specific(generations, genes, freq);

                if(args[7].equals("specify"))
                {
                        Hierarchical_c k = new Hierarchical_c(Integer.parseInt(args[8]), freq, genes);
                        k.clusterdata(Integer.parseInt(args[6]),Boolean.parseBoolean(args[5]));

                        Determiner d = new Determiner(k.names, k.clusters, k.l, generations);
                        k.print();
                        d.print();
                        new Erol(d, generations);
                }
                else
                {
                        Hierarchical k = new Hierarchical(Double.parseDouble(args[8]), freq, genes);

                        k.clusterdata(Integer.parseInt(args[6]),Boolean.parseBoolean(args[5]));

                        Determiner d = new Determiner(k.names, k.clusters, k.l, generations);
                        k.print();
                        d.print();
                        new Erol(d, generations);
                }
        

			
	}
	
}
