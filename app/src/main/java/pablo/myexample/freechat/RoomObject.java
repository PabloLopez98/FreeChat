package pablo.myexample.freechat;

import java.util.ArrayList;
import java.util.List;

public class RoomObject {

    private ArrayList<String> listOfNames, listOfIds;
    private String roomId;

    RoomObject() {
    }

    public RoomObject(ArrayList<String> listOfNames, ArrayList<String> listOfIds, String roomId) {
        this.listOfNames = listOfNames;
        this.listOfIds = listOfIds;
        this.roomId = roomId;
    }

    public ArrayList<String> getListOfNames() {
        return listOfNames;
    }

    public void setListOfNames(ArrayList<String> listOfNames) {
        this.listOfNames = listOfNames;
    }

    public ArrayList<String> getListOfIds() {
        return listOfIds;
    }

    public void setListOfIds(ArrayList<String> listOfIds) {
        this.listOfIds = listOfIds;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
