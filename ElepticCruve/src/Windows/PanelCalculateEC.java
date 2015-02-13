package Windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DiscreteLogarithm.BruteForce;
import DiscreteLogarithm.RoPolard;
import EllipticCurve.ECF2m;
import EllipticCurve.ECFp;
import EllipticCurve.EllipticCurve;
import EllipticCurve.PointXY;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PanelCalculateEC extends JPanel
{
	private JLabel lbltime;
	private PanelECParams paramsEC;
	private PanelResaultAndOperation panelResault;
	private PanelCheckField panelField;
	private PanelCheckRadix selectRadix;
	private PanelPoint panelPoint1, panelPoint2;
	private int radix;
	
	
	
	PanelCalculateEC ()
	{
		setLayout(new MigLayout("", "[270px][10px][350px]", "[120px][60px][110px][110px][92px][14px]"));
		
		panelField = new PanelCheckField();
		
		add(panelField, "cell 0 1,growx,aligny top");
		
		selectRadix = new PanelCheckRadix();
		add(selectRadix, "cell 2 1,alignx left,aligny top");
		
		
		panelPoint1 = new PanelPoint("First Point", 0);
		add(panelPoint1, "cell 0 3 3 1,grow");
	
		panelPoint2 = new PanelPoint("Second Point", 1);
		add(panelPoint2, "cell 0 4 3 1,grow");
		
		panelResault = new PanelResaultAndOperation();
		add(panelResault, "cell 0 0 3 1,grow");

		panelResault.comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBoxListener(panelResault.comboBox.getSelectedIndex());
			}
		});
		panelResault.btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetListener();
			}
		});
		panelResault.btnCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				operationCount();
			}
		});
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
	
		panelPoint2.tfX.setEditable(false);
		panelPoint2.tfY.setEditable(false);
		panelPoint1.tfD.setEditable(false);
		
		lbltime = new JLabel("");
		add(lbltime, "cell 0 5 3 1,grow");
		
		panelPoint2.tfX.setText("dafebf5828783f2ad35534631588a3f629a70fb16982a888");
		panelPoint2.tfY.setText("dd6bda0d993da0fa46b27bbc141b868f59331afa5c7e93ab");
		radix = 16;
		
		paramsEC = new PanelECParams();
		add(paramsEC, "cell 0 2 3 1,grow");
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
			if (!panelPoint1.tfX.getText().equals(""))
			{
				temp = new BigInteger (panelPoint1.tfX.getText(), radix);
				panelPoint1.tfX.setText(temp.toString(r));
			}
			if (!panelPoint1.tfY.getText().equals(""))
			{
				temp = new BigInteger (panelPoint1.tfY.getText(), radix);
				panelPoint1.tfY.setText(temp.toString(r));
			}
			if (!panelPoint1.tfD.getText().equals(""))
			{
				temp = new BigInteger (panelPoint1.tfD.getText(), radix);
				panelPoint1.tfD.setText(temp.toString(r));
			}
			if (!panelPoint2.tfX.getText().equals(""))
			{
				temp = new BigInteger (panelPoint2.tfX.getText(), radix);
				panelPoint2.tfX.setText(temp.toString(r));
			}
			if (!panelPoint2.tfY.getText().equals(""))
			{
				temp = new BigInteger (panelPoint2.tfY.getText(), radix);
				panelPoint2.tfY.setText(temp.toString(r));
			}
			radix = r;
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Some fields are not numbers.");
		}
		
	}
	private void comboBoxListener(int index)
	{
		if (index == 0)
		{
			panelPoint2.tfX.setEditable(false);
			panelPoint2.tfY.setEditable(false);
			panelPoint1.tfD.setEditable(false);
			panelPoint1.tfD.setToolTipText(null);
			panelPoint1.setToolTipText(null);
			panelPoint2.setToolTipText(null);
		}
		else if(index == 1)
		{
			panelPoint2.tfX.setEditable(false);
			panelPoint2.tfY.setEditable(false);
			panelPoint1.tfD.setEditable(true);
			panelPoint1.tfD.setToolTipText("Koeficient of multiply");
			panelPoint1.setToolTipText(null);
			panelPoint2.setToolTipText(null);
		}
		else if (index == 2)
		{
			panelPoint2.tfX.setEditable(true);
			panelPoint2.tfY.setEditable(true);
			panelPoint1.tfD.setEditable(false);
			panelPoint1.tfD.setToolTipText(null);
			panelPoint1.setToolTipText(null);
			panelPoint2.setToolTipText(null);
		}
		else
		{
			panelPoint2.tfX.setEditable(true);
			panelPoint2.tfY.setEditable(true);
			panelPoint1.tfD.setEditable(true);
			panelPoint1.tfD.setToolTipText("Order of Base Point");
			panelPoint1.setToolTipText("Base Point");
			panelPoint2.setToolTipText("Open Key");
		}
	}
	private void resetListener()
	{
		panelField.rbGalua.setSelected(true);
		selectRadix.rbDec.setSelected(true);
		panelResault.taResault.setText("");
		paramsEC.tfA.setText(""); 
		paramsEC.tfB.setText(""); 
		paramsEC.tfP.setText(""); 
		panelPoint1.tfX.setText(""); 
		panelPoint1.tfY.setText(""); 
		panelPoint2.tfX.setText(""); 
		panelPoint2.tfY.setText(""); 
		panelPoint1.tfD.setText("");
		panelResault.comboBox.setSelectedIndex(0);
		comboBoxListener(0);
		lbltime.setText("");
	}
	private void operationCount()
	{
		long time1 = 0;
		EllipticCurve ec = null;
		if (selectRadix.rbBin.isSelected())
			radix = 2;
		else if (selectRadix.rbDec.isSelected())
			radix = 10;
		else if (selectRadix.rbHex.isSelected())
			radix = 16;
		try {
			if (panelField.rbGalua.isSelected())
				ec = new ECFp(paramsEC.tfA.getText(),paramsEC.tfB.getText(),paramsEC.tfP.getText(), radix);
			else if (panelField.rbExtended.isSelected())
				ec = new ECF2m(paramsEC.tfA.getText(),paramsEC.tfB.getText(),paramsEC.tfP.getText(), radix);
			PointXY point1 = new PointXY(panelPoint1.tfX.getText(), panelPoint1.tfY.getText(), radix);
			PointXY point2;
			PointXY temp;
			BigInteger d;
			
			if (panelResault.comboBox.getSelectedIndex() == 0)
			{
				time1 = System.nanoTime();
				temp = (PointXY)ec.duplicate(point1);
				time1 = System.nanoTime() - time1;
				panelResault.taResault.setText(temp.toString(radix));
			}
			if (panelResault.comboBox.getSelectedIndex() == 1)
			{
				time1 = System.nanoTime();
				d = new BigInteger (panelPoint1.tfD.getText(),radix);
				temp = (PointXY) ec.multiply(d,point1);
				time1 = System.nanoTime() - time1;
				panelResault.taResault.setText(temp.toString(radix));
			}
			if (panelResault.comboBox.getSelectedIndex() == 2)
			{
				time1 = System.nanoTime();
				point2 = new PointXY(panelPoint2.tfX.getText(), panelPoint2.tfY.getText(), radix);
				temp = (PointXY) ec.add(point1, point2);
				time1 = System.nanoTime() - time1;
				panelResault.taResault.setText(temp.toString(radix));
			}
			if (panelResault.comboBox.getSelectedIndex() == 3)
			{
				String options [] = {"Brute Force", "RoPolard","Cancel"};
				int code = JOptionPane.showOptionDialog(this, 
				         "Select a method:", 
				         "Disrete Logarithm methods", 0, JOptionPane.QUESTION_MESSAGE, 
				         null, options, null);
				time1 = System.nanoTime();
				if(code == 2)
					return;
				else if (code == 1)
				{
					point2 = new PointXY(panelPoint2.tfX.getText(), panelPoint2.tfY.getText());
					d = new BigInteger (panelPoint1.tfD.getText(), radix);
					RoPolard rp = new RoPolard (ec, point1, point2, d); 
					rp.find();
					time1 = System.nanoTime() - time1;
					d = rp.getD();
					panelResault.taResault.setText(d.toString(radix));
				}
				else if (code == 0)
				{
					point2 = new PointXY(panelPoint2.tfX.getText(), panelPoint2.tfY.getText());
					d = new BigInteger (panelPoint1.tfD.getText(), radix);
					BruteForce bf = new BruteForce (ec, point1, point2, d); 
					bf.count();
					time1 = System.nanoTime() - time1;
					d = bf.getD();
					panelResault.taResault.setText(d.toString(radix));
				}
			}
		}catch (NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Some fields are not numbers.");
		}
		if (time1 != 0)
		{
			double time = ((double) time1)/1000000.00;
			lbltime.setText("Spent time " + time + " milliseconds");
		}
	}
}
