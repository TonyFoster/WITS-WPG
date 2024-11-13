package tw.mcark.tony;

import io.javalin.Javalin;
import tw.mcark.tony.attendance.AttendanceController;
import tw.mcark.tony.attendance.AttendanceService;

import java.util.Map;

public class App {
    public static void main(String[] args) {
        AttendanceService attendanceService = new AttendanceService();
        AttendanceController attendanceController = new AttendanceController(attendanceService);

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(corsPluginConfig -> {
                corsPluginConfig.addRule(corsRule -> corsRule.allowHost("http://localhost:4200", "tony19907051.com"));
            });
        }).start(7001);

        app.exception(AppException.class, (exception, context) -> {
            context.status(400);
            context.json(Map.of("error", exception.getMessage()));
        });

        app.post("/check", attendanceController::check);
    }
}