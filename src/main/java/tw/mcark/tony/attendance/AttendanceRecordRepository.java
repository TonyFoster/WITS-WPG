package tw.mcark.tony.attendance;

import tw.mcark.tony.DataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class AttendanceRecordRepository {

    public void saveAttendanceRecord(AttendanceRecord record) throws SQLException {
        String sql = "INSERT INTO attendance_records (name, office, type, created_at, leave_start, leave_end, leave_reason) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataSourceManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, record.getName());
            statement.setString(2, record.getOffice());
            statement.setString(3, record.getType().name());
            statement.setObject(4, record.getCreatedAt());
            statement.setObject(5, record.getLeaveStart());
            statement.setObject(6, record.getLeaveEnd());
            statement.setString(7, record.getLeaveReason());
            statement.executeUpdate();
        }
    }

    public boolean isUserCheckIn(String username, Calendar date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM attendance_records WHERE name = ? AND DATE(created_at) = ?";

        try (Connection connection = DataSourceManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            java.sql.Date sqlDate = new java.sql.Date(date.getTimeInMillis());
            statement.setDate(2, sqlDate);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }
}
