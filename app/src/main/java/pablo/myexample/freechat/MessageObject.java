package pablo.myexample.freechat;

public class MessageObject {

    private String messageName, messageText, messageDate, messageTime, messageId;

    public MessageObject() {
    }

    public MessageObject(String messageName, String messageText, String messageDate, String messageTime, String messageId){
        this.messageName = messageName;
        this.messageText = messageText;
        this.messageDate = messageDate;
        this.messageTime = messageTime;
        this.messageId = messageId;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
