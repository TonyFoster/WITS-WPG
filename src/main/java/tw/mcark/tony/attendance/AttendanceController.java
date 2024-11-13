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
        AttendanceRequest request = context.bodyAsClass(AttendanceRequest.class);

        if (request.getName() == null || request.getName().isBlank()) {
            throw new AppException("Name is required");
        }
        Calendar calendar = Calendar.getInstance();
        boolean isCheckedIn = attendanceService.hasUserCheckedIn(request.getName(), calendar);
        boolean isBefore1030 = calendar.get(Calendar.HOUR_OF_DAY) < 10 || (calendar.get(Calendar.HOUR_OF_DAY) == 10 && calendar.get(Calendar.MINUTE) < 30);
        request.setType(isCheckedIn || isBefore1030 ? AttendanceType.CHECK_IN : AttendanceType.CHECK_OUT);
        attendanceService.check(request);
        context.json(Map.of("success", "Request completed"));
    }

    public void dbTest(@NotNull Context context) {
        AttendanceRecordRepository repository = new AttendanceRecordRepository();
        try {
            repository.saveAttendanceRecord(new AttendanceRecord(new AttendanceRequest("劉仁傑", AttendanceType.CHECK_IN)));
            boolean b = repository.isUserCheckIn("劉仁傑", Calendar.getInstance());
            System.out.println(b);
            context.json(Map.of("success", "DB test completed"));
        } catch (Exception e) {
            logger.error("DB test failed", e);
            throw new AppException("DB test failed");
        }
    }
}
