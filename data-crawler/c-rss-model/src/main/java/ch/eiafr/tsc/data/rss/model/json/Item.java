package ch.eiafr.tsc.data.rss.model.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Item {

@Expose
private String title;
@Expose
private String description;
@Expose
private String link;
@Expose
private String pubDate;

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

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public String getPubDate() {
return pubDate;
}

public void setPubDate(String pubDate) {
this.pubDate = pubDate;
}

}