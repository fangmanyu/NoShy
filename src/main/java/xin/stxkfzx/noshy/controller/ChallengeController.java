package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.stxkfzx.noshy.domain.Challenge;
import xin.stxkfzx.noshy.domain.Rank;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.domain.UserInformation;
import xin.stxkfzx.noshy.dto.ChallengeDTO;
import xin.stxkfzx.noshy.exception.ChallengeServiceException;
import xin.stxkfzx.noshy.service.ChallengeService;
import xin.stxkfzx.noshy.service.UserService;
import xin.stxkfzx.noshy.vo.*;
import xin.stxkfzx.noshy.vo.challenge.CreateChallengeVO;
import xin.stxkfzx.noshy.vo.challenge.RankDetailVO;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 挑战API
 *
 * @author fmy
 * @date 2018-09-18 0:58
 */
@Api(description = "挑战相关API接口")
@RestController
@RequestMapping("/challenge")
@Validated
public class ChallengeController {
    private static final Logger log = LogManager.getLogger(ChallengeController.class);
    private final ChallengeService challengeService;
    private final UserService userService;
    private User currentUser;

    @ApiOperation(value = "获取指定挑战")
    @GetMapping("/{challengeId}")
    public JSONResponse getChallenge(@PathVariable @Min(0) int challengeId) {
        ChallengeDTO dto = challengeService.getChallengeByChallengeId(challengeId);

        if (!dto.getSuccess()) {
            return new JSONResponse(false, dto.getMessage());
        }

        List<Rank> rankList = dto.getRankList();
        RankDetailVO detailVO;
        List<RankDetailVO> rankDetailVOList = new ArrayList<>();
        for (Rank rank :
                rankList) {
            detailVO = new RankDetailVO();
            Long userId = Long.valueOf(rank.getUserId());
            User user = userService.getUser(userId);
            UserInformation userDetail = userService.getUserDetail(userId);
            UserVO userVO = new UserVO();
            userVO.setUserName(user.getUserName());
            userVO.setUserId(userId);
            BeanUtils.copyProperties(rank, detailVO);
            detailVO.setUserInfo(userVO);
            detailVO.setHeadPortraitAddr(userDetail.getHeadPortraitAddr());
            rankDetailVOList.add(detailVO);
        }

        ChallengeDetailVO data = new ChallengeDetailVO();
        BeanUtils.copyProperties(dto.getChallenge(), data);
        data.setRankList(rankDetailVOList);

        return new JSONResponse(dto.getSuccess(), dto.getMessage(), data);
    }

    @ApiOperation(value = "分页获取猜你喜欢挑战列表")
    @GetMapping("/like")
    public JSONResponse listChallengeByLike(@RequestParam @Min(0) int pageIndex,
                                            @RequestParam @Min(0) int pageSize) {
        ChallengeDTO dto = challengeService.listInterestedChallenge(pageIndex, pageSize);
        List<Challenge> challengeList = dto.getChallengeList();
        List<LikeChallengeVO> list = new ArrayList<>(challengeList.size());
        LikeChallengeVO likeChallengeVO;
        for (Challenge item :
                challengeList) {
            likeChallengeVO = new LikeChallengeVO();
            BeanUtils.copyProperties(item, likeChallengeVO);
            list.add(likeChallengeVO);
        }
        return new JSONResponse(dto.getSuccess(), dto.getMessage(), list);
    }

    @ApiOperation(value = "添加挑战视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频Id"),
            @ApiImplicitParam(name = "challengeId", value = "加入挑战Id")
    })
    @GetMapping("/{challengeId}/{videoId}/addChallengeVideo")
    public JSONResponse addVideoToChallenge(@PathVariable @NotEmpty String videoId,
                                            @PathVariable @Min(0) Integer challengeId) {

        ChallengeDTO dto = null;
        try {
            dto = challengeService.addChallengeVideo(videoId, challengeId);
        } catch (ChallengeServiceException e) {
            e.printStackTrace();
            return new JSONResponse(false, "添加失败");
        }
        return new JSONResponse(dto.getSuccess(), dto.getMessage());

    }


    @ApiOperation(value = "创建挑战")
    @PostMapping()
    public JSONResponse createChallenge(@RequestParam(required = false) String challengeTitle,
                                        @NotEmpty @RequestParam String videoId,
                                        @RequestParam(required = false) MultipartFile image) {

        Challenge challenge = new Challenge();
        challenge.setChallengeName(challengeTitle);
        try {
            ImageHolder holder = Optional.ofNullable(image).map(multipartFile -> {
                try {
                    ImageHolder imageHolder = new ImageHolder(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
                    return imageHolder;
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }).orElse(null);

            try {
                ChallengeDTO dto = challengeService.addChallenge(challenge, videoId, holder);
                return new JSONResponse(dto.getSuccess(), dto.getMessage());
            } catch (ChallengeServiceException e) {
                log.error(e.getMessage());
                return new JSONResponse(false, e.getMessage());
            }
        } catch (RuntimeException e) {
            return new JSONResponse(false, "系统内部错误");
        }
    }


    @ModelAttribute
    public void getCurrentUser(HttpSession session) {
        Object currentUser = session.getAttribute("currentUser");
        if (currentUser instanceof User) {
            this.currentUser = (User) currentUser;
            log.debug("当前登录用户: {}", this.currentUser);
        }
    }

    @Autowired
    public ChallengeController(ChallengeService challengeService, UserService userService) {
        this.challengeService = challengeService;
        this.userService = userService;
    }
}
