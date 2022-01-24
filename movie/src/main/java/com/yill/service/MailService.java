package com.yill.service;

import com.yill.constant.EmailConstant;
import com.yill.mapper.UserMapper;
import com.yill.utils.CommonUtils;
import com.yill.utils.EmailUtils;
import com.yill.utils.RedisUtils;
import com.yill.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.List;

@Service("mailService")

public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private EmailConstant emailConstant;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result sendSimpleMail(String to){
        String code = CommonUtils.randomCode();
            boolean hasKey = redisUtils.hasKey(to);
            if (!hasKey) {
                Boolean sendEmail = emailUtils.sendEmail(to, emailConstant.getEMAIL_TITLE(), emailConstant.getLOGIN_EMAIL_MESSAGE() + code + emailConstant.getLOGIN_EMAIL_MESSAGE_TIME());
                if (!sendEmail) {
                    logger.info("验证码发送失败");
                    return Result.fail("验证码发送失败");
                }
                redisUtils.setWithTime(to,code,60);
                logger.info("邮件发送成功");
                return Result.succ("邮件发送成功");
            } else {
                logger.info("请勿频繁请求验证码");
                return Result.fail("请勿频繁请求验证码");
            }
    }

    public void sendAttachmentsMail(String to, String title, String cotent, List<File> fileList){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(cotent);
            String fileName = null;
            for (File file:fileList) {
                fileName = MimeUtility.encodeText(file.getName(), "GB2312", "B");
                helper.addAttachment(fileName, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
        logger.info("邮件发送成功");
    }


}
