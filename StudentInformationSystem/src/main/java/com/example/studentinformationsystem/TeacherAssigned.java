package com.example.studentinformationsystem;

public class TeacherAssigned {
    private  String assign_id;
    private  String teacher_id;
    private  int course_id;
    private String department_id;

    public TeacherAssigned(String assign_id, String teacher_id, int course_id, String department_id) {
        this.assign_id = assign_id;
        this.teacher_id = teacher_id;
        this.course_id = course_id;
        this.department_id = department_id;
    }

    public String getAssign_id() {
        return assign_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setAssign_id(String assign_id) {
        this.assign_id = assign_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
}
