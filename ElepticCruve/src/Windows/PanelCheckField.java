package Windows;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class PanelCheckField extends JPanel
{
	public final ButtonGroup bgField = new ButtonGroup();
	public JRadioButton rbGalua, rbExtended;
	public PanelCheckField() 
	{
		setBorder(new TitledBorder(null, "Check Field of Eliptic Cruve", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(290, 60);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		rbGalua = new JRadioButton("EC on Galua Field");
		add(rbGalua);
		bgField.add(rbGalua);
		rbGalua.setSelected(true);
		
		rbExtended = new JRadioButton("EC on Extended Field");
		add(rbExtended);
		bgField.add(rbExtended);
	}

}
