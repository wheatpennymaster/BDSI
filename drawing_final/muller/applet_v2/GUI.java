
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JApplet implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color[] colors = {Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA};
	ArrayList<Double>sig = new ArrayList<Double>();
	int cluseters;
	String mutation;
	
	JButton submit = new JButton("Submit");
	JButton open = new JButton("Open");
	JTextArea data_t = new JTextArea();
	JRadioButton filter_default = new JRadioButton("Default (.2 freq for 4 generations, .5 freq for 1 generations)");
	JRadioButton filter_custom = new JRadioButton("Custom");
	JTextField freq1 = new JTextField("Freq1");
	JTextField gen1 = new JTextField("Gen1");
	JTextField freq2 = new JTextField("Freq2");
	JTextField gen2 = new JTextField("Gen2");
	JRadioButton euclid = new JRadioButton("Euclidean");
	JRadioButton manhattan = new JRadioButton("Manhattan");
	JRadioButton single = new JRadioButton("Single");
	JRadioButton complete = new JRadioButton("Complete");
	JRadioButton average = new JRadioButton("Average");
	JRadioButton spec_cluster = new JRadioButton("Specify num. clusters");
	JRadioButton tolerate_dist = new JRadioButton("Tolerate distance");
	JTextField num_clusters = new JTextField("#clusters or tolerated distance");
	JTextField pop_name = new JTextField();
	
	public GUI()
	{
	
	GridLayout super_main = new GridLayout(2,1);
	this.setLayout(super_main);
	JPanel top = new JPanel();
	top.setLayout(new GridLayout(11,1));
	JPanel bottom = new JPanel();
		
		JLabel label1 = new JLabel("  Filtering Options:");
		ButtonGroup filtering = new ButtonGroup();
		
		filtering.add(filter_default);
		filtering.add(filter_custom);
		top.add(label1);
		top.add(filter_default);
			JPanel filter_c = new JPanel();
			filter_c.setLayout(new GridLayout(1,6));
			
			freq1.setPreferredSize(new Dimension(14,14));
			
			gen1.setPreferredSize(new Dimension(14,14));
			
			freq2.setPreferredSize(new Dimension(14,14));
			
			gen2.setPreferredSize(new Dimension(14,14));
			filter_c.add(filter_custom);
			filter_c.add(freq1);
			filter_c.add(gen1);
			filter_c.add(freq2);
			filter_c.add(gen2);
			top.add(filter_c);
		top.add(new JPanel());
		
		JLabel cluster_label = new JLabel("  Clustering Options:");
		top.add(cluster_label);
			JPanel dist_m = new JPanel();
			dist_m.setLayout(new GridLayout(1,3));
			JLabel dist_lab = new JLabel("    Distance measure");
			ButtonGroup distances = new ButtonGroup();
			
			distances.add(euclid);
			distances.add(manhattan);
			dist_m.add(dist_lab);
			dist_m.add(euclid);
			dist_m.add(manhattan);
			
			JPanel linkage = new JPanel();
			linkage.setLayout(new GridLayout(1,4));
			JLabel linkage_l = new JLabel("    Linkage");
			ButtonGroup linkage_g = new ButtonGroup();
			
			linkage_g.add(single);
			linkage_g.add(complete);
			linkage_g.add(average);
			linkage.add(linkage_l);
			linkage.add(single);
			linkage.add(complete);
			linkage.add(average);
			
			JPanel clustering = new JPanel();
			clustering.setLayout(new GridLayout(1,4));
			JLabel cluster_l = new JLabel("    Clustering?");
			ButtonGroup cluster_g = new ButtonGroup();
			
			cluster_g.add(spec_cluster);
			cluster_g.add(tolerate_dist);
			
			num_clusters.setPreferredSize(new Dimension(14,14));
			clustering.add(cluster_l);
			clustering.add(spec_cluster);
			clustering.add(tolerate_dist);
			clustering.add(num_clusters);
		top.add(dist_m);
		top.add(linkage);
		top.add(clustering);
			
		top.add(new JPanel());
		
		JLabel pop_name_ = new JLabel("  Please specify population name:");
		JPanel pop_name_panel = new JPanel();
		pop_name_panel.setLayout(new GridLayout(1,2));
		pop_name_panel.add(pop_name_);
		pop_name_panel.add(pop_name);
		
		top.add(pop_name_panel);
		top.add(new JPanel());
		
		
		bottom.setLayout(new BorderLayout());
		bottom.add(new JPanel(),BorderLayout.EAST);
		bottom.add(new JPanel(),BorderLayout.WEST);
		JLabel data = new JLabel("  Data:");
		
		data_t.setLineWrap(true);
		data_t.setSize(200,400);
		data_t.setBorder(BorderFactory.createLineBorder(Color.black));
		bottom.add(data, BorderLayout.NORTH);
		bottom.add(data_t, BorderLayout.CENTER);
		

		bottom.add(submit, BorderLayout.SOUTH);
		submit.addActionListener(this);
		
		
	this.add(top);
	this.add(bottom);
		
		
		
		
		this.setVisible(true);
		this.setBackground(Color.white);
	
	}
	
	String input_string;
	boolean dist;
	int linkage;
	double f1=.2;
	int g1 = 3;
	double f2=.5;
	int g2=1;
	int clusters;
	double tolerated_distance;
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==submit)
		{
			for(int i=0;i<1;i++)
			{	
				if(filter_default.isSelected())
				{
					//nothing?
				}
				else if(filter_custom.isSelected())
				{
					try
					{
						f1 = Double.parseDouble(freq1.getText());
						g1 = Integer.parseInt(gen1.getText());
						f2 = Double.parseDouble(freq2.getText());
						g2 = Integer.parseInt(gen2.getText());
					}
					catch(NumberFormatException nfe)
					{
						data_t.setText("Please enter valid input for custom filtering");
						break;
					}
				}
				else
				{
					data_t.setText("Please specify filter");
					break;
				}
				
				if(euclid.isSelected())
					dist=false;
				else if(manhattan.isSelected())
					dist=true;
				else
				{
					data_t.setText("Please select distance");
					break;
				}
				
				if(single.isSelected())
					linkage=0;
				else if(complete.isSelected())
					linkage=1;
				else if(average.isSelected())
					linkage=2;
				else
				{
					data_t.setText("Please choose linkage");
					break;
				}
				
				Mutation2[]input;
				if(data_t.getText()==null)
				{
					data_t.setText("Please add data");
					break;
				}
				else
				{
					try
					{
						input = read_mutations(data_t.getText());
					}
					catch (IOException e1)
					{
						data_t.setText("Error with input data. Please try again.");
						break;
					}
				}
				
				if(pop_name.getText()==null)
				{
					data_t.setText("Please specify population name");
					break;
				}
				else
				{
					input_string = pop_name.getText();
				}
				
				if(spec_cluster.isSelected())
				{
					try
					{
						clusters=Integer.parseInt(num_clusters.getText());
					}
					catch(NumberFormatException nfe)
					{
						data_t.setText("Please enter valid input for number of clusters");
						break;
					}
					
					driver.determine_sig(input, f1, g1);
					driver.determine_sig(input, f2, g2);
					double[]generations = driver.get_gen(input);
					String[]genes = driver.get_genes(input,input_string);
					double[][]freq = driver.get_freq(input,input_string);
					
					Hierarchical_c k = new Hierarchical_c(clusters, freq, genes);
					k.clusterdata(linkage,dist);
					
					Determiner d = new Determiner(k.names, k.clusters, k.l, generations);
					k.print();
					d.print();
					
					DrawStuff dd = new DrawStuff(d, generations);

					BufferedImage bi = new BufferedImage((int)generations[generations.length-1]+50, 320, BufferedImage.TYPE_INT_ARGB); 
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
					    catch (IOException e1)
					    {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							
						}
					}
				}
				else if(tolerate_dist.isSelected())
				{
					try
					{
						tolerated_distance=Double.parseDouble(num_clusters.getText());
					}
					catch(NumberFormatException nfe)
					{
						data_t.setText("Please enter valid input for maximum tolerated distance");
						break;
					}
					driver.determine_sig(input, f1, g1);
					driver.determine_sig(input, f2, g2);
					double[]generations = driver.get_gen(input);
					String[]genes = driver.get_genes(input,input_string);
					double[][]freq = driver.get_freq(input,input_string);
					
					Hierarchical k = new Hierarchical(tolerated_distance, freq, genes);
					k.clusterdata(linkage,dist);
					
					Determiner d = new Determiner(k.names, k.clusters, k.l, generations);
					k.print();
					d.print();
					
					DrawStuff dd = new DrawStuff(d, generations);

					BufferedImage bi = new BufferedImage((int)generations[generations.length-1]+50, 320, BufferedImage.TYPE_INT_ARGB); 
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
					    catch (IOException e1)
					    {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							
						}
					}
				}
				else
				{
					data_t.setText("Please choose cluster options");
					break;
				}
			}
		}
	}
	
	public Mutation2[] read_mutations(String a) throws IOException
	{
		InputStream is = new ByteArrayInputStream(a.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		int count=0;
		while ((line = br.readLine()) != null)
		{
			count++;
		}
		br.close();
		
		Mutation2[] r = new Mutation2[count];
		InputStream ist = new ByteArrayInputStream(a.getBytes());
		BufferedReader br_r = new BufferedReader(new InputStreamReader(ist));
		String line_r;
		int count_r = 0;
		
		try
		{
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
		}
		catch(NumberFormatException nfe)
		{
			data_t.setText("Error with input data. Probably a non-double number.");
		}
		catch(ArrayIndexOutOfBoundsException out)
		{
			data_t.setText("Error with input data. Probably inconsistent number of generations.");
		}
		br_r.close();
		return r;
	}
	
	public void print_stuff()
	{
		System.out.println("Filter default: " + filter_default.isSelected() + "\n" + 
				"Filter custom: " +	filter_custom.isSelected() + "\n" + 
				"Freq1: " +			freq1.getText() + "\n" + 
				"Gen1: " +			gen1.getText() + "\n" +
				"Freq2: " +			freq2.getText() + "\n" + 
				"Gen2: "	+		gen2.getText() + "\n" + 
				"Euclid: " + 		euclid.isSelected() + "\n" + 
				"Manhattan: " + 	manhattan.isSelected() + "\n" + 
				"Single: " +		single.isSelected() + "\n" + 
				"Complete: " + 		complete.isSelected() + "\n" + 
				"Average: " + 		average.isSelected() + "\n" + 
				"Specify cluster: " + spec_cluster.isSelected() + "\n" + 
				"Tolerate: " + 		tolerate_dist.isSelected() + "\n" + 
				"Cluster num: " + 	num_clusters.getText()  + "\n" + 
				"Data: " +			data_t.getText());
	}
	
	public ArrayList<Double> getSig()
	{
		return sig;
	}
}
