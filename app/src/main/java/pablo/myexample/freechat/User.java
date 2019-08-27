package pablo.myexample.freechat;

public class User {

    private String userId, profileUrl, userName, nickName;

    User(){}

    public User(String userId, String profileUrl, String userName, String nickName) {
        this.userId = userId;
        this.profileUrl = profileUrl;
        this.userName = userName;
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
