package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable(); //создание таблицы users

        userService.saveUser("ivan", "Petrov", (byte) 33); //добавление юзеров
        userService.saveUser("John", "Smith", (byte) 22);
        userService.saveUser("Henry", "Jones", (byte) 18);
        userService.saveUser("Donald", "Trump", (byte) 72);

        for (User u:userService.getAllUsers()){ // Получение всех User из базы и вывод в консоль
            System.out.println(u.toString());
        }

        userService.cleanUsersTable(); //Очистка таблицы User(ов)

        userService.dropUsersTable(); //Удаление таблицы
    }
}
