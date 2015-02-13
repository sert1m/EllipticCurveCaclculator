package Windows;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PanelCheckRadix extends JPanel 
{
	public JRadioButton rbBin, rbHex, rbDec; 
	public final ButtonGroup bgRadix = new ButtonGroup();
	
	PanelCheckRadix ()
	{
		setBorder(new TitledBorder(null, "Select Radix of Parametrs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(169, 60);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		rbBin = new JRadioButton("bin");
		bgRadix.add(rbBin);
		add(rbBin);
		
		rbDec = new JRadioButton("dec");
		bgRadix.add(rbDec);
		add(rbDec);
		
		rbHex = new JRadioButton("hex");
		bgRadix.add(rbHex);
		rbHex.setSelected(true);
		add(rbHex);
	}
}
