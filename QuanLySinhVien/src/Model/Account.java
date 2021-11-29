package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class Account {
	private String aid;
	private String userName;
	private String passWord;
	private int permission;
	private Date create_Date;
	public Account() {
		
	}
	public Account(String aid, String userName, String passWord, int permission, Date create_Date) {
		this.aid = aid;
		this.userName = userName;
		this.passWord = passWord;
		this.permission = permission;
		this.create_Date = create_Date;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public Date getCreate_Date() {
		return create_Date;
	}
	public void setCreate_Date(Date create_Date) {
		this.create_Date = create_Date;
	}
	public boolean checkLogin(Connection conn, String userName, String passWord, int permission) throws SQLException{
		String sql = "select * from Account where username = ? and password = ? and permission = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, passWord);
		pstm.setInt(3, permission);
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next()) {
			return true;
		}
		return false;
	}
}
	