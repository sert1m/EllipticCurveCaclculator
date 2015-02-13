package Windows;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JTabbedPane tabbedPane;
	private PanelCalculateEC calculateEC;
	private PanelEncode encode;
	private JMenuBar menuBar;
	private JMenuItem mntmEllipticCurveDemo;
	private JMenuItem mntmExit;

	public MainWindow() {
		setTitle("Elliptic Cruve");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 630);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mntmEllipticCurveDemo = new JMenuItem("Elliptic Curve Demo");
		mntmEllipticCurveDemo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		         try {
		        	 Runtime.getRuntime().exec("java -jar eccdemo.jar");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuBar.add(mntmEllipticCurveDemo);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		menuBar.add(mntmExit);
		tabbedPane = new JTabbedPane();
		setContentPane(tabbedPane);

		calculateEC = new PanelCalculateEC();
		tabbedPane.addTab("Eliptic Cruve Calculate", null, calculateEC, null);
		
		encode = new PanelEncode (this);
		tabbedPane.addTab("Elliptic Cruve Encoding&Encryption", null, encode, null);
		
		setResizable(false);
	}

}
