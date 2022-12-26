package tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Scanner;

import static tasks.TaskMethods.*;

public class Tasks {
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private TypeOfTask typeOfTask;
    private static int count = 0;
    private int id;
    private boolean isWorked;
    private boolean isDeleted;


    Scanner scanner = new Scanner(System.in);


    public Tasks(String title, String description, LocalDate date, LocalTime time, TypeOfTask typeOfTask, boolean isWorked) {
        setTitle(title);
        setDescription(description);
        this.typeOfTask = typeOfTask;
        this.date = date;
        this.time = time;
        Tasks.count++;
        this.id = count;
        this.isWorked = isWorked;
        this.isDeleted = false;
    }

    public void setWorked(boolean worked) {
        isWorked = worked;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public TypeOfTask getTypeOfTask() {
        return typeOfTask;
    }

    public void setTitle(String title) {
        this.title = validateStringParameters(title);
    }

    public void setDescription(String description) {
        this.description = validateStringParameters(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return id == tasks.id && title.equals(tasks.title) && description.equals(tasks.description) && date.equals(tasks.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, date, id);
    }

    @Override
    public String toString() {
        return "Задача (ID) [" + id +
                "]: название " + title +
                ", описание " + description +
                ", дата " + date + " " + time +
                ", тип: " +typeOfTask.toString() +
                ", " + (isWorked? "рабочая" : "личная")  + ".";
    }
}
