package com.example.studentinformationsystem;

import java.sql.Date;

public class Enrollement {
    private String Enroll_id;
    private String Student_id;
    private  int Course_id;
    private Date Enroll_date;

    public Enrollement(String enroll_id, String student_id, int course_id, Date enroll_date) {
        Enroll_id = enroll_id;
        Student_id = student_id;
        Course_id = course_id;
        Enroll_date = enroll_date;
    }

    public void setEnroll_id(String enroll_id) {
        Enroll_id = enroll_id;
    }

    public void setStudent_id(String student_id) {
        Student_id = student_id;
    }

    public void setCourse_id(int course_id) {
        Course_id = course_id;
    }

    public void setEnroll_date(Date enroll_date) {
        Enroll_date = enroll_date;
    }

    public String getEnroll_id() {
        return Enroll_id;
    }

    public String getStudent_id() {
        return Student_id;
    }

    public int getCourse_id() {
        return Course_id;
    }

    public Date getEnroll_date() {
        return Enroll_date;
    }
}
