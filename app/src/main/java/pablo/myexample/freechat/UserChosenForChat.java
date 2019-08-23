package pablo.myexample.freechat;

public class UserChosenForChat {

    private String Name, Nickname;

    public UserChosenForChat() {
    }

    public UserChosenForChat(String Name, String Nickname){
        this.Name = Name;
        this.Nickname = Nickname;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }
}
