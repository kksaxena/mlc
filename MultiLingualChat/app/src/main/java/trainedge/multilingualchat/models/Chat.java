package trainedge.multilingualchat.models;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Chat {
    public String sender;
    public String receiver;
    public String senderUid;
    public String receiverUid;
    public String message;
    public long timestamp;
    public String lang;

    public Chat() {
    }

    public Chat(String sender, String receiver, String senderUid, String receiverUid, String message, long timestamp, String lang) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.message = message;
        this.timestamp = timestamp;
        this.lang = lang;

    }

//    public Chat(String sender, String receiver, String senderUid, String receiverUid, String message, long timestamp) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.senderUid = senderUid;
//        this.receiverUid = receiverUid;
//        this.message = message;
//        this.timestamp = timestamp;
//    }
}
