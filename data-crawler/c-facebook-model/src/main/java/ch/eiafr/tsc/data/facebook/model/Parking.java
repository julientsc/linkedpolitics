
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
public class Parking implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3264119679621279364L;
	private long lot;
    private long street;
    private long valet;
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
	
    public long getLot() {
        return lot;
    }

    public void setLot(long lot) {
        this.lot = lot;
    }

    public long getStreet() {
        return street;
    }

    public void setStreet(long street) {
        this.street = street;
    }

    public long getValet() {
        return valet;
    }

    public void setValet(long valet) {
        this.valet = valet;
    }

}
