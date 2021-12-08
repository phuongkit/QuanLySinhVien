package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoCourse_Class extends Course_Class {
	private String name;
	private String descriptionCourse;
	private int numOfCredits;
	public InfoCourse_Class() {
		super();
	}
	public InfoCourse_Class(InfoCourse_Class x) {
		super.setCid(x.getCid());
		super.setCcid(x.getCcid());
		super.setRid(x.getRid());
		super.setTid(x.getTid());
		super.setStatus(x.isStatus());
		super.setSemester(x.getSemester());
		super.setDescription(x.getDescription());
		this.name = x.getName();
		this.descriptionCourse = x.getDescriptionCourse();
		this.numOfCredits = x.getNumOfCredits();
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescriptionCourse() {
		return descriptionCourse;
	}
	public void setDescriptionCourse(String descriptionCourse) {
		this.descriptionCourse = descriptionCourse;
	}
	public int getNumOfCredits() {
		return numOfCredits;
	}
	public void setNumOfCredits(int numOfCredits) {
		this.numOfCredits = numOfCredits;
	}
	public static ArrayList<InfoCourse_Class> loadInfo(String search, boolean searchCID, Connection connection) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		ArrayList<InfoCourse_Class> listInfoCourse_Class = new ArrayList<InfoCourse_Class>();
		InfoCourse_Class ics =new InfoCourse_Class();
		try {
			String query = "select c.CID, cc.CCID, c.NAME, cc.RID, cc.TID, cc.STATUS, cc.SEMESTER, cc.DESCRIPTION, c.NUMBEROFCREDITS, c.DESCRIPTION from Course c INNER JOIN Course_Class cc ON c.CID = cc.CID";
			if(!search.equals("")) {
				if(searchCID) {
					query = query + " where c.CID = ?";
				}
				else {
					query = query + " where c.NAME = ?";
				}
			}
			PreparedStatement ps = connection.prepareStatement(query);
			if(!search.equals("")) {
				ps.setString(1,search);
			}
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				ics.setCid(resultSet.getString(1));
				ics.setCcid(resultSet.getString(2));
				ics.setName(resultSet.getString(3));
				ics.setRid(resultSet.getString(4));
				ics.setTid(resultSet.getString(5));
				ics.setStatus(resultSet.getBoolean(6));
				ics.setSemester(resultSet.getInt(7));
				ics.setDescription(resultSet.getString(8));
				ics.setNumOfCredits(resultSet.getInt(9));
				ics.setDescriptionCourse(resultSet.getString(10));
				
				listInfoCourse_Class.add(new InfoCourse_Class(ics));
			}
			return listInfoCourse_Class;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
