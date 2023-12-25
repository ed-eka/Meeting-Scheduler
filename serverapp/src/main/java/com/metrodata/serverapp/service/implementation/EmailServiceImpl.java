package com.metrodata.serverapp.service.implementation;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.request.Email;
import com.metrodata.serverapp.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;
    private GoogleCalenderService calenderService;

    @Override
    public Email sendVerificationEmail(Email email) {
        try {
            Context context = new Context();

            context.setVariable("name", email.getName());
            context.setVariable("attachment", email.getAttachment());
            String body = templateEngine.process("verification", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(body, true);
            mailSender.send(mimeMessage);

            log.info("Successfully send email verification account to : {}", email.getTo());
        } catch (MessagingException e) {
            log.error("Failed to send email verification account to : {}", email.getTo());
            throw new CustomException(e.getMessage(), "FAILED_TO_SEND_EMAIL", 500);
        }
        return email;
    }


    @Override
    public Email invitationMessage(Email email) {
        try {
            Context context = new Context();
            // FileSystemResource fileSystemResource = new FileSystemResource(new File(email.getAttachment()));

            context.setVariable("name", email.getName());
            context.setVariable("title", email.getTitle());
            context.setVariable("dateStart", email.getDateStart());
            context.setVariable("dateEnd", email.getDateEnd());
            context.setVariable("location", email.getLocation());
            context.setVariable("initName", email.getInitName());
            context.setVariable("initEmail", email.getInitEmail());
            context.setVariable("initPhone", email.getInitPhone());
            context.setVariable("linkConfirm", email.getLinkConfirm());
            context.setVariable("linkReject", email.getLinkReject());
// TO DO: Expand the context to cover the rest of the variable (Title, DateStart, DateEnd, Location with conditional if Online or not, and Initiator (Name, Email, Phone)
            // context.setVariable("imageSource", fileSystemResource.getFilename());
            String templateProcess = templateEngine.process("invitation.html", context); //(With Thymeleaf)
            // String templateProcess = templateEngine.process("testInvitation.html", context); //(No Thymeleaf)

            MimeMessage htmlMessage = mailSender.createMimeMessage();
            htmlMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
            htmlMessage.setSubject(email.getSubject());

            MimeMultipart mimeMultipart = new MimeMultipart("mixed");

            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(templateProcess, "text/html; charset=utf-8");

            mimeMultipart.addBodyPart(htmlBodyPart);
            // mimeMultipart.addBodyPart(createCalenderMymeBody(email));
            
            htmlMessage.setContent(mimeMultipart);
            mailSender.send(htmlMessage);

            // MimeMessageHelper helper = new MimeMessageHelper(htmlMessage, true, "UTF-8");
            // helper.setTo(email.getTo());
            // helper.setSubject(email.getSubject());
            // helper.setText(templateProcess, true);
            

            // helper.addInline(fileSystemResource.getFilename(), fileSystemResource);


        } catch (MessagingException e){
            log.error("Fail to send email to : {}", email.getTo());
            throw new CustomException(e.getMessage(), "FAILED_TO_SEND_EMAIL", 500);
        } 
        return email;
    }

    public BodyPart createCalenderMymeBody(Email email) throws IOException, MessagingException {
        MimeBodyPart calenderBodyPart = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource(calenderService.createCalender(email), "text/calender; charset=UTF-8");
        calenderBodyPart.setDataHandler(new DataHandler(source));
        calenderBodyPart.setHeader("Content-Type", "text/calender; charset=UTF-8; method=REQUEST; name=invite.ics");

        return calenderBodyPart;
    }
}