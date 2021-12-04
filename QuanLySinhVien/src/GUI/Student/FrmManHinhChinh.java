package GUI.Student;

import java.sql.Connection;

import javax.swing.JFrame;

public class FrmManHinhChinh extends JFrame{
	private Connection conn;
	private String userName;
	
	public FrmManHinhChinh(Connection conn, String userName) {
		this.conn = conn;
		this.userName = userName;
	}	
}
