package homework2;

import java.util.ArrayList;

public class StudentTest {
	public static void main(String[] args){
		
		ArrayList<Student> student_array = new ArrayList<Student>();
		Student s1 = new Student("Sai Ma", "ACT", "Master of Computing", "CECS", "2015");
		student_array.add(s1);
		Student s2 = new Student("Bingxin Lee", "ACT", "Master of Computing", "CECS", "2014");
		student_array.add(s2);
		Student s3 = new Student("Bingxin Lee", "ACT", "Master of Computing", "CECS", "2014");
		student_array.add(s3);
		
		for (int n = 0; n < student_array.size(); n ++){
			
			System.out.println(student_array.get(n).toString());
			
		}
		
		
	}
	

}
