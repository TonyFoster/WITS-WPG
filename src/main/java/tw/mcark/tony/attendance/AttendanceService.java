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

//    public static void sendPushNotification(String token, String title, String body) {
//        String registrationToken = "YOUR_REGISTRATION_TOKEN";
//
//// See documentation on defining a message payload.
//        Message message = Message.builder().putData("score", "850").putData("time", "2:45").setToken(registrationToken).build();
//
//// Send a message to the device corresponding to the provided
//// registration token.
//        String response = null;
//        try {
//            response = FirebaseMessaging.getInstance().send(message);
//        } catch (FirebaseMessagingException e) {
//            throw new RuntimeException(e);
//        }
//// Response is a message ID string.
//        System.out.println("Successfully sent message: " + response);
//    }
//
//    public static void main(String[] args) {
//        sendPushNotification("token", "title", "body");
//    }

}

