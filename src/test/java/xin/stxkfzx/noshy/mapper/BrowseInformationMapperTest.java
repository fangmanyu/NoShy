package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.BrowseInformation;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-08-29 0:25
 */
public class BrowseInformationMapperTest extends BaseTest {
    @Autowired
    private BrowseInformationMapper browseInformationMapper;

    @Test
    public void insert() {
        BrowseInformation browseInformation = new BrowseInformation();
        browseInformation.setBrowseType(2);
        browseInformationMapper.insert(browseInformation);
    }


    @Test
    public void insertSelective() {
        BrowseInformation browseInformation = new BrowseInformation();
        browseInformation.setBrowseType(2);
        browseInformationMapper.insertSelective(browseInformation);
    }
}