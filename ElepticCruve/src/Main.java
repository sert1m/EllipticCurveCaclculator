import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import Windows.MainWindow;


public class Main {
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					MainWindow frame = new MainWindow();
					JFrame.setDefaultLookAndFeelDecorated(true);
					frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
