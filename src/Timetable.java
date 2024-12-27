public class Timetable {
    private String timetableName;
    private String day;
    private String startTime;
    private String endTime;
    private String activityName;

    public Timetable(String timetableName, String day, String startTime, String endTime, String activityName) {
        this.timetableName = timetableName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityName = activityName;
    }

    public String getTimetableName() {
        return timetableName;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getActivityName() {
        return activityName;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "timetableName='" + timetableName + '\'' +
                ", day='" + day + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", activityName='" + activityName + '\'' +
                '}';
    }
}
