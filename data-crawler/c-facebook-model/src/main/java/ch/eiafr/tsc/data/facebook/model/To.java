package ch.eiafr.tsc.data.facebook.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class To implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6692310157635720117L;
	private List<ToData> data;
	private Map<String, Object> additionalProperties;
	

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


	public List<ToData> getData() {
		return data;
	}

	public void setData(List<ToData> data) {
		this.data = data;
	}

	static class ToData implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7119632698315995929L;
		private String category;
		private String name;
		private String id;
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Map<String, Object> getAdditionalProperties() {
			return additionalProperties;
		}

		public void setAdditionalProperties(
				Map<String, Object> additionalProperties) {
			this.additionalProperties = additionalProperties;
		}
	}
}
