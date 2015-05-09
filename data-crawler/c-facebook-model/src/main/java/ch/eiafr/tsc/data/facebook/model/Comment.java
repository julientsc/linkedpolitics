package ch.eiafr.tsc.data.facebook.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2546376026455251645L;
	private String id;
	private From from;
	private String message;
	private boolean can_remove;
	private Date created_time;
	private long like_count;
	private boolean user_likes;
	private List<MessageTag> message_tags;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		System.out.println("Unkown property:" + this.getClass().getName() + ","
				+ name);
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isCan_remove() {
		return can_remove;
	}

	public void setCan_remove(boolean can_remove) {
		this.can_remove = can_remove;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public long getLike_count() {
		return like_count;
	}

	public void setLike_count(long like_count) {
		this.like_count = like_count;
	}

	public boolean isUser_likes() {
		return user_likes;
	}

	public void setUser_likes(boolean user_likes) {
		this.user_likes = user_likes;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public List<MessageTag> getMessage_tags() {
		return message_tags;
	}

	public void setMessage_tags(List<MessageTag> message_tags) {
		this.message_tags = message_tags;
	}

	
}