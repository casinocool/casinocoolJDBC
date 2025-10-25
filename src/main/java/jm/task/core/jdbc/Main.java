package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        //userService.dropUsersTable();
        userService.saveUser("Ivan","Dragunov",(byte)10);
        userService.saveUser("Anton","Pirozhkov",(byte)46);
        userService.saveUser("Александр","Белоголовский",(byte)20);
        userService.removeUserById(1);
       userService.getAllUsers().stream().forEach(user -> {
           System.out.println(user.getName()+" "+" "+user.getLastName()+" "+user.getAge());
       });
       userService.cleanUsersTable();
        userService.getAllUsers().stream().forEach(user -> {
            System.out.println(user.getName()+" "+" "+user.getLastName()+" "+user.getAge());
        });
    }
}
