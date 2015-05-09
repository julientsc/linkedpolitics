
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DirectMessages  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9035490161573990613L;
	@SerializedName("/direct_messages/show")
    @Expose
    private DirectMessagesShow directMessagesShow;
    @SerializedName("/direct_messages/sent_and_received")
    @Expose
    private DirectMessagesSentAndReceived directMessagesSentAndReceived;
    @SerializedName("/direct_messages/sent")
    @Expose
    private DirectMessagesSent directMessagesSent;
    @SerializedName("/direct_messages")
    @Expose
    private DirectMessages_ directMessages;

    public DirectMessagesShow getDirectMessagesShow() {
        return directMessagesShow;
    }

    public void setDirectMessagesShow(DirectMessagesShow directMessagesShow) {
        this.directMessagesShow = directMessagesShow;
    }

    public DirectMessagesSentAndReceived getDirectMessagesSentAndReceived() {
        return directMessagesSentAndReceived;
    }

    public void setDirectMessagesSentAndReceived(DirectMessagesSentAndReceived directMessagesSentAndReceived) {
        this.directMessagesSentAndReceived = directMessagesSentAndReceived;
    }

    public DirectMessagesSent getDirectMessagesSent() {
        return directMessagesSent;
    }

    public void setDirectMessagesSent(DirectMessagesSent directMessagesSent) {
        this.directMessagesSent = directMessagesSent;
    }

    public DirectMessages_ getDirectMessages() {
        return directMessages;
    }

    public void setDirectMessages(DirectMessages_ directMessages) {
        this.directMessages = directMessages;
    }

}
