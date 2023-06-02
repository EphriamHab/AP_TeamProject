
package com.example.studentinformationsystem;
import javafx.beans.property.*;

public class StudentResult {
    private final StringProperty courseId;
    private final StringProperty courseName;
    private final IntegerProperty creditHour;
    private final StringProperty letterGrade;
    private final DoubleProperty numberGrade;

    public StudentResult(String courseId, String courseName, double numberGrade, int creditHour, String letterGrade) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseName = new SimpleStringProperty(courseName);
        this.creditHour = new SimpleIntegerProperty(creditHour);
        this.letterGrade = new SimpleStringProperty(letterGrade);
        this.numberGrade = new SimpleDoubleProperty(numberGrade);
    }

    // Getters and Setters

    public String getCourseId() {
        return courseId.get();
    }

    public StringProperty courseIdProperty() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId.set(courseId);
    }

    public String getCourseName() {
        return courseName.get();
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public int getCreditHour() {
        return creditHour.get();
    }

    public IntegerProperty creditHourProperty() {
        return creditHour;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour.set(creditHour);
    }

    public String getLetterGrade() {
        return letterGrade.get();
    }

    public StringProperty letterGradeProperty() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade.set(letterGrade);
    }

    public double getNumberGrade() {
        return numberGrade.get();
    }

    public DoubleProperty numberGradeProperty() {
        return numberGrade;
    }

    public void setNumberGrade(double numberGrade) {
        this.numberGrade.set(numberGrade);
    }
}