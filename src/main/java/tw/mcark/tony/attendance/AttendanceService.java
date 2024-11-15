package tw.mcark.tony.attendance;

import tw.mcark.tony.AppException;

import java.sql.SQLException;
import java.util.Calendar;

public class AttendanceService {

    private final AttendanceForm form = new AttendanceForm();
    private final AttendanceRecordRepository attendanceRecordRepository = new AttendanceRecordRepository();

    public boolean hasUserCheckedIn(String username, Calendar date) {
        try {
            return attendanceRecordRepository.isUserCheckIn(username, date);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AppException("Failed to save attendance record");
        }
    }

    public void check(AttendanceRequest request) {
        if (form.check(request)) {
            saveAttendanceRecord(new AttendanceRecord(request));
        } else {
            throw new AppException("Failed to check attendance");
        }
    }

    public void saveAttendanceRecord(AttendanceRecord record) {
        try {
            attendanceRecordRepository.saveAttendanceRecord(record);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AppException("Failed to save attendance record");
        }
    }

}

