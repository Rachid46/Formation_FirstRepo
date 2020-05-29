package Views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import Model.BBO_OptimizationFactory;
import Model.CarPool;
import Model.Optimization;
import Model.OptimizationFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JList;

public class CarPoolingWin {

	private JFrame frame;
	private JTextField IterNumber;
	private JTextField bbo_PopSize;
	private JTextField mutationRate;
	private JTextField nbofelite;
	private final Action action = new SwingAction();
	private JTextField tFieldFPath;
	
	private String source;
	private JTextField tFRunNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CarPoolingWin window = new CarPoolingWin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CarPoolingWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 513, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel BBO = new JPanel();
		tabbedPane.addTab("BBO", null, BBO, null);
		
		JLabel lblNewLabel = new JLabel("Population size");
		BBO.add(lblNewLabel);
		
		bbo_PopSize = new JTextField();
		BBO.add(bbo_PopSize);
		bbo_PopSize.setColumns(10);
		
		JLabel lblMutRate = new JLabel("Mutation rate");
		lblMutRate.setVerticalAlignment(SwingConstants.TOP);
		BBO.add(lblMutRate);
		
		mutationRate = new JTextField();
		BBO.add(mutationRate);
		mutationRate.setColumns(10);
		
		JLabel lblNumberOfElite = new JLabel("Number of elite");
		BBO.add(lblNumberOfElite);
		
		nbofelite = new JTextField();
		BBO.add(nbofelite);
		nbofelite.setColumns(10);
		
		JButton btnCompute = new JButton("Start");
		btnCompute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int nbRun = Integer.parseInt(tFRunNumber.getText());
				
				//Result's file
				////////////////////////////
				
				JFileChooser dialogue = new JFileChooser(new File("."));
				PrintWriter sortie=null;
				File fichier;
				
				if (dialogue.showOpenDialog(null)== 
				    JFileChooser.APPROVE_OPTION) {
				    fichier = dialogue.getSelectedFile();
				    try {
						sortie = new PrintWriter
						(new FileWriter(fichier.getPath(), true));
						sortie.println(source);
						//sortie.println(arg[0]);
						//tFieldFPath.setText(fichier.getPath());
						//source = new String(tFieldFPath.getText());
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
				////////////////////////////
				
				double bestSol=10000.0;
				double worstSol=0.0;
				double avgSol=0.0;
				double meanTime=0.0;
				for(int i=0;i<nbRun;i++)
				{
					//Start time
					long duration = System.currentTimeMillis();
					
					CarPool carPool = new CarPool();
				
					OptimizationFactory optfact1 = new BBO_OptimizationFactory();
					//OptimizationFactory optfact2 = new GA_OptimizationFactory();
					
					//String source;
							
					Optimization opt1 = optfact1.getOptimization(); //new BBO_Optimization();
					//Optimization opt2 = optfact2.getOptimization();
					
					//opt.source = "D:\\Dossier_Doctorat_2018\\Mon_Travail\\Problematiques\\Covoiturage_Regulier\\Benchmark_Yuhan_Gup_2012\\Extract\\C12_RKA.txt";
					//String source = new String("E:\\Dossier_Doctorat_2018\\Mon_Travail\\Problematiques\\Covoiturage_Regulier\\Benchmark_Yuhan_Guo_2012\\Extract\\R103.txt");
					carPool.lecture(source);
					//new BufferedReader("D:\\Dossier_Doctorat_2018\\Mon_Travail\\Problematiques\\Covoiturage_Regulier\\Benchmark_Yuhan_Gup_2012\\Extract\\C12_RKA.txt");
									
					//
				    carPool.initOpt();
				    
				   //Optimize according to the chosen meta-heuristics
							
					opt1.treatment();
					
					// End treatment
					duration = System.currentTimeMillis() - duration;
					
					//get best solution
					//sortie.write(String.valueOf(opt1.getBestSol()));
					//sortie.write(" iteration : " + (i+1));
					bestSol = Math.min(bestSol, opt1.getBestSol());
					worstSol = Math.max(worstSol, opt1.getBestSol());
					avgSol += opt1.getBestSol();
					meanTime += duration/1000.0;
					sortie.println("Duration : "  + duration/1000.0);
					sortie.println(opt1.getBestSol());
					
				}
				
				//exploit data
				sortie.println("Global best Solution : " + bestSol);
				sortie.println("Global average Solution : " + avgSol/nbRun);
				sortie.println("Global worst Solution : " + worstSol);
				sortie.println("Global mean time : " + meanTime/nbRun);
				sortie.close();
				
				//for(int i=0;i<Utilisateurs.getAuthorizedUsers()[0].length;i++)
				//{
				//	System.out.println("Authorized tableau[" + i + "] :" + Arrays.toString(Utilisateurs.getAuthorizedUsers()[i]));
				//}
			}
		});
		
		BBO.add(btnCompute);
		
		JPanel GA = new JPanel();
		tabbedPane.addTab("GA", null, GA, null);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblIterationNumber = new JLabel("Iteration number");
		panel.add(lblIterationNumber);
		
		IterNumber = new JTextField();
		IterNumber.setToolTipText("");
		panel.add(IterNumber);
		IterNumber.setColumns(10);
		
		JButton btnNewButton = new JButton("Graphic");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					GraphicalCard dialog = new GraphicalCard();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);
		
		JButton btnSelectFile = new JButton("Select file");
		btnSelectFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser dialogue = new JFileChooser(new File("."));
				PrintWriter sortie;
				File fichier;
				
				if (dialogue.showOpenDialog(null)== 
				    JFileChooser.APPROVE_OPTION) {
				    fichier = dialogue.getSelectedFile();
				    try {
						sortie = new PrintWriter
						(new FileWriter(fichier.getPath(), true));
						//sortie.println(arg[0]);
						tFieldFPath.setText(fichier.getPath());
						source = new String(tFieldFPath.getText());
						sortie.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			}
		});
		panel.add(btnSelectFile);
		
		tFieldFPath = new JTextField();
		panel.add(tFieldFPath);
		tFieldFPath.setColumns(10);
		
		JLabel lblRunNumber = new JLabel("Run number");
		panel.add(lblRunNumber);
		
		tFRunNumber = new JTextField();
		panel.add(tFRunNumber);
		tFRunNumber.setColumns(10);
		
		JList listBatch = new JList();
		panel.add(listBatch);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}
}
