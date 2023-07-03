package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Герман", "Жуков", (byte) 24);
        userService.saveUser("Илья", "Порываев", (byte) 26);
        userService.saveUser("Сергей", "Струков", (byte) 35);
        userService.saveUser("Наталья", "Рыбкина", (byte) 45);

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println("User: " + user.getName() + " " + user.getLastName() + " " + user.getAge());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeConnections();
    }
}
