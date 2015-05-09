package ch.eiafr.tsc.data.facebook.model;

import java.io.Serializable;
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
public class MessageTags implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8084102338151037998L;
	private Map<String, List<MessageTag>> messageTags = new HashMap<String, List<MessageTag>>();

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
	
	@JsonAnyGetter
	public Map<String, List<MessageTag>> getMessageTags() {
		return this.messageTags;
	}

	@JsonAnySetter
	public void setMessageTag(String name, List<MessageTag> value) {
		this.messageTags.put(name, value);
	}

}