package tw.mcark.tony.attendance;

import tw.mcark.tony.AppException;

import java.util.HashMap;

public class AttendanceRequest {

    private static final HashMap<String, String> OFFICES = new HashMap<>();

    static {
        OFFICES.put("侯性男", "LaaS");
        OFFICES.put("劉仁傑", "ERP-A");
        OFFICES.put("吳印", "ERP-A");
        OFFICES.put("陳祈男", "應用");
        OFFICES.put("虞振華", "LaaS");
        OFFICES.put("盧宥銨", "LaaS");
        OFFICES.put("鐘學明", "應用");
        OFFICES.put("林昶翰", "ERP-B");
        OFFICES.put("沈羽碒", "ERP-B");
    }

    private String name;
    private AttendanceType type;

    public AttendanceRequest() {}

    public AttendanceRequest(String name, AttendanceType type) {
        if (!OFFICES.containsKey(name)) {
            throw new AppException("Invalid name");
        }
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return OFFICES.get(name);
    }

    public AttendanceType getType() {
        return type;
    }

    public void setType(AttendanceType type) {
        this.type = type;
    }
}
