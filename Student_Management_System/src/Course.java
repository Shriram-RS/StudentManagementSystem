// File: Course.java
import java.util.Objects;

public class Course {

	private String title;

	public Course(String title) {
		this.title = title.trim();
	}

	public String getTitle() {
		return title;
	}

	// Override equals and hashCode based on 'title' for uniqueness
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Course course = (Course) obj;
		return title.equalsIgnoreCase(course.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title.toLowerCase());
	}
}
