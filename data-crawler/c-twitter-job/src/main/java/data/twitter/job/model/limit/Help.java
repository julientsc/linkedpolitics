
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Help implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3147408797951698084L;
	@SerializedName("/help/settings")
    @Expose
    private HelpSettings helpSettings;
    @SerializedName("/help/privacy")
    @Expose
    private HelpPrivacy helpPrivacy;
    @SerializedName("/help/tos")
    @Expose
    private HelpTos helpTos;
    @SerializedName("/help/configuration")
    @Expose
    private HelpConfiguration helpConfiguration;
    @SerializedName("/help/languages")
    @Expose
    private HelpLanguages helpLanguages;

    public HelpSettings getHelpSettings() {
        return helpSettings;
    }

    public void setHelpSettings(HelpSettings helpSettings) {
        this.helpSettings = helpSettings;
    }

    public HelpPrivacy getHelpPrivacy() {
        return helpPrivacy;
    }

    public void setHelpPrivacy(HelpPrivacy helpPrivacy) {
        this.helpPrivacy = helpPrivacy;
    }

    public HelpTos getHelpTos() {
        return helpTos;
    }

    public void setHelpTos(HelpTos helpTos) {
        this.helpTos = helpTos;
    }

    public HelpConfiguration getHelpConfiguration() {
        return helpConfiguration;
    }

    public void setHelpConfiguration(HelpConfiguration helpConfiguration) {
        this.helpConfiguration = helpConfiguration;
    }

    public HelpLanguages getHelpLanguages() {
        return helpLanguages;
    }

    public void setHelpLanguages(HelpLanguages helpLanguages) {
        this.helpLanguages = helpLanguages;
    }

}
