package diary;

import tasks.TaskMethods;
import java.util.Scanner;
import static tasks.TaskMethods.*;

public class DiaryRun {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                TaskMethods.printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            deleteTask(scanner);
                            break;
                        case 3:
                            printTaskByDate(scanner);
                            break;
                        case 4:
                            printAllTasks();
                            break;
                        case 5:
                            printDeletedTasks();
                            break;
                        case 6:
                            changeTask(scanner);
                            break;
                        case 7:
                            getTasksByPeriod(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    System.out.println("Пункт меню выбран некорректно!");
                    scanner.next();
                }
            }
        }
    }


}
