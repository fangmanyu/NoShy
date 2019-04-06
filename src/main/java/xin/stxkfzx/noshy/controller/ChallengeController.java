package xin.stxkfzx.noshy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.stxkfzx.noshy.domain.*;
import xin.stxkfzx.noshy.dto.ChallengeDTO;
import xin.stxkfzx.noshy.dto.VideoDTO;
import xin.stxkfzx.noshy.exception.ChallengeServiceException;
import xin.stxkfzx.noshy.service.ChallengeService;
import xin.stxkfzx.noshy.service.UserService;
import xin.stxkfzx.noshy.service.VideoService;
import xin.stxkfzx.noshy.util.UserUtils;
import xin.stxkfzx.noshy.vo.*;
import xin.stxkfzx.noshy.vo.challenge.ChallengeListVO;
import xin.stxkfzx.noshy.vo.challenge.ChallengeVO;
import xin.stxkfzx.noshy.vo.challenge.ListChallengeQuery;
import xin.stxkfzx.noshy.vo.challenge.RankDetailVO;
import xin.stxkfzx.noshy.vo.video.VideoDetailVO;
import xin.stxkfzx.noshy.vo.video.VideoVO;

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
    private final VideoService videoService;
    private final UserService userService;

    @ApiOperation(value = "获取指定挑战")
    @GetMapping("/{challengeId}")
    public JSONResponse getChallenge(@PathVariable @Min(0) int challengeId) {
        Long currentUserId = UserUtils.getUserId();
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
            String videoId = rank.getVideoId();

            VideoDTO videoDTO = videoService.getVideoByVideoId(videoId);
            BeanUtils.copyProperties(rank, detailVO);
            detailVO.setUserInfo(getUserVo(userId));
            detailVO.setVideoInfo(getVideoDetail(videoDTO.getVideo(), videoDTO.getBrowseInformation()));

            boolean liked = challengeService.isLiked(rank.getRankId(), currentUserId.intValue());
            detailVO.setHasLiked(liked);
            rankDetailVOList.add(detailVO);
        }

        ChallengeDetailVO data = new ChallengeDetailVO();
        BeanUtils.copyProperties(dto.getChallenge(), data);
        data.setRankList(rankDetailVOList);

        return new JSONResponse(dto.getSuccess(), dto.getMessage(), data);
    }

    private VideoDetailVO getVideoDetail(Video video, BrowseInformation browseInformation) {
        if (video != null && browseInformation != null) {
            VideoVO videoVO = getVideoVO(video);
            VideoDetailVO detailVO = new VideoDetailVO();
            detailVO.setVideo(videoVO);
            detailVO.setBrowseInformation(browseInformation);

            return detailVO;
        }
        return null;
    }

    private VideoVO getVideoVO(Video video) {
        VideoVO videoVO;
        if (video.getPlayUrl() == null) {

            String videoId = video.getVideoId();
            VideoDTO playUrl = videoService.getPlayUrl(videoId);

            if (playUrl.getSuccess()) {
                video.setPlayUrl(playUrl.getPlayUrl());
            }
        }
        videoVO = new VideoVO();
        BeanUtils.copyProperties(video, videoVO);

        ChallengeDTO challengeDTO = challengeService.listChallengeIdByVideo(video.getVideoId());
        List<ChallengeVO> challengeVOS = Optional.ofNullable(challengeDTO.getChallengeIdList()).map(ids -> {
            List<ChallengeVO> vos = new ArrayList<>(ids.size());
            ChallengeVO vo;
            for (Integer id : ids) {
                ChallengeDTO dto = challengeService.getChallengeByChallengeId(id);
                if (dto.getSuccess()) {
                    vo = new ChallengeVO();
                    BeanUtils.copyProperties(dto.getChallenge(), vo);
                    vo.setJoinNum(dto.getRankList().size());
                    vos.add(vo);
                }

            }
            return vos;
        }).orElse(null);
        videoVO.setJoinChallengeList(challengeVOS);
        videoVO.setUserInfo(getUserVo(video.getUserId()));
        return videoVO;
    }

    private UserVO getUserVo(Long userId) {
        UserInformation userDetail = userService.getUserDetail(userId);
        User user = userService.getUser(userId);
        UserVO userVO = new UserVO();
        userVO.setUserName(user.getUserName());
        userVO.setUserId(userId);
        userVO.setHeadPortraitAddr(userDetail.getHeadPortraitAddr());

        return userVO;
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
    @PostMapping("/{challengeId}/{videoId}/addChallengeVideo")
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

    @ApiOperation(value = "获取挑战列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "challengeQueryStr", value = "查询参数"),
            @ApiImplicitParam(name = "pageIndex", value = "分页起始位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "int")
    })
    @GetMapping
    public JSONResponse listChallenge(@RequestParam(required = false) String challengeQueryStr,
                                      @RequestParam @Min(0) int pageIndex,
                                      @RequestParam @Min(0) int pageSize) {
        ChallengeDTO dto;
        if (StringUtils.isEmpty(challengeQueryStr)) {
            dto = challengeService.listChallengeByCondition(null, pageIndex, pageSize);
        } else {
            try {
                ListChallengeQuery challenge = new ObjectMapper().readValue(challengeQueryStr, ListChallengeQuery.class);
                Challenge challengeCondition = new Challenge();
                BeanUtils.copyProperties(challenge, challengeCondition);

                dto = challengeService.listChallengeByCondition(challengeCondition, pageIndex, pageSize);
            } catch (IOException e) {
                e.printStackTrace();
                return new JSONResponse(false, "查询参数错误: " + e.getLocalizedMessage());
            }
        }

        return new JSONResponse(dto.getSuccess(), dto.getMessage(), getChallengeListVO(dto));
    }

    private ChallengeListVO getChallengeListVO(ChallengeDTO dto) {
        ChallengeListVO vos = new ChallengeListVO();
        vos.setCount(dto.getCount());

        Optional.ofNullable(dto.getChallengeList()).ifPresent(challenges -> {
            List<ChallengeVO> voList = new ArrayList<>(challenges.size());
            challenges.forEach(challenge -> Optional.ofNullable(getChallengeVO(challenge)).ifPresent(voList::add));
            if (!voList.isEmpty()) {
                vos.setChallengeVOList(voList);
            }
        });

        return vos;
    }

    private ChallengeVO getChallengeVO(Challenge challenge) {
        ChallengeDTO dto = challengeService.getChallengeByChallengeId(challenge.getChallengeId());
        if (dto.getSuccess()) {
            ChallengeVO vo = new ChallengeVO();
            BeanUtils.copyProperties(dto.getChallenge(), vo);
            vo.setJoinNum(dto.getRankList().size());

            return vo;
        }
        return null;
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

    @ApiOperation(value = "挑战视频点赞")
    @ApiImplicitParam(name = "rankId", value = "排名Id")
    @PutMapping("/{rankId}/addLike")
    public JSONResponse addLike(@PathVariable @Min(0) Integer rankId) {
        Long userId = UserUtils.getUserId();

        ChallengeDTO dto = null;
        try {
            dto = challengeService.likeIt(rankId, userId.intValue());
        } catch (ChallengeServiceException e) {
            e.printStackTrace();
            return new JSONResponse(false, e.getMessage());
        }
        return new JSONResponse(dto.getSuccess(), dto.getMessage());
    }

    @GetMapping("/myChallenge")
    public JSONResponse listMyJoinChallenge() {
        Long userId = UserUtils.getUserId();

        ChallengeDTO dto = challengeService.listMyJoinChallenge(userId.intValue());
        return new JSONResponse(dto.getSuccess(), dto.getMessage(), getChallengeListVO(dto));
    }

    @ApiOperation(value = "获取挑战页面URL地址")
    @GetMapping("/url")
    public String getChallengeHtmlURL() {
        return "http://119.23.208.165/NoShy/html/challenge/challenge_index.html";
    }

    @ApiOperation(value = "获取上传挑战页面URL地址")
    @GetMapping("/url/upload")
    public String getUploadChallengeHtmlURL() {
        return "http://119.23.208.165/NoShy/html/share/publish.html?type=1";
    }


    @Autowired
    public ChallengeController(ChallengeService challengeService, VideoService videoService, UserService userService) {
        this.challengeService = challengeService;
        this.videoService = videoService;
        this.userService = userService;
    }
}
