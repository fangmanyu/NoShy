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
        challenge.setChallengeName("编成挑战");
        challenge.setChallengeDescription("学习编程好能手");
        challenge.setUserId(27);

        try {
            ChallengeDTO challengeDTO = service.addChallenge(challenge, "a954b24b01294a6e9cd5d367b22467a3", null);
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

    // @Commit
    @Test
    public void addChallengeVideo() {
        String videoId = "962dbcf0eb424cbe93d62f8da5ffec80";
        Integer challengeId = 3;
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