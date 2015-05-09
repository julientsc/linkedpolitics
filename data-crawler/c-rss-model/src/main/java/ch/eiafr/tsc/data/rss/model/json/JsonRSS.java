package ch.eiafr.tsc.data.rss.model.json;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class JsonRSS {

@Expose
private String title;
@Expose
private String description;
@Expose
private String language;
@Expose
private String pubDate;
@Expose
private String lastBuildDate;
@Expose
private List<Item> items = new ArrayList<Item>();
@Expose
private String copyright;
@Expose
private String link;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getLanguage() {
return language;
}

public void setLanguage(String language) {
this.language = language;
}

public String getPubDate() {
return pubDate;
}

public void setPubDate(String pubDate) {
this.pubDate = pubDate;
}

public String getLastBuildDate() {
return lastBuildDate;
}

public void setLastBuildDate(String lastBuildDate) {
this.lastBuildDate = lastBuildDate;
}

public List<Item> getItems() {
return items;
}

public void setItems(List<Item> items) {
this.items = items;
}

public String getCopyright() {
return copyright;
}

public void setCopyright(String copyright) {
this.copyright = copyright;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

}