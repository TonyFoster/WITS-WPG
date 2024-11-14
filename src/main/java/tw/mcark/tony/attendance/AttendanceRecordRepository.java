package tw.mcark.tony.attendance;

import tw.mcark.tony.DataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public List<AttendanceRecord> getAttendanceRecords(String name) {
        String sql = "SELECT * FROM attendance_records WHERE name = ?";
        List<AttendanceRecord> records = new ArrayList<>();
        try (Connection connection = DataSourceManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                records.add(new AttendanceRecord(
                        resultSet.getString("name"),
                        resultSet.getString("office"),
                        AttendanceType.valueOf(resultSet.getString("type")),
                        resultSet.getTimestamp("created_at").toInstant(),
                        resultSet.getObject("leave_start", Calendar.class),
                        resultSet.getObject("leave_end", Calendar.class),
                        resultSet.getString("leave_reason")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
