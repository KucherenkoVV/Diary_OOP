package tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class TaskMethods {
    public static Map<Integer, Tasks> tasksMap = new HashMap<>();

    public static Set<Tasks> deletedTasks = new HashSet<>();

    public static void printMenu() {
        System.out.println(
                "1. Добавить задачу\n" +
                        "2. Удалить задачу\n" +
                        "3. Получить задачу на указанный день\n" +
                        "4. Вывести все задачи\n" +
                        "5. Вывести все удаленные задачи\n" +
                        "6. Отредактировать название и описание задачи\n" +
                        "7. Получить все задачи за указанный период\n" +
                        "0. Выход"
        );
    }

    public static void inputTask(Scanner scanner) {
        System.out.print("Введите название задачи:");
        String taskName = scanner.next();
        System.out.print("Введите описание задачи:");
        String taskDescriptor = scanner.next();
        System.out.println("Введите дату задачи в формате yyyy-mm-dd: ");
        LocalDate date = validateDate(scanner);
        System.out.println("Введите время задачи в формате hh-mm: ");
        LocalTime time = validateTime(scanner);
        System.out.print("Введите тип задачи: \n" +
                "1.Однократная\n" +
                "2.Ежедневная\n" +
                "3.Еженедельная\n" +
                "4.Ежемесячная\n" +
                "5.Ежегодная\n");
        int type = scanner.nextInt();
        System.out.println("Выберите назначение задачи: \n" +
                "1.Рабочая\n" +
                "2.Личная");
        int worked = scanner.nextInt();
        Tasks task = new Tasks(taskName, taskDescriptor, date, time, setTypeOfTask(type), setWorked(worked));
        tasksMap.put(task.getId(), task);
    }


    public static void deleteTask(Scanner scanner) {
        System.out.print("Введите ID задачи:");
        int id = scanner.nextInt();
        if (tasksMap.containsKey(id)) {
            deletedTasks.add(tasksMap.get(id));
            tasksMap.remove(id);
            System.out.println("Задача с ID " + id + " удалена.");
        } else {
            System.out.println("Задача не найдена.");
        }
    }

    public static void printDeletedTasks() {
        if (deletedTasks.size() > 0) {
            for (Tasks deleted : deletedTasks) {
                System.out.println(deleted);
            }
        } else {
            System.out.println("Удаленные задачи отсутствуют.");
        }
    }

    public static void changeTask(Scanner scanner) {
        System.out.println("Введите id задачи для редактирования: ");
        int id = scanner.nextInt();
        for (Map.Entry<Integer, Tasks> task : tasksMap.entrySet()) {
            if (tasksMap.containsKey(id)) {
                System.out.println("Введите новое название задачи: ");
                String title = scanner.next();
                task.getValue().setTitle(title);
                System.out.println("Введите новое описание задачи: ");
                String desc = scanner.next();
                task.getValue().setDescription(desc);
                System.out.println("Задача успешно отредактирована.");
            } else {
                System.out.println("Задача с данным id отсутствует.");
            }
        }
    }

    public static void printAllTasks() {
        if (!tasksMap.isEmpty()) {
            for (Map.Entry<Integer, Tasks> taskMap : getTasksMap().entrySet()) {
                System.out.println(taskMap);
            }
        } else {
            throw new RuntimeException("Нет добавленных задач.");
        }
    }

    public static void printTaskByDate(Scanner scanner) {
        System.out.println("Введите дату для получения задач в формате yyyy-mm-dd: ");
        LocalDate localDate = validateDate(scanner);

        System.out.println("Задачи на указанный день: ");
        if(!tasksMap.isEmpty()) {
            for (Tasks task : tasksMap.values()) {
                while (localDate.isAfter(task.getDate())) {
                    if (task.getTypeOfTask().equals(TypeOfTask.DAILY_TASK)) {
                        task.setDate(task.getDate().plusDays(1));
                    }
                    if (task.getTypeOfTask().equals(TypeOfTask.WEEKLY_TASK)) {
                        task.setDate(task.getDate().plusWeeks(1));
                    }
                    if (task.getTypeOfTask().equals(TypeOfTask.MONTHLY_TASK)) {
                        task.setDate(task.getDate().plusMonths(1));
                    }
                    if (task.getTypeOfTask().equals(TypeOfTask.YEARLY_TASK)) {
                        task.setDate(task.getDate().plusYears(1));
                    }
                    if (localDate.equals(task.getDate())) {
                        System.out.println(task);
                    }
                }
            }
        } else {
            System.out.println("Задачи не найдены.");
        }
    }

    protected static boolean setWorked(int worked) {
        boolean isWorked;
        if (worked == 1) {
            isWorked = true;
        } else if (worked == 2) {
            isWorked = false;
        } else {
            throw new IllegalArgumentException("Некорректно выбран тип задачи!");
        }
        return isWorked;
    }

    public static void getTasksByPeriod(Scanner scanner) {
        System.out.println("Введите начальное значение периода в формате yyyy-mm-dd: ");
        LocalDate start = validateDate(scanner);
        System.out.println("Введите конечное значение периода в формате yyyy-mm-dd: ");
        LocalDate end = validateDate(scanner);
        for (Map.Entry<Integer, Tasks> task : tasksMap.entrySet()) {
            if (task.getValue().getDate().isAfter(start) && task.getValue().getDate().isBefore(end)) {
                System.out.println(task);
            } else {
                System.out.println("Задачи в указанном периоде отсутствуют.");
            }
        }
    }

    protected static TypeOfTask setTypeOfTask(int typeTask) {
        TypeOfTask type = null;
        switch (typeTask) {
            case 1:
                type = TypeOfTask.SINGLE_TASK;
                break;
            case 2:
                type = TypeOfTask.DAILY_TASK;
                break;
            case 3:
                type = TypeOfTask.WEEKLY_TASK;
                break;
            case 4:
                type = TypeOfTask.MONTHLY_TASK;
                break;
            case 5:
                type = TypeOfTask.YEARLY_TASK;
                break;
            default:
                throw new IllegalArgumentException("Неверно указан тип задачи");
        }
        return type;
    }

    protected static String validateStringParameters(String value) {
        if (value == null || value.isBlank() || value.isEmpty()) {
            throw new IllegalArgumentException("Некорректно введено значение");
        } else {
            return value;
        }
    }

    protected static LocalDate validateDate(Scanner scanner) {
        LocalDate localDate;
        try {
            String date = scanner.next();
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (localDate.isAfter(LocalDate.now())) {
                return localDate;
            } else {
                System.out.println("Дата из прошлого. Введите корректную дату.");
                date = scanner.next();
                localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Дата введена некорректно.");
        }
        return localDate;
    }

    protected static LocalTime validateTime(Scanner scanner) {
        LocalTime time;
        try {
            String timeTask = scanner.next();
            time = LocalTime.parse(timeTask, DateTimeFormatter.ofPattern("HH-mm"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Время введено некорректно.");
        }
        return time;
    }

    protected static Map<Integer, Tasks> getTasksMap() {
        return tasksMap;
    }

}


