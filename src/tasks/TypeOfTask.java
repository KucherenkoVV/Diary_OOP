package tasks;

public enum TypeOfTask {
    SINGLE_TASK("однократная"), DAILY_TASK("ежедневная"), WEEKLY_TASK("еженедельная"), MONTHLY_TASK("ежемесячная"),
    YEARLY_TASK("ежегодная");

    private final String typeTask;

    TypeOfTask(String typeTask) {
        this.typeTask = typeTask;
    }

    @Override
    public String toString() {
        return typeTask ;
    }
}
