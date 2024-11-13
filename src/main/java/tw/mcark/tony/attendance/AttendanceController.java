package tw.mcark.tony.attendance;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.mcark.tony.AppException;
import tw.mcark.tony.DataSourceManager;

import java.util.Calendar;
import java.util.Map;

public class AttendanceController {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void check(Context context) {
        String name = context.formParam("name");

        if (name == null || name.isBlank()) {
            throw new AppException("Name is required");
        }
//        boolean isCheckedIn = attendanceService.hasUserCheckedIn(name, Calendar.getInstance());
        boolean isCheckedIn = false;
        AttendanceRequest request = new AttendanceRequest(name, isCheckedIn ? AttendanceType.CHECK_OUT : AttendanceType.CHECK_IN);
        attendanceService.check(request);
        context.json(Map.of("success", "Request completed"));

    }

    public void dbTest(@NotNull Context context) {
        AttendanceRecordRepository repository = new AttendanceRecordRepository();
        try {
            repository.saveAttendanceRecord(new AttendanceRecord(new AttendanceRequest("Tony", AttendanceType.CHECK_IN)));
            boolean b = repository.isUserCheckIn("Tony", Calendar.getInstance());
            System.out.println(b);
            context.json(Map.of("success", "DB test completed"));
        } catch (Exception e) {
            logger.error("DB test failed", e);
            throw new AppException("DB test failed");
        }
    }
}
