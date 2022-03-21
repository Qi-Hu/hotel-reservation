package model;

/**
 * @author qihu
 */

public interface IRoom{
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public Boolean isFree();
}
