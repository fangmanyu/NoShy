package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-09-20 12:00
 */
public class ChallengeMapperTest extends BaseTest {

    @Autowired
    private ChallengeMapper challengeMapper;

    @Test
    public void findOwnerVideoId() {
        String ownerVideoId = challengeMapper.findOwnerVideoId(3, 26);
        System.out.println(ownerVideoId);
    }
}