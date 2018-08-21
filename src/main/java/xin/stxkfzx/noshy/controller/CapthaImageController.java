package xin.stxkfzx.noshy.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 生成kaptha 验证码
 */
@Controller
@RequestMapping(value = "/kaptcha", method = RequestMethod.GET)
@Api(description = "生成验证码接口API")
public class CapthaImageController {

    private final Producer captchaProducer;
    private static final Logger log = LogManager.getLogger(CapthaImageController.class);

    @Autowired
    public CapthaImageController(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @ApiOperation(value = "生成验证码")
    @GetMapping()
    public ModelAndView handleRequest(HttpSession session, HttpServletResponse response, Model model) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-control", "post-check=0, pre-check=0" );
        response.setHeader("Pragma", "no-cache");

        response.setContentType("image/jpeg");

        // 获取kaptcha 生成的内容
        // 将验证码的值存到session中
        String text = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        log.debug("服务端产生的验证码: {}", text);

        try {
            ImageIO.write(captchaProducer.createImage(text), "jpg", response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
