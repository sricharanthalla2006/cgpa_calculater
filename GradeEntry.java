package com.example.cgpa; // <-- Only ONE package declaration at the very top

import java.math.BigDecimal; // <-- ESSENTIAL import to resolve 'BigDecimal'

public class GradeEntry {
    private int id;
    private String studentId;
    private String term;
    private String subjectCode;
    private String subjectName;
    private BigDecimal credits;
    private String grade;
    private BigDecimal gradePoints;

    public GradeEntry() {}

    public GradeEntry(String studentId, String term, String subjectCode,
                      String subjectName, BigDecimal credits, String grade, BigDecimal gradePoints) {
        this.studentId = studentId;
        this.term = term;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.grade = grade;
        this.gradePoints = gradePoints;
    }

    // --- Getters and Setters (ensure all are present) ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public BigDecimal getCredits() { return credits; }
    public void setCredits(BigDecimal credits) { this.credits = credits; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public BigDecimal getGradePoints() { return gradePoints; }
    public void setGradePoints(BigDecimal gradePoints) { this.gradePoints = gradePoints; }
}