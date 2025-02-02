package tw.mcark.tony;

import io.javalin.Javalin;
import tw.mcark.tony.attendance.AttendanceController;
import tw.mcark.tony.attendance.AttendanceService;
import tw.mcark.tony.chatbot.ChatBotController;

import java.util.Map;

public class App {
    public static void main(String[] args) {
        AttendanceService attendanceService = new AttendanceService();
        AttendanceController attendanceController = new AttendanceController(attendanceService);
        ChatBotController chatBotController = new ChatBotController();

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(corsPluginConfig -> {
                corsPluginConfig.addRule(corsRule -> corsRule.allowHost("http://localhost:4200", "https://wits.tony19907051.com", "https://wits.tony-liu.tw"));
            });
        }).start(7001);

        app.exception(AppException.class, (exception, context) -> {
            context.status(400);
            context.json(Map.of("error", exception.getMessage()));
        });

        app.post("/wits/check", attendanceController::check);
        app.get("/wits/records", attendanceController::records);
        app.get("/wits/lastCheckTime", attendanceController::lastCheckTime);
        app.get("/wits/db-test", attendanceController::dbTest);
        app.post("/wits/ask", chatBotController::ask);
    }
}