package Windows;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class PanelResaultAndOperation extends JPanel
{
	public JButton btnReset, btnCount;
	public JTextArea taResault;
	private JScrollPane scrollPane;
	public String cbItems[] = {"Duplicate", "Multiply", "Add", "Discrete Logarithm"};
	public JComboBox comboBox;
	
	public PanelResaultAndOperation() 
	{
		setBorder(new TitledBorder(null, "Resault and Operation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[grow][][]", "[grow][]"));
		taResault = new JTextArea();
		taResault.setFont(new Font("Tahoma", Font.PLAIN, 11));
		taResault.setEditable(false);
		scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0 4 1,grow");
		scrollPane.setViewportView(taResault);
		
		comboBox = new JComboBox(cbItems);
		add(comboBox, "cell 0 1,grow");
		btnReset = new JButton("Reset");
		btnCount = new JButton("Count");
		add(btnReset,"cell 1 1");
		add(btnCount,"cell 3 1");
	}

}
