package Windows;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PanelPoint extends JPanel
{
	public JTextField tfX, tfY, tfD;
	public JLabel lblX, lblY, lblD;
	
	public PanelPoint(String name, int config) 
	{
		setBorder(new TitledBorder(null, name, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		lblX = new JLabel("X");
		add(lblX, "cell 0 0,alignx trailing");
		
		tfX = new JTextField();
		add(tfX, "cell 1 0,growx");
		tfX.setColumns(10);
		
		lblY = new JLabel("Y");
		add(lblY, "cell 0 1,alignx trailing");
		
		tfY = new JTextField();
		add(tfY, "cell 1 1,growx");
		tfY.setColumns(10);
		if (config == 0)
		{
			lblD = new JLabel("D");
			add(lblD, "cell 0 2,alignx trailing");
		
			tfD = new JTextField();
			add(tfD, "cell 1 2,growx");
			tfD.setColumns(10);
			tfD.setText("ffffffffffffffffffffffff99def836146bc9b1b4d22831");
		}
		tfX.setText("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012");
		tfY.setText("07192b95ffc8da78631011ed6b24cdd573f977a11e794811");
		
	}
}
