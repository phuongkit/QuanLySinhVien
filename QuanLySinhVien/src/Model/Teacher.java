package Model;

public class Teacher extends Person{
	public Teacher() {
		super();
	}
	public Teacher(Teacher x) {
		super.setId(x.getId());
		super.setName(x.getName());
		super.setEmail(x.getEmail());
		super.setPhone(x.getPhone());
		super.setAddress(x.getAddress());
		super.setAid(x.getAid());
	}
}
