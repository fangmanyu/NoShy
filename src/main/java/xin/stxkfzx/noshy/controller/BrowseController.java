package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.stxkfzx.noshy.dto.BrowseDTO;
import xin.stxkfzx.noshy.exception.BrowseException;
import xin.stxkfzx.noshy.service.BrowseService;
import xin.stxkfzx.noshy.vo.AddRecordVO;
import xin.stxkfzx.noshy.vo.JSONResponse;

/**
 * 浏览信息 路由
 *
 * @author fmy
 * @date 2018-11-01 21:59
 */
@RestController
@RequestMapping("/browse")
@Api(description = "浏览信息相关 API")
@Validated
public class BrowseController {
    private final BrowseService browseService;

    @ApiOperation(value = "添加浏览信息")
    @PostMapping("/addRecord")
    public JSONResponse updateBrowseInfo(@RequestBody @ApiParam AddRecordVO recordVO) {
        try {
            BrowseDTO dto = browseService.updateBrowseInfo(recordVO.getBrowseType(), recordVO.getBelongId(), recordVO.getTypeName());
            return new JSONResponse(dto.getSuccess(), dto.getMessage(), dto.getBrowseInformation());
        } catch (BrowseException e) {
            e.printStackTrace();
            return new JSONResponse(false, e.getMessage());
        }
    }


    @Autowired
    public BrowseController(BrowseService browseService) {
        this.browseService = browseService;
    }
}
