package controllers;

import java.util.Scanner;

public class MenuController {
    public void MenuController() {
        try {
            System.out.println("-----Welcome to Bati-cuisine-----\n1.Create a new project\n" +
                    "2.Show existing projects\n3.Calculate project cost\n4.Quit");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    createProject();
                    break;
                case 2 :
                    break;
                case 3 :
                    break;
                case 4 :
                    break;
                default:

            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void createProject() {
        try {
            System.out.println("Do you wish to search for an existing client ot to add a new project?\n1.Search for an existing client\n2.Add a new client\n0.return to main menu\n");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    break;
                case 2 :
                    break;
                case 0 :
                    break;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
