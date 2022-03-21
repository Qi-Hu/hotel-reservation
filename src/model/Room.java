package model;

import java.util.Objects;

/**
 * @author qihu
 */

public class Room implements IRoom{
    protected String roomNumber;
    protected Double roomPrice;
    protected RoomType roomType;

    public Room(String roomNumber, Double roomPrice, RoomType roomType){
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    public String getRoomNumber(){
        return roomNumber;
    }
    public Double getRoomPrice(){
        return roomPrice;
    }
    public RoomType getRoomType(){
        return roomType;
    }
    public Boolean isFree(){
        return roomPrice.equals(0.0);
    }

    @Override
    public String toString(){
        return "Room number: " + roomNumber + ", price: " + roomPrice + ", type: " + roomType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Room)) {
            return false;
        }
        Room room = (Room) obj;
        return Objects.equals(this.roomNumber, room.roomNumber);
    }

    //@Override
    public int hashcode(){
        return roomNumber.hashCode();
    }
}
