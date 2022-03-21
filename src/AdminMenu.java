import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author qihu
 */

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void adminMenu(){
        String choice;
        do {
            printAdminMenu();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    displayAllCustomers();
                    break;
                case "2":
                    displayAllRooms();
                    break;
                case "3":
                    displayAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    MainMenu.mainMenu();
                    break;
                default:
                    System.out.println("Invalid input. Please select again.");
            }
        } while (!choice.equals("5"));
    }

    private static void printAdminMenu() {
        System.out.println("--------------------\n"
                + "1. See all customers\n"
                + "2. See all rooms\n"
                + "3. See all reservations\n"
                + "4. Add a room\n"
                + "5. Back to main menu\n"
                + "--------------------\n"
                + "Please select a number for the menu option:");
    }

    private static void displayAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()){
            System.out.println("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()){
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void displayAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addRoom() {
        final Scanner sc = new Scanner(System.in);

        System.out.println("Enter room number:");
        String roomNumber = sc.nextLine();

        double roomPrice;
        do {
            System.out.println("Enter room price:");
            String s = sc.nextLine();
            try {
                roomPrice = Double.parseDouble(s);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid room price. Please enter a valid double number.");
            }
        } while (true);

        RoomType roomType;
        do {
            System.out.println("Enter room type: 1 for single bed, 2 for double bed.");
            String s = sc.nextLine();
            switch (s) {
                case "1":
                    roomType = RoomType.SINGLE;
                    break;
                case "2":
                    roomType = RoomType.DOUBLE;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    roomType = null;
            }
        } while (roomType == null);
        final Room room = new Room(roomNumber, roomPrice, roomType);
        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Room added successfully!");
    }


}
