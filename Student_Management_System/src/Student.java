// File: Student.java
import java.util.Objects;

public class Student {
	private String name;
	private String id;

	public Student(String name, String id) {
		this.name = name.trim();
		this.id = id.trim();
	}

	@Override
	public String toString() {
		return "Name: " + name + ", ID: " + id;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	// Override equals and hashCode based on 'id' for uniqueness
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Student student = (Student) obj;
		return id.equalsIgnoreCase(student.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id.toLowerCase());
	}
}
