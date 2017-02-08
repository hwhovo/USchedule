package u.Schedule.entity.entities;

/**
 * Created by Hovhannes on 29.01.2016.
 */
public class Bunch {
    private String bunch;
    private String univercity;
    private String faculty;
    private String department;

    public Bunch(String univercity, String faculty, int course, String bunch) {
        this.bunch = bunch;
        this.univercity = univercity;
        this.faculty = faculty;
        this.course = course;
        this.department = null;
    }

    public Bunch(String univercity, String faculty, String department, int course) {
        this.univercity = univercity;
        this.faculty = faculty;
        this.department = department;
        this.course = course;
        this.bunch = null;
    }

    public Bunch(String univercity, String faculty, String department, int course, String bunch) {
        this.bunch = bunch;
        this.univercity = univercity;
        this.faculty = faculty;
        this.department = department;
        this.course = course;

    }


    public String getBunch() {
        return bunch;
    }

    public void setBunch(String bunch) {
        this.bunch = bunch;
    }

    public String getUnivercity() {
        return univercity;
    }

    public void setUnivercity(String univercity) {
        this.univercity = univercity;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    private int course;
}
