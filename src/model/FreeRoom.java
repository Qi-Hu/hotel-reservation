package model;

/**
 * @author qihu
 */

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType roomType){
        super(roomNumber, 0.0, roomType);
//        this.roomNumber = roomNumber;
//        this.roomPrice = 0.0;
//        this.roomType = roomType;
    }
    @Override
    public String toString(){
        return "Free room: " + super.toString();
    }
}
