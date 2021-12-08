package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Transcript {
	private String ccid;
	private String sid;
	private float score;
	private int semester;
	public Transcript() {
		
	}
	public Transcript(Transcript x) {
		this.ccid = x.ccid;
		this.sid = x.sid;
		this.score = x.score;
		this.semester = x.semester;
	}
	public String getCcid() {
		return ccid;
	}
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public static ArrayList<Transcript> load(Connection connection) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		ArrayList<Transcript> listTranscripts = new ArrayList<Transcript>();
		Transcript tr =new Transcript();
		try {
			String query = "select * from Transcript"; 
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tr.setCcid(resultSet.getString(1));
				tr.setSid(resultSet.getString(2));
				tr.setScore(resultSet.getFloat(3));
				tr.setSemester(resultSet.getInt(4));

				listTranscripts.add(new Transcript(tr));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listTranscripts;
	}
	public static int Insert(Transcript tr, Connection conn) throws ClassNotFoundException, SQLException {

		int rs = 0;
		try  
		{
			String query = "Insert into Transcript values ( ? , ? , ? , ?);";

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, tr.getCcid());
			ps.setString(2, tr.getSid());
			ps.setFloat(3, tr.getScore());
			ps.setInt(4, tr.getSemester());

			rs = ps.executeUpdate();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	public static int Edit(Transcript tr,Connection conn) throws ClassNotFoundException, SQLException {
		int rs = 0;
		try  
		{
			String query = "Update Transcript set SCORE = ?, SEMESTER = ? where CCID = ? and SID = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setFloat(1, tr.getScore());
			ps.setInt(2, tr.getSemester());
			ps.setString(3, tr.getCcid());
			ps.setString(4, tr.getSid());
			
			rs = ps.executeUpdate();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	public static int Del(String ccid, String sid,Connection conn) throws ClassNotFoundException, SQLException {

		int rs = 0;
		try  
		{
			PreparedStatement ps = null;
			String query = "delete from Transcript where CCID = ? and SID = ?";			
			ps = conn.prepareStatement(query);

			ps.setString(1,ccid);
			ps.setString(2,sid);

			rs = ps.executeUpdate();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}

	public static Transcript findTranscript(String ccid, String sid,Connection conn) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Transcript tr =new Transcript();
		try {
			String query = "select * from Transcript where CCID = ? and SID = ?"; 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1,ccid);
			ps.setString(2,sid);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				tr.setCcid(resultSet.getString(1));
				tr.setSid(resultSet.getString(2));
				tr.setScore(resultSet.getFloat(3));
				tr.setScore(resultSet.getInt(4));
				
				return tr;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Transcript> findTranscriptoOfCCID(String ccid,Connection conn) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ArrayList<Transcript> listTranscripts = new ArrayList<Transcript>();
		Transcript tr  = new Transcript();
		try {
			String query = "select * from Transcript where CCID = ?"; 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1,ccid);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				tr.setCcid(resultSet.getString(1));
				tr.setSid(resultSet.getString(2));
				tr.setScore(resultSet.getFloat(3));
				tr.setSemester(resultSet.getInt(4));
				
				listTranscripts.add(new Transcript(tr));
			}
			return listTranscripts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Transcript> findTranscriptOfSID(String sid,Connection conn) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ArrayList<Transcript> listTranscripts = new ArrayList<Transcript>();
		Transcript tr = new Transcript();
		try {
			String query = "select * from Transcript where SID = ?"; 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1,sid);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				tr.setCcid(resultSet.getString(1));
				tr.setSid(resultSet.getString(2));
				tr.setScore(resultSet.getFloat(3));
				tr.setSemester(resultSet.getInt(4));
				
				listTranscripts.add(new Transcript(tr));
			}
			return listTranscripts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}