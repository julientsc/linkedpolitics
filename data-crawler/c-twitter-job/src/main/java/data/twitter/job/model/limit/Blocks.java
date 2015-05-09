
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Blocks implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3394138045539409374L;
	@SerializedName("/blocks/ids")
    @Expose
    private BlocksIds blocksIds;
    @SerializedName("/blocks/list")
    @Expose
    private BlocksList blocksList;

    public BlocksIds getBlocksIds() {
        return blocksIds;
    }

    public void setBlocksIds(BlocksIds blocksIds) {
        this.blocksIds = blocksIds;
    }

    public BlocksList getBlocksList() {
        return blocksList;
    }

    public void setBlocksList(BlocksList blocksList) {
        this.blocksList = blocksList;
    }

}
