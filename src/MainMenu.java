import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

/**
 * @author qihu
 */

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public static void mainMenu(){
        System.out.println("Welcome to the Hotel Reservation Application!");
        String s;
        do {
            printMainMenu();
            Scanner sc = new Scanner(System.in);
            s = sc.nextLine();
            switch (s) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservation();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.adminMenu();
                    break;
                case "5":
                    System.out.println("Exit application.");
                    break;
                default:
                    System.out.println("Invalid input. Please select again.");
            }
        }while(!s.equals("5"));

    }

    public static void printMainMenu(){
        System.out.println("--------------------\n"
                + "1. Find and reserve a room\n"
                + "2. See my reservations\n"
                + "3. Create an account\n"
                + "4. Admin\n"
                + "5. Exit\n"
                + "--------------------\n"
                + "Please select a number for the menu option:");
    }

    private static void findAndReserveRoom(){
        final Scanner sc = new Scanner(System.in);
        Date checkInDate;
        Date checkOutDate;
        do {
            System.out.println("Enter check-in date mm/dd/yyyy:");
            checkInDate = getInputDate(sc);
            System.out.println("Enter check-out date mm/dd/yyyy:");
            checkOutDate = getInputDate(sc);
            if (checkInDate.before(checkOutDate)) {
                break;
            } else {
                System.out.println("Invalid dates. Please enter again.");
            }
        } while(true);
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if(!availableRooms.isEmpty()){
            reserveRoom(sc, checkInDate, checkOutDate, availableRooms);
        }else{
            Date alternativeCheckIn = hotelResource.addPlusDays(checkInDate);
            Date alternativeCheckOut = hotelResource.addPlusDays(checkOutDate);
            Collection<IRoom> alternativeRooms = hotelResource.findARoom(alternativeCheckIn, alternativeCheckOut);
            if(!alternativeRooms.isEmpty()){
                System.out.println("We only found rooms on alternative days: " + DATE_FORMAT.format(alternativeCheckIn) + "---" + DATE_FORMAT.format(alternativeCheckOut));
                reserveRoom(sc, alternativeCheckIn, alternativeCheckOut, alternativeRooms);
            }else{
                System.out.println("No rooms found.");
            }
        }
    }

    private static Date getInputDate(Scanner sc){
        Date date;
        do {
            try {
                String s = sc.nextLine();
                date = DATE_FORMAT.parse(s);
            } catch (ParseException e) {
                System.out.println("Invalid input. Please try again.");
                date = null;
            }
        }while(date == null);
        return date;
    }

    private static void reserveRoom(Scanner sc, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms){
        rooms.forEach(System.out::println);
        do {
            System.out.println("Would you like to book? y/n");
            String bookRoom = sc.nextLine();
            if (bookRoom.equals("y")) {
                do {
                    System.out.println("Do you have an account with us? y/n");
                    String hasAccount = sc.nextLine();
                    if (hasAccount.equals("y")) {
                        System.out.println("Please enter your email.");
                        String customerEmail = sc.nextLine();
                        if (hotelResource.getCustomer(customerEmail) == null) {
                            System.out.println("Account not found. Please try again.");
                        } else {
                            System.out.println("Please enter the number of the room you want to book:");
                            String roomNumber = sc.nextLine();
                            if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))){
                                IRoom room = hotelResource.getRoom(roomNumber);
                                Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                                System.out.println("Reservation created successfully!");
                                System.out.println(reservation);
                                return;
                            } else {
                                System.out.println("Room not available. Please try again.");
                            }
                            break;
                        }
                    } else {
                        System.out.println("Please create an account first.");
                        return;
                    }
                } while (true);
            } else if ((bookRoom.equals("n"))) {
                return;
            }
        } while (true);
    }

    private static void seeMyReservation() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your email:");
        String customerEmail = sc.nextLine();
        Customer customer = hotelResource.getCustomer(customerEmail);
        if (customer == null) {
            System.out.println("Account not found. Please create one first.");
            return;
        }
        Collection<Reservation> reservations = hotelResource.getCustomerReservations(customerEmail);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            reservations.forEach(System.out::println);
        }
    }

    private static void createAccount() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your first name:");
        String firstName = sc.nextLine();
        System.out.println("Please enter your last name:");
        String lastName = sc.nextLine();
        do {
            System.out.println("Please enter your email name@domain.com:");
            String customerEmail = sc.nextLine();
            try {
                hotelResource.createACustomer(customerEmail, firstName, lastName);
                System.out.println("Account created successfully!");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid email. Please try again");
            }
        } while (true);

    }

}
