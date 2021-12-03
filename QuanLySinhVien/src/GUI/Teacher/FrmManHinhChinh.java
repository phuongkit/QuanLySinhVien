package GUI.Teacher;

import java.sql.Connection;

import javax.swing.JFrame;

public class FrmManHinhChinh  extends JFrame{
	private Connection conn;
	
	public FrmManHinhChinh(Connection conn) {
		this.conn = conn;
	}	
}
