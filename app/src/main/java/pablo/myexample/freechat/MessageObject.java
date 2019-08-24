package pablo.myexample.freechat;

public class MessageObject {

    private String messageName, messageText;

    public MessageObject() {
    }

    public MessageObject(String messageName, String messageText){
        this.messageName = messageName;
        this.messageText = messageText;
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
}
