package tw.mcark.tony.attendance;

import java.time.Instant;
import java.util.Calendar;

public class AttendanceRecord {
    private String name;
    private String office;
    private AttendanceType type;
    private Instant createdAt;
    private Calendar leaveStart;
    private Calendar leaveEnd;
    private String leaveReason;

    public AttendanceRecord(String name, String office, AttendanceType type, Instant createdAt, Calendar leaveStart, Calendar leaveEnd, String leaveReason) {
        this.name = name;
        this.office = office;
        this.type = type;
        this.createdAt = createdAt;
        this.leaveStart = leaveStart;
        this.leaveEnd = leaveEnd;
        this.leaveReason = leaveReason;
    }

    public AttendanceRecord(AttendanceRequest request) {
        this.name = request.getName();
        this.office = request.getOffice();
        this.type = request.getType();
        this.createdAt = Instant.now();
    }

    public String getName() {
        return name;
    }

    public String getOffice() {
        return office;
    }

    public AttendanceType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Calendar getLeaveStart() {
        return leaveStart;
    }

    public Calendar getLeaveEnd() {
        return leaveEnd;
    }

    public String getLeaveReason() {
        return leaveReason;
    }
}
