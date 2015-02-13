package Windows;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelECParams extends JPanel {
	public JTextField tfA, tfB, tfP;
	public JLabel lblA, lblB, lblP;

	public PanelECParams() 
	{
		setBorder(new TitledBorder(null, "Paramets of Elliptic Cruve", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		lblA = new JLabel("A");
		add(lblA, "cell 0 0,alignx trailing");
		
		tfA = new JTextField();
		add(tfA, "cell 1 0,growx");
		tfA.setColumns(10);
		
		lblB = new JLabel("B");
		add(lblB, "cell 0 1,alignx trailing");
		
		tfB = new JTextField();
		add(tfB, "cell 1 1,growx");
		tfB.setColumns(10);
		
		lblP = new JLabel("P");
		add(lblP, "cell 0 2,alignx trailing");
		
		tfP = new JTextField();
		add(tfP, "cell 1 2,growx");
		tfP.setColumns(10);
		tfA.setText("-3");
		tfB.setText("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1");
		tfP.setText("fffffffffffffffffffffffffffffffeffffffffffffffff");
	}
}
