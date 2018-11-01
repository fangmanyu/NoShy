package xin.stxkfzx.noshy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Challenge;
import xin.stxkfzx.noshy.dto.ChallengeDTO;
import xin.stxkfzx.noshy.exception.ChallengeServiceException;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-09-18 0:41
 */
public class ChallengeServiceTest extends BaseTest {

    @Autowired
    private ChallengeService service;

    @Commit
    @Test
    public void addChallenge() {
        Challenge challenge = new Challenge();
        challenge.setChallengeName("测试挑战三");
        challenge.setChallengeDescription("测试挑战三");

        try {
            ChallengeDTO challengeDTO = service.addChallenge(challenge, "8360f865364a4bd284ac5c46b419c9e1", null);
            assertTrue(challengeDTO.getSuccess());
        } catch (ChallengeServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getChallengeByChallengeId() throws JsonProcessingException {
        ChallengeDTO info = service.getChallengeByChallengeId(3);
        String s = new ObjectMapper().writeValueAsString(info);
        System.out.println(s);
    }

    @Commit
    @Test
    public void addChallengeVideo() {
        String videoId = "f29b6928de314b1e99c7997e41959173";
        Integer challengeId = 10;
        ChallengeDTO dto = service.addChallengeVideo(videoId, challengeId);
        System.out.println(dto);
        assertTrue(dto.getSuccess());
    }

    @Test
    public void getChallengeImage() {
        String challengeImage = service.getChallengeImage(4);
        System.out.println(challengeImage);
    }
}