package Windows;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import EllipticCurve.ECFp;
import EllipticCurve.PointXY;
import EncodingInformation.EncodingKoblitz;
import EncodingInformation.EncodingMessage;
import EncodingInformation.EncryptionElGamal;

@SuppressWarnings("serial")
public class PanelEncode extends JPanel
{
	private String cbItems[] = {"Encode", "Decode", "Encrypt", "Decrypt", "Encode&Encrypt"};
	private JPanel operationPanel;
	private JComboBox comboBox;
	private JButton btnExecute, selectInput, selectOutput;
	private PanelECParams paramsEC;
	private PanelPoint pointG, pointQ;
	private PanelCheckRadix selectRadix;
	private JTextField tfInputFile, tfOutputFile;
	private JPanel panelFile;
	private JLabel lblInputfile, lblOutputfile, lblTime;
	private int radix;
	private JFrame frame;
	private JTextField tfK;
	private JPanel panel;
	
	public PanelEncode(JFrame frame) {
		this.frame = frame;
		setLayout(new MigLayout("", "[271px][169px]", "[78px][67px][110px][110px][110px][20px]"));
		
		operationPanel = new JPanel();
		operationPanel.setToolTipText("SelectOperation");
		operationPanel.setBorder(new TitledBorder(null, "Select Operation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(operationPanel, "cell 0 1,grow");
		operationPanel.setLayout(new MigLayout("", "[grow]", "[]"));
		
		comboBox = new JComboBox(cbItems);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBoxListener (comboBox.getSelectedIndex());
			}
		});
		operationPanel.add(comboBox, "flowx,cell 0 0,growx");
		
		btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executeListener();
			}
		});
		operationPanel.add(btnExecute, "cell 0 0");
		
		paramsEC = new PanelECParams();
		add(paramsEC, "cell 0 2 2 1,grow");
		
		pointG = new PanelPoint("Base Point", 0);
		pointQ = new PanelPoint("User`s Keys", 0);
		pointG.lblD.setText("N");
		add(pointG, "cell 0 3 2 1,grow");
		pointQ.tfX.setText("b84df98303e0852b1e28d45d8889eabdab12261159a2fb1");
		pointQ.tfY.setText("7247df60810c8d4a66ef69fe28bfe133d305ce692f35e7a3");
		pointQ.tfD.setText("99def836146bc9b1b4d22831");	
		radix = 16;
		add(pointQ, "cell 0 4 2 1,grow");
		
		selectRadix = new PanelCheckRadix ();
		
		add(selectRadix, "cell 1 1,grow");
		selectRadix.rbBin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radixListener (2);
			}
		});
		selectRadix.rbDec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radixListener (10);
			}
		});
		selectRadix.rbHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radixListener (16);
			}
		});
		
		panelFile = new JPanel();
		panelFile.setBorder(new TitledBorder(null, "Select Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panelFile, "cell 0 0 2 1,grow");
		panelFile.setLayout(null);
		
		lblInputfile = new JLabel("InputFile");
		lblInputfile.setBounds(8, 26, 60, 14);
		panelFile.add(lblInputfile);
		
		lblOutputfile = new JLabel("OutPutFile");
		lblOutputfile.setBounds(8, 51, 60, 14);
		panelFile.add(lblOutputfile);
		
		tfInputFile = new JTextField();
		tfInputFile.setBounds(78, 23, 242, 20);
		panelFile.add(tfInputFile);
		tfInputFile.setColumns(10);
		
		tfOutputFile = new JTextField();
		tfOutputFile.setBounds(78, 48, 242, 20);
		panelFile.add(tfOutputFile);
		tfOutputFile.setColumns(10);
		
		selectInput = new JButton("Select Input");
		selectInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getInputFile();
			}
		});
		selectInput.setBounds(330, 22, 100, 23);
		panelFile.add(selectInput);
		
		selectOutput = new JButton("Select Output");
		selectOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getOutputFile();
			}
		});
		selectOutput.setBounds(330, 47, 100, 23);
		panelFile.add(selectOutput);
		
		panel = new JPanel();
		add(panel, "cell 0 5 2 1,grow");
		panel.setLayout(null);
		
		lblTime = new JLabel("");
		lblTime.setBounds(125, 6, 293, 14);
		panel.add(lblTime);
		
		JLabel lblK = new JLabel("k");
		lblK.setBounds(0, 6, 23, 14);
		panel.add(lblK);
		
		tfK = new JTextField();
		tfK.setBounds(29, 0, 86, 20);
		panel.add(tfK);
		tfK.setToolTipText("Koblitz encoding parametr");
		tfK.setColumns(10);
		tfK.setText ("14");
	}
	private void radixListener(int r)
	{
		BigInteger temp;
		try
		{
			if (!paramsEC.tfA.getText().equals(""))
			{
				temp = new BigInteger (paramsEC.tfA.getText(), radix);
				paramsEC.tfA.setText(temp.toString(r));
			}
			if (!paramsEC.tfB.getText().equals(""));
			{
				temp = new BigInteger (paramsEC.tfB.getText(), radix);
				paramsEC.tfB.setText(temp.toString(r));
			}
			if (!paramsEC.tfP.getText().equals(""));
			{
				temp = new BigInteger (paramsEC.tfP.getText(), radix);
				paramsEC.tfP.setText(temp.toString(r));
			}
			if (!pointG.tfX.getText().equals(""))
			{
				temp = new BigInteger (pointG.tfX.getText(), radix);
				pointG.tfX.setText(temp.toString(r));
			}
			if (!pointG.tfY.getText().equals(""))
			{
				temp = new BigInteger (pointG.tfY.getText(), radix);
				pointG.tfY.setText(temp.toString(r));
			}
			if (!pointG.tfD.getText().equals(""))
			{
				temp = new BigInteger (pointG.tfD.getText(), radix);
				pointG.tfD.setText(temp.toString(r));
			}
			if (!pointQ.tfX.getText().equals(""))
			{
				temp = new BigInteger (pointQ.tfX.getText(), radix);
				pointQ.tfX.setText(temp.toString(r));
			}
			if (!pointQ.tfY.getText().equals(""))
			{
				temp = new BigInteger (pointQ.tfY.getText(), radix);
				pointQ.tfY.setText(temp.toString(r));
			}
			if (!pointQ.tfD.getText().equals(""))
			{
				temp = new BigInteger (pointQ.tfD.getText(), radix);
				pointQ.tfD.setText(temp.toString(r));
			}
			if (tfK.getText().equals (""))
			{
				temp = new BigInteger (tfK.getText(), radix);
				tfK.setText(temp.toString(r));
			}
			radix = r;
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Some fields are not numbers.");
		}
		
	}
	private void comboBoxListener (int index)
	{
		if (index == 0 || index == 1)
		{
			pointQ.tfX.setEditable(false);
			pointQ.tfY.setEditable(false);
			pointQ.tfD.setEditable(false);
		}
		else if (index == 2 || index == 4)
		{
			pointQ.tfX.setEditable(true);
			pointQ.tfY.setEditable(true);
			pointQ.tfX.setToolTipText("Write here an open key.");
			pointQ.tfY.setToolTipText("Write here an open key.");
			pointQ.tfD.setEditable(true);
			pointQ.tfD.setToolTipText("Write here a session key. If it is empty, it would be generated.");
		}
		else if (index == 3 || index == 5)
		{
			pointQ.tfX.setEditable(true);
			pointQ.tfY.setEditable(true);
			pointQ.tfX.setToolTipText("Write here an session key.");
			pointQ.tfY.setToolTipText("Write here an session key.");
			pointQ.tfD.setEditable(true);
			pointQ.tfD.setToolTipText("Write here a private key.");
		}
	}
	private void getInputFile ()
	{
		FileDialog fd = new FileDialog(frame, "Select txt or res file", FileDialog.LOAD);
		fd.setVisible(true);
		tfInputFile.setText(fd.getDirectory() + fd.getFile());
	}
	private void getOutputFile ()
	{
		FileDialog fd = new FileDialog(frame, "Select txt or res file", FileDialog.LOAD);
		fd.setVisible(true);
		tfOutputFile.setText(fd.getDirectory() + fd.getFile());
	}
	private void executeListener()
	{
		ECFp ec = null;
		PointXY G = null;
		PointXY Q = null;
		BigInteger n = null;
		BigInteger k = null;
		EncodingKoblitz ek = null;
		EncodingMessage em = null;
		EncryptionElGamal eg = null;
		List<PointXY> points;
		try 
		{
			 ec= new ECFp (paramsEC.tfA.getText(), paramsEC.tfB.getText(), paramsEC.tfP.getText(), radix);
			 G = new PointXY (pointG.tfX.getText(), pointG.tfY.getText(), radix);
			 n = new BigInteger (pointG.tfD.getText(), radix);
			 k = new BigInteger (tfK.getText(), radix);
			 ek = new EncodingKoblitz (k , ec);
			 em = new EncodingMessage ();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Some fields are not numbers.");
		}
		
		try 
		{
			long time = System.nanoTime();
			if (comboBox.getSelectedIndex() == 0)
			{
				em.readMessage(tfInputFile.getText());
				points = em.encodeMessage(ek);
				em.writePoints(points, tfOutputFile.getText());
			}
			else if (comboBox.getSelectedIndex() == 1)
			{
				points = new ArrayList<PointXY>();
				points = em.readPoints(points, tfInputFile.getText());
				String s = em.decodeMessage(points, ek);
				FileWriter fw = new FileWriter(tfOutputFile.getText());
				fw.write(s);
				fw.close();
			}
			else if (comboBox.getSelectedIndex() == 2)
			{
				points = new ArrayList<PointXY>();
				points = em.readPoints(points, tfInputFile.getText());
				Q = new PointXY (pointQ.tfX.getText(), pointQ.tfY.getText(), radix);
				if (pointQ.tfD.getText().equals(""))
					eg = new EncryptionElGamal (ec, n, G, Q, null);
				else 
				{
					BigInteger k1 = new BigInteger (tfK.getText(), radix);
					eg = new EncryptionElGamal (ec, n, G, Q, k1);
				}
				
				points = em.encryptMessage(points, eg);
				em.writePoints(points, tfOutputFile.getText());
				String filename = tfOutputFile.getText().substring(0,tfOutputFile.getText().lastIndexOf("."));
				filename = filename.concat ("SessionKey.txt");
				FileWriter fw = new FileWriter(filename);
				fw.write(ec.toString(radix)+"\n"+eg.getR().toString(radix));
				fw.close();
			}
			else if (comboBox.getSelectedIndex() == 3)
			{
				points = new ArrayList<PointXY>();
				points = em.readPoints(points, tfInputFile.getText());
				Q = new PointXY (pointQ.tfX.getText(), pointQ.tfY.getText(), radix);
				BigInteger d = new BigInteger (pointQ.tfD.getText(), radix);
				eg = new EncryptionElGamal (ec, n, G, Q, null);
				points = em.decryptMessage(points, eg, d, Q);
				String s = em.decodeMessage(points, ek);
				FileWriter fw = new FileWriter(tfOutputFile.getText());
				fw.write(s);
				fw.close();
			}
			else if (comboBox.getSelectedIndex() == 4)
			{
				em.readMessage(tfInputFile.getText());
				points = em.encodeMessage(ek);
				Q = new PointXY (pointQ.tfX.getText(), pointQ.tfY.getText(), radix);
				eg = new EncryptionElGamal (ec, n, G, Q, null);
				points = em.encryptMessage(points, eg);
				em.writePoints(points, tfOutputFile.getText());
				String filename = tfOutputFile.getText().substring(0,tfOutputFile.getText().lastIndexOf("."));
				filename = filename.concat ("SessionKey.txt");
				FileWriter fw = new FileWriter(filename);
				fw.write(ec.toString(radix)+"\r\n"+eg.getR().toString(radix));
				fw.close();
			}
			time = System.nanoTime() - time;
			time =  time/1000000;
			lblTime.setText("Spent time " + time + " milliseconds");
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Some fields are not numbers!");
		}
		catch (FileNotFoundException e){
			JOptionPane.showMessageDialog(this, "File not found!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error of input/output!");
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, "Check the data carefully!");
		}
		
	}
}
