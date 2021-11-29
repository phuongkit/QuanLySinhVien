package Model;

public class Admin extends Person{
	public Admin() {
		super();
	}
	public Admin(Admin x) {
		super.setId(x.getId());
		super.setName(x.getName());
		super.setEmail(x.getEmail());
		super.setPhone(x.getPhone());
		super.setAddress(x.getAddress());
		super.setAid(x.getAid());
	}
}
