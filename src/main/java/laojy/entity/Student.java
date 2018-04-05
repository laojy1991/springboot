package laojy.entity;

public class Student {
    private Integer id;

    private String studentName;

    private String studentAge;

    private Integer teacherNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge == null ? null : studentAge.trim();
    }

    public Integer getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(Integer teacherNo) {
        this.teacherNo = teacherNo;
    }
}