package pablo.myexample.freechat;

import android.graphics.Bitmap;

public class ChatPreviewCardObject {

    private String username, message_preview, date, time, notifications, url;

    public ChatPreviewCardObject(String username, String message_preview, String date, String time, String notifications, String url) {
        this.username = username;
        this.message_preview = message_preview;
        this.date = date;
        this.time = time;
        this.notifications = notifications;
        this.url = url;
    }

    public ChatPreviewCardObject() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
