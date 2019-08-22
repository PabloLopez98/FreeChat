package pablo.myexample.freechat;

public class UserChosenForChat {

    private String Name, Email;

    public UserChosenForChat() {
    }

    public UserChosenForChat(String Name, String Email){
        this.Name = Name;
        this.Email = Email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
