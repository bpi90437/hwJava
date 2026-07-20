package com.bpi.userservice.menu;

import com.bpi.userservice.entity.User;
import com.bpi.userservice.exception.UserNotFoundException;
import com.bpi.userservice.exception.ValidationException;
import com.bpi.userservice.service.UserService;


import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner=new Scanner(System.in);
    private final UserService userService;

    public ConsoleMenu(UserService userService) {
        this.userService=userService;

    }
    public void start(){
        while(true) {
            try {


            showMenu();
            int choice = readChoice();
            switch (choice) {
                case 1 -> addUser();
                case 2 -> showAllUsers();
                case 3 -> findUser();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> {
                    exit();
                    return;
                }
                default -> System.out.println("Неверный выбор");

            }
        }catch (RuntimeException e) {
                System.out.println(
                        "Ошибка: " + e.getMessage()
                );
            }
        }
    }
    private void showMenu(){
        System.out.println("""
                    ===== USER SERVICE =====
                    1. Добавить пользователя
                    2. Показать всех пользователей
                    3. Найти пользователя по id
                    4. Обновить пользователя
                    5. Удалить пользователя
                    0. Выход
                    """);
    }
    private int readChoice(){
        while(true){
            System.out.print("Выбор: ");
            if (scanner.hasNextInt()){
                return scanner.nextInt();
            }else {
                System.out.println("Введите число");
                scanner.nextLine();
            }
        }
    }
    private  void addUser() {
        try {


            scanner.nextLine();

            System.out.print("Имя: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Возраст: ");
            int age = scanner.nextInt();

            User user = new User(name, email, age);

            userService.save(user);

            System.out.println("Создан: " + user);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
    private  void showAllUsers(){
        List<User> users = userService.findAll();

        users.forEach(System.out::println);
    }

    private  void  findUser(){
        try {
            System.out.print("ID: ");

        Long id = scanner.nextLong();

        User user = userService.findById(id);
        System.out.println(user);
    }catch (UserNotFoundException e){
        System.out.println(e.getMessage());
        }
    }

    private  void  updateUser() {
        try {


            System.out.print("ID: ");
            Long id = scanner.nextLong();

            User user = userService.findById(id);

            if (user != null) {

                scanner.nextLine();

                System.out.print("Новое имя: ");
                user.setName(scanner.nextLine());

                System.out.print("Новый email: ");
                user.setEmail(scanner.nextLine());

                System.out.print("Новый возраст: ");
                user.setAge(scanner.nextInt());

                userService.update(user);

                System.out.println("Обновлено");
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }

    }
        private  void  deleteUser(){
        try {

                System.out.print("ID: ");

            Long id = scanner.nextLong();
            userService.delete(id);

            System.out.println("Удалено");
        }catch (UserNotFoundException e){
        System.out.println(e.getMessage());
        }
        }

    private  void  exit (){
        scanner.close();

        System.out.println("До свидания!");
    }

}


