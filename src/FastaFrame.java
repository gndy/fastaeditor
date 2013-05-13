import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.*;



public class FastaFrame extends JFrame implements ItemListener {
	/**
	 * @author wenlei
	 * a small tool to extract CDS information from Genbank file and save as fasta format
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jp1;
	private JButton jb1,jb2,jb3;
	private JLabel jLabel1;
	private JTextField jTextField1,jTextField2;
	private JRadioButton jr1,jr2;
	
	private File filein,fileout;
	private Boolean DNA;
	
	public FastaFrame(){
		super("FastaEditer");
		this.setBounds(300,100, 800, 500);
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints  = new GridBagConstraints();
		this.setLayout(gridbag);
				
				
		constraints.weightx = 0.0;
		constraints.weighty = 0.2;
		jLabel1 = new JLabel("GenBank File");
		gridbag.setConstraints(jLabel1, constraints);
		this.add(jLabel1);
		
		
		constraints.fill =GridBagConstraints.HORIZONTAL;//input file textfield
		constraints.weightx = 1;
		jTextField1 = new JTextField();
		gridbag.setConstraints(jTextField1, constraints);
		this.add(jTextField1);
		
		
		constraints.weightx =0;//input file button
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		jb1 = new JButton("input");
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooserin = new JFileChooser();
				chooserin.setFileFilter(new GbFilter());
				int retval = chooserin.showOpenDialog(null);
				if(retval==JFileChooser.APPROVE_OPTION){
				filein = chooserin.getSelectedFile();
				jTextField1.setText(filein.getAbsolutePath());
				}
			}
		});
		gridbag.setConstraints(jb1, constraints);
		this.add(jb1);
		
		
		constraints.weightx = 0;//jradiobutton DNA or Protein
		constraints.gridwidth =1;
		jp1 = new JPanel();
		jr1 = new JRadioButton("DNA");
		jr1.addItemListener(this);
		jr2 = new JRadioButton("Protein");
		jr2.addItemListener(this);
		ButtonGroup bg = new ButtonGroup();
		bg.add(jr1); bg.add(jr2);
		jp1.add(jr1); jp1.add(jr2);
		jr1.setSelected(true);
		gridbag.setConstraints(jp1, constraints);
		this.add(jp1);
		
					
		constraints.fill = GridBagConstraints.HORIZONTAL;//output textfield
		constraints.weightx = 1;
		jTextField2 = new JTextField();
		gridbag.setConstraints(jTextField2, constraints);
		this.add(jTextField2);
		
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;//output button
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0;
		jb2 = new JButton("output");
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooserout = new JFileChooser();
				chooserout.setFileFilter(new FastaFilter());
				int retval = chooserout.showSaveDialog(null);
				if(retval==JFileChooser.APPROVE_OPTION){
				fileout = chooserout.getSelectedFile();
				if(!fileout.getName().toLowerCase().endsWith(".fasta")){
					fileout = new File(fileout.getParent(),fileout.getName()+".fasta");
				}
				jTextField2.setText(fileout.getAbsolutePath());
				}
			}
		});
		gridbag.setConstraints(jb2, constraints);
		this.add(jb2);
		
		
		constraints.fill = GridBagConstraints.NONE;//run button
		constraints.gridwidth = 3;
		constraints.weighty = 0.5;
		constraints.ipadx = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		jb3 = new JButton(" start ");
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(filein!=null&&fileout!=null){
					SequenceToolsA.gb2fast(filein, fileout, DNA);
					JOptionPane.showConfirmDialog(null, "mission finished","congratulations",JOptionPane.OK_OPTION);
				}else{
					JOptionPane.showConfirmDialog(null, "Are you sure you hava selected files?", "Warning", JOptionPane.OK_CANCEL_OPTION);
				}
				
			}
		});
		gridbag.setConstraints(jb3, constraints);
		this.add(jb3);
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getItemSelectable()==jr1){//jr1=DNA
			DNA = true;
		}else if(e.getItemSelectable()==jr2){//jr2=protein
			DNA = false;
		}
		
	}
	
	public static void main(String[] args){
		FastaFrame ff = new FastaFrame();
		ff.setVisible(true);
		ff.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

}

class GbFilter extends FileFilter{
	public boolean accept(File f){
		if(f.getName().endsWith("gb")||f.isDirectory()){
			return true;
		}
		return false;
	}
	
	public String getDescription(){
		return "Genbank file";
	}
}

class FastaFilter extends FileFilter{
	public boolean accept(File f){
		if(f.getName().endsWith("fasta")||f.isDirectory()){
			return true;
		}
		return false;
	}
	
	public String getDescription(){
		return "Fasta file";
	}
}

