package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qihu
 */

public class ReservationService {
    private static final ReservationService instance = new ReservationService();
    private final int RECOMMENDED_PLUS_DAYS = 7;
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    private ReservationService(){}

    public static ReservationService getInstance(){
        return instance;
    }

    public void addRoom(IRoom room){
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms(){
        return rooms.values();
    }

    public Collection<Reservation> getCustomerReservation(Customer customer){
        return reservations.get(customer.getEmail());
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOUtDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOUtDate);
        Collection<Reservation> customerReservations = getCustomerReservation(customer);
        if(customerReservations == null){
            customerReservations = new ArrayList<>();
        }
        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> notAvailableRooms = new HashSet<>();
        for(Collection<Reservation> customerReservations: reservations.values()){
            for(Reservation reservation: customerReservations){
                if(reservation.getCheckInDate().before(checkOutDate) && reservation.getCheckOutDate().after(checkInDate)){
                    notAvailableRooms.add(reservation.getRoom());
                }
            }
        }
        return rooms.values().stream().filter(room -> !notAvailableRooms.contains(room)).collect(Collectors.toList());
    }

    public Collection<IRoom> findAlternativeRooms(Date checkInDate, Date checkOutDate){
        return findRooms(addPlusDays(checkInDate), addPlusDays(checkOutDate));
    }

    public Date addPlusDays(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, RECOMMENDED_PLUS_DAYS);
        return c.getTime();
    }

    public void printAllReservation(){
        Collection<Reservation> allReservations = new ArrayList<>();
        for(Collection<Reservation> customerReservations: reservations.values()){
            allReservations.addAll(customerReservations);
        }
        if(allReservations.isEmpty()){
            System.out.println("No reservations found.");
        }else{
            for(Reservation reservation: allReservations){
                System.out.println(reservation);
            }
        }

    }
}
