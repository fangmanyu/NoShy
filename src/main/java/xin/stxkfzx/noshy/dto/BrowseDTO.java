package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.BrowseInformation;

/**
 * @author fmy
 * @date 2018-11-01 14:12
 */
public class BrowseDTO extends BaseDTO{
    private BrowseInformation browseInformation;

    @Override
    public String toString() {
        return "BrowseDTO{" +
                "browseInformation=" + browseInformation +
                "} " + super.toString();
    }

    public BrowseInformation getBrowseInformation() {
        return browseInformation;
    }

    public void setBrowseInformation(BrowseInformation browseInformation) {
        this.browseInformation = browseInformation;
    }

    public BrowseDTO(Boolean success, String message, BrowseInformation browseInformation) {
        super(success, message);
        this.browseInformation = browseInformation;
    }

    public BrowseDTO(Boolean success, String message) {
        super(success, message);
    }
}
