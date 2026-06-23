package com.example.cgpa;

import javax.sql.DataSource;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    private final DataSource dataSource;

    public GradeDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Saves a single subject grade record into the student_cgpa table.
     */
    public void saveGrade(GradeEntry entry) throws SQLException {
        String sql = "INSERT INTO student_cgpa (student_id, term, subject_code, subject_name, credits, grade, grade_points) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entry.getStudentId());
            ps.setString(2, entry.getTerm());
            ps.setString(3, entry.getSubjectCode());
            ps.setString(4, entry.getSubjectName());
            ps.setBigDecimal(5, entry.getCredits());
            ps.setString(6, entry.getGrade());
            ps.setBigDecimal(7, entry.getGradePoints());

            ps.executeUpdate();
        }
    }

    /**
     * Fetches all grade entries for a given student ID.
     */
    public List<GradeEntry> fetchGrades(String studentId) throws SQLException {
        String sql = "SELECT student_id, term, subject_code, subject_name, credits, grade, grade_points "
                + "FROM student_cgpa WHERE student_id = ?";

        List<GradeEntry> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GradeEntry ge = new GradeEntry();
                    ge.setStudentId(rs.getString("student_id"));
                    ge.setTerm(rs.getString("term"));
                    ge.setSubjectCode(rs.getString("subject_code"));
                    ge.setSubjectName(rs.getString("subject_name"));
                    ge.setCredits(rs.getBigDecimal("credits"));
                    ge.setGrade(rs.getString("grade"));
                    ge.setGradePoints(rs.getBigDecimal("grade_points"));
                    list.add(ge);
                }
            }
        }
        return list;
    }

    /**
     * Saves the final CGPA for a student into the cgpa_results table.
     */
    public void saveFinalCGPA(String studentId, String term, BigDecimal cgpa) throws SQLException {
        String sql = "INSERT INTO cgpa_results (student_id, term, cgpa, calculated_at) VALUES (?, ?, ?, NOW())";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ps.setString(2, term);
            ps.setBigDecimal(3, cgpa);

            ps.executeUpdate();
        }
    }
}
