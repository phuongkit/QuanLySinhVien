package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class Account {
	private String aid;
	private String userName;
	private String passWord;
	private int permission;
	private Date create_Date;
	public Account() {
		
	}
	public Account(Account ac) {
		this.aid = ac.aid;
		this.userName = ac.userName;
		this.passWord = ac.passWord;
		this.permission = ac.permission;
		this.create_Date = ac.create_Date;
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
	public static ArrayList<Account> load(Connection connection) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		ArrayList<Account> listAccounts = new ArrayList<Account>();
		Account ac = new Account();
		try {
			String query = "select * from Account"; 
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ac.setAid(resultSet.getString(1));
				ac.setUserName(resultSet.getString(2));
				ac.setPassWord(resultSet.getString(3));
				ac.setPermission(resultSet.getInt(4));
				ac.setCreate_Date(resultSet.getDate(5));

				listAccounts.add(new Account(ac));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listAccounts;
	}
	public static int Insert(Account ac, Connection conn) throws ClassNotFoundException, SQLException {

		int rs = 0;
		try  
		{
			String query = "Insert into Account values ( ? , ? , ? , ?, ?);";

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, ac.getAid());
			ps.setString(2, ac.getUserName());
			ps.setString(3, ac.getPassWord());
			ps.setInt(4, ac.getPermission());
			ps.setDate(5,  new java.sql.Date(ac.getCreate_Date().getTime()));

			rs = ps.executeUpdate();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	public static int Edit(Account ac,Connection conn) throws ClassNotFoundException, SQLException {

		int rs = 0;
		try  
		{

			String query = "Update Account set AID = ?, USERNAME = ?, PASSWORD = ?, PERMISSION = ?, CREATE_DATE = ? where AID = ?";

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, ac.getUserName());
			ps.setString(2, ac.getPassWord());
			ps.setInt(3, ac.getPermission());
			ps.setDate(4,  new java.sql.Date(ac.getCreate_Date().getTime()));
			ps.setString(5, ac.getAid());

			rs = ps.executeUpdate();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	public static int Del(String id,Connection conn) throws ClassNotFoundException, SQLException {

		int rs = 0;
		try  
		{
			PreparedStatement ps = null;
			String query = "delete from Account where AID = ?";			
			ps = conn.prepareStatement(query);

			ps.setString(1,id);


			rs = ps.executeUpdate();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}

	public static Account findAccount(String id,Connection conn)  throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		Account ac =new Account();
		try {
			String query = "select * from Account where AID = ?"; 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				ac.setAid(resultSet.getString(1));
				ac.setUserName(resultSet.getString(2));
				ac.setPassWord(resultSet.getString(3));
				ac.setPermission(resultSet.getInt(4));
				ac.setCreate_Date(resultSet.getDate(5));
				return ac;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Account findAccountofUserName(String userName,Connection conn)  throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		Account ac = new Account();
		String ID = "";
		try {
			String query = "select * from Account where USERNAME = ?"; 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				ac.setAid(resultSet.getString(1));
				ac.setUserName(resultSet.getString(2));
				ac.setPassWord(resultSet.getString(3));
				ac.setPermission(resultSet.getInt(4));
				ac.setCreate_Date(resultSet.getDate(5));
				return ac;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
	