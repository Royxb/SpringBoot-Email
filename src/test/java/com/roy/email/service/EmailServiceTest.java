package com.roy.email.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 *邮箱服务
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @Resource
    EmailService emailService;

    @Test
    public void sayHelloTest() {
        emailService.sayHello();
    }

    @Test
    public void sendSimpleMailTest() {
        emailService.sendSimpleMail("****@126.com", "这是第一封邮件", "大家好，这是我的第一封邮件");
    }

    @Test
    public void sendHtmlMailTest() throws MessagingException {
        String content = "<html>\n"+
                            "<body>\n"+
                                "<h3>hello word,这是第一封HTML邮件!</h3>"+
                            "</body>\n" +
                        "</html>";
        emailService.sendHtmlMail("****@126.com", "这是第一封HTML邮件", content);
    }

    @Test
    public void sendAttachmentsMailTest() throws MessagingException {
        String filePath = "D:\\Downloads\\spring-boot.zip";
        String content = "<html>\n"+
                            "<body>\n"+
                                "<h3>hello word,这是第一封带附件的邮件内容!</h3>"+
                            "</body>\n" +
                        "</html>";
        emailService.sendAttachmentsMail("****@126.com", "这是第一封带附件的邮件", content,filePath);
    }

    @Test
    public void sendInlinResourceMailTest() throws MessagingException {
        String imgPath = "D:\\IdeaProjects\\img9.jpg";
        String rscId = "roy001";
        String content = "<html><body>"+
                                "这是有图片的邮件：<img src=\'cid:"+rscId+"\'></img>"+
                            "</body></html>";
        emailService.sendInlinResourceMail("****@126.com", "这是第一封带图片的邮件", content,imgPath,rscId);
    }

    @Resource
    TemplateEngine templateEngine;

    @Test
    public void testTeeplateTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id","006");

        String emailContext = templateEngine.process("emailTemplate",context);

        emailService.sendHtmlMail("royxubing@qq.com","这是一个模板邮件",emailContext);
    }
}
