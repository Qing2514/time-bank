package com.fortuna.bampo.util;

import com.fortuna.bampo.config.properties.RestApiProperties;
import com.fortuna.bampo.model.response.data.EmailItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

/**
 * 发送邮件工具类
 *
 * @author lhx, Eva7
 * @since 0.3.1
 */
@Configuration
@RequiredArgsConstructor
public class EmailUtil {
    JavaMailSender javaMailSender;
    MailProperties mailProperties;

    @Autowired
    public EmailUtil(MailProperties mailProperties, JavaMailSender javaMailSender) {
        this.mailProperties = mailProperties;
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送邮件
     *
     * @param emailItem 发送的邮件的相关信息
     */
    public boolean sendSimpleMail(EmailItem emailItem) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(emailItem.getSubject());
        message.setFrom(RestApiProperties.getEmailAddress(mailProperties.getUsername()));
        message.setTo(emailItem.getTo());
        message.setSentDate(new Date());
        message.setText(emailItem.getText());
        javaMailSender.send(message);
        return true;
    }
}
