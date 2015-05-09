
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Account  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3743370142005046217L;
	@SerializedName("/account/verify_credentials")
    @Expose
    private AccountVerifyCredentials accountVerifyCredentials;
    @SerializedName("/account/update_profile")
    @Expose
    private AccountUpdateProfile accountUpdateProfile;
    @SerializedName("/account/login_verification_enrollment")
    @Expose
    private AccountLoginVerificationEnrollment accountLoginVerificationEnrollment;
    @SerializedName("/account/settings")
    @Expose
    private AccountSettings accountSettings;

    public AccountVerifyCredentials getAccountVerifyCredentials() {
        return accountVerifyCredentials;
    }

    public void setAccountVerifyCredentials(AccountVerifyCredentials accountVerifyCredentials) {
        this.accountVerifyCredentials = accountVerifyCredentials;
    }

    public AccountUpdateProfile getAccountUpdateProfile() {
        return accountUpdateProfile;
    }

    public void setAccountUpdateProfile(AccountUpdateProfile accountUpdateProfile) {
        this.accountUpdateProfile = accountUpdateProfile;
    }

    public AccountLoginVerificationEnrollment getAccountLoginVerificationEnrollment() {
        return accountLoginVerificationEnrollment;
    }

    public void setAccountLoginVerificationEnrollment(AccountLoginVerificationEnrollment accountLoginVerificationEnrollment) {
        this.accountLoginVerificationEnrollment = accountLoginVerificationEnrollment;
    }

    public AccountSettings getAccountSettings() {
        return accountSettings;
    }

    public void setAccountSettings(AccountSettings accountSettings) {
        this.accountSettings = accountSettings;
    }

}
