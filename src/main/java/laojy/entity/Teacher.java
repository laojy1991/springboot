package laojy.entity;

public class Teacher {
    private Integer id;

    private String teacherName;

    private String teacherAge;

    private Integer teacherNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public String getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(String teacherAge) {
        this.teacherAge = teacherAge == null ? null : teacherAge.trim();
    }

    public Integer getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(Integer teacherNo) {
        this.teacherNo = teacherNo;
    }
}