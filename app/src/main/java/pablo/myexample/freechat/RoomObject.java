package pablo.myexample.freechat;

import java.util.ArrayList;
import java.util.List;

public class RoomObject {

    private ArrayList<String> listOfNames, listOfIds, listOfUrls;

    RoomObject(){}

    public RoomObject(ArrayList<String> listOfNames, ArrayList<String> listOfIds, ArrayList<String> listOfUrls) {
        this.listOfNames = listOfNames;
        this.listOfIds = listOfIds;
        this.listOfUrls = listOfUrls;
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

    public ArrayList<String> getListOfUrls() {
        return listOfUrls;
    }

    public void setListOfUrls(ArrayList<String> listOfUrls) {
        this.listOfUrls = listOfUrls;
    }
}
