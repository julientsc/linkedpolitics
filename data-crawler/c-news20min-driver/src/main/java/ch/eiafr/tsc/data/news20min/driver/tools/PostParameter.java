package ch.eiafr.tsc.data.news20min.driver.tools;

public class PostParameter {
	String name;
	String value;

	public PostParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
}