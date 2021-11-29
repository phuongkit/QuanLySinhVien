package Model;

import java.util.Date;

public class Student extends Person{
	private boolean gender;
	private Date dateOfBirth;
	private String fid;
	public Student() {
		super();
	}
	public Student(Student x) {
		super.setId(x.getId());
		super.setName(x.getName());
		super.setEmail(x.getEmail());
		super.setPhone(x.getPhone());
		super.setAddress(x.getAddress());
		super.setAid(x.getAid());
		this.gender = x.isGender();
		this.dateOfBirth = x.dateOfBirth;
		this.fid = x.fid;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	
}
