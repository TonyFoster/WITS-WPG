package tw.mcark.tony.attendance;

import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.mcark.tony.AppException;

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
}
