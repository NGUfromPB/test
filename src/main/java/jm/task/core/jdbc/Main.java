package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService =new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("iv","mok", (byte) 4);
        System.out.println("User iv добавлен в базу данных");
        userService.saveUser("iv2","mok2", (byte) 42);
        System.out.println("User iv2 добавлен в базу данных");
        userService.saveUser("iv3","mok3", (byte) 43);
        System.out.println("User iv3 добавлен в базу данных");
        userService.saveUser("iv1","mok1", (byte) 5);
        System.out.println("User iv1 добавлен в базу данных");
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
