package com.yill.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yill.constant.EmaliConstant;
import com.yill.entity.User;
import com.yill.mapper.UserMapper;
import com.yill.utils.CommonUtils;
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
import java.util.Random;

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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result sendSimpleMail(String to){
        String code = CommonUtils.randomCode();
        SimpleMailMessage message = new SimpleMailMessage();
        User user = userMapper.queryUserByEmail(to);
        if (null != user) {
            boolean hasKey = redisUtils.hasKey(to);
            if (!hasKey) {
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(EmaliConstant.EMALI_TITLE);
                message.setText(EmaliConstant.EMALI_CONTENT+code+EmaliConstant.EMALI_CONTENT_TIME);
                mailSender.send(message);
                redisUtils.setWithTime(to,code,60);
                logger.info("邮件发送成功");
                return Result.succ("邮件发送成功");
            } else {
                logger.info("请勿频繁请求验证码");
                return Result.fail("请勿频繁请求验证码");
            }
        } else {
            return Result.fail(404,"账号："+to+"\t未注册，是否立即注册？",to);
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
