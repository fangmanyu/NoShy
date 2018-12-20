package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 测试下载
 *
 * @author fmy
 * @date 2018-10-29 18:58
 */

@Api(value = "测试", description = "测试相关接口")
@RestController
public class EcmOutsourceUserController {

    @Resource
    private ResourceLoader resourceLoader;

    /**
     * <p>Discription:[下载模板功能]</p>
     * Created on 2018年2月1日 上午11:57:59
     * @param response response对象
     * @param request response对象
     * @author:[全冉]
     */
    @ApiOperation("下载模板功能[15175223269@163.com]")
    @GetMapping("/download/app")
    public void downloadTemplate(HttpServletResponse response, HttpServletRequest request) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = "app.apk";
            String path = "app.apk";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);

            response.setContentType("application/vnd.android.package-archive");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                    servletOutputStream = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/version")
    public String getVersion() {
        return "1235";
    }

}
