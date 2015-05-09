package data.twitter.model;

public class PageData {
	private String name;
	private Integer num;
	private String link;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getLink() {
		return link;
	}
	public void updateLink() {
		this.link = "/all/"+num+"/";
		this.name = "Page "+num;
	}
	
}
