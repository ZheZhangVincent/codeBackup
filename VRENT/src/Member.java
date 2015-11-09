
/*
 * Writer:
 * Version: 0.1
 * Time: 20/10/2015
 * 
 * */


public class Member {
	
	
	private String contactNo;
	private String name;
	private String add;
	private int memberNo;

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public Member(String contactNo, String name, String add, int memberNo) {
		this.contactNo = contactNo;
		this.name = name;
		this.add = add;
		this.memberNo = memberNo;
	}
	
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}
	


}
