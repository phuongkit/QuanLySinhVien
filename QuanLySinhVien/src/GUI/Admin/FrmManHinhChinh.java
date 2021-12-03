package GUI.Admin;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrmManHinhChinh extends JFrame{
	private Connection conn;

	private JPanel pnlMain;

	public FrmManHinhChinh(Connection conn) {
		this.conn = conn;
		setTitle("Màn hình chính - Admin");
		pnlMain = new JPanel();
		pnlMain.setLayout(null);
		pnlMain = (JPanel) getContentPane();

		// Frame Size
		setSize(504, 336);
		//setBounds(0, 0, FRM_WIDTH, FRM_HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
	}
}
