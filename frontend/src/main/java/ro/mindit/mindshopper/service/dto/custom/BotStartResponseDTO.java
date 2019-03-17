package ro.mindit.mindshopper.service.dto.custom;

import java.util.Objects;

public class BotStartResponseDTO implements java.io.Serializable{

    private String conversationId;
    private String token;
    private String expires_in;
    private String streamUrl;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotStartResponseDTO that = (BotStartResponseDTO) o;
        return Objects.equals(conversationId, that.conversationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId);
    }

    @Override
    public String toString() {
        return "BotStartResponseDTO{" +
            "conversationId='" + conversationId + '\'' +
            ", token='" + token + '\'' +
            ", expires_in='" + expires_in + '\'' +
            ", streamUrl='" + streamUrl + '\'' +
            '}';
    }
}
