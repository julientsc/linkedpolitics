package ch.eiafr.tsc.data.facebook.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Cover implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3112834050302323173L;
	private String id;
	private String cover_id;
	private long offset_x;
	private long offset_y;
	private String source;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getId() {
		return id;
	}

	public void setId(String cover_id) {
		this.id = cover_id;
	}

	public long getOffset_x() {
		return offset_x;
	}

	public void setOffset_x(long offset_x) {
		this.offset_x = offset_x;
	}

	public long getOffset_y() {
		return offset_y;
	}

	public void setOffset_y(long offset_y) {
		this.offset_y = offset_y;
	}

	public String getCover_id() {
		return cover_id;
	}

	public void setCover_id(String cover_id) {
		this.cover_id = cover_id;
	}
	
	

}
