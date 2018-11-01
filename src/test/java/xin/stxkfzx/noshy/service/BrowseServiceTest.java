package xin.stxkfzx.noshy.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.BrowseInformation;
import xin.stxkfzx.noshy.dto.BrowseDTO;
import xin.stxkfzx.noshy.exception.BrowseException;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-11-01 20:58
 */
public class BrowseServiceTest extends BaseTest {
    @Autowired
    private BrowseService browseService;

    @Test
    public void updateBrowseInfo() {
        try {
            BrowseDTO dto = browseService.updateBrowseInfo(-1, "asd", BrowseInformation.LIKES);
        } catch (BrowseException e) {
            System.out.println(e.getMessage());
        }

        BrowseDTO browseDTO = browseService.updateBrowseInfo(BrowseInformation.VIDEO, "3586c11eaec047c88dc40a673c3b4720", BrowseInformation.LIKES);
        assertTrue(browseDTO.getSuccess());
    }
}