package com.roy.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 *
 */
@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.mail.username}")
    private String from;    //发件人

    @Autowired
    private JavaMailSender mailSender;

    public void sayHello(){
        System.out.println("Hello World!");
    }

    public void sendSimpleMail(String to,String subject,String content){
        logger.info("发送普通邮件开始：{},{},{}",to,subject,content);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);  //发给收件人
            message.setSubject(subject);    //邮件主题
            message.setText(content);      //发送内容
            message.setFrom(from);

            mailSender.send(message);
            logger.info("发送普通邮件成功！");
            } catch (Exception e) {
            logger.error("发送普通邮件失败：",e);
//            e.printStackTrace();
    }
    }

    public void sendHtmlMail(String to,String subject,String content) {
        logger.info("发送HTML邮件开始：{},{},{}",to,subject,content);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            mailSender.send(message);
            logger.info("发送HTML邮件成功！");
        } catch (MessagingException e) {
            logger.error("发送HTML邮件失败：",e);
//            e.printStackTrace();
        }
    }

    public void sendAttachmentsMail(String to,String subject,String content,String filePath) {

        logger.info("发送静态带附件邮件开始：{},{},{},{}",to,subject,content,filePath);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName,file);
//        helper.addAttachment(fileName + "_test",file);

            mailSender.send(message);   //发送邮件
            logger.info("发送静态带附件邮件成功！");
        } catch (MessagingException e) {
            logger.error("发送静态邮件失败：",e);
//            e.printStackTrace();
        }
    }

    public void sendInlinResourceMail(String to,String subject,String content,String rscPath,String rscId) {

        logger.info("发送静态邮件开始：{},{},{},{},{}",to,subject,content,rscPath,rscId);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);


            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            helper.setFrom(from);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId,res);

            mailSender.send(message);   //发送邮件
            logger.info("发送静态图片邮件成功！");
        } catch (MessagingException e) {
            logger.error("发送静态邮件失败：",e);
//            e.printStackTrace();
        }
    }
}