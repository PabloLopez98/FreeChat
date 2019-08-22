package pablo.myexample.freechat;

import android.graphics.Bitmap;

public class ChatPreviewCardObject{

    private Bitmap profile_image;
    private String username, message_preview, date, time, notifications;

    public ChatPreviewCardObject(Bitmap profile_image, String username, String message_preview, String date, String time, String notifications) {
        this.profile_image = profile_image;
        this.username = username;
        this.message_preview = message_preview;
        this.date = date;
        this.time = time;
        this.notifications = notifications;
    }

    public ChatPreviewCardObject() {
    }

    public Bitmap getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(Bitmap profile_image) {
        this.profile_image = profile_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage_preview() {
        return message_preview;
    }

    public void setMessage_preview(String message_preview) {
        this.message_preview = message_preview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }
}
