package com.aryan.blogging.bloggingapis.services;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;
import com.aryan.blogging.bloggingapis.utils.Constants;




@Service
public class EmailService {
    
    @Autowired
    OTPService otpService;
    
    @Autowired
    UserRepo userRepo;
    
  //Responsible to end email
    public boolean sendEmail(String recepient) {
        boolean sent=false;
        //smtp.gmail.com
        //Constants.gmailHost is the It is the server through which email has to be sent using Simple Mail transfer protocol
        
        //get the system properties
        
        Optional<User> optional=userRepo.findByEmail(recepient);
        User user=optional.get();
        if(user==null)
            return false;
        
        Properties properties=System.getProperties();
        System.out.println(properties);
        
        //setting important information to properties object
        
        properties.put(Constants.hostKey, Constants.gmailHost);
        properties.put(Constants.smtpPortKey,Constants.smtpPort);
        properties.put(Constants.MAIL_SMTP_SSL_ENABLE, "true");
        properties.put(Constants.MAIL_SMTP_AUTH, "true");
        
        // Step-1 Get the session object
        
        
        Session session =Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(Constants.userName,Constants.password);//If the username or password is wrong, there will be an exception and the session object will not be obtained
            }
            
        });
        session.setDebug(true);// For debugging facility
        
        // Step-2 compose the message with [text,multimedia, etc]
        
        MimeMessage mimeMessage= new MimeMessage(session);
        
        //from email
        try {
            mimeMessage.setFrom(Constants.sender);
            
            //add recipients
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));//For many recepients create array of InternetAddresse
            mimeMessage.setSubject(Constants.subject);
            
            int otp=otpService.generateOTP(recepient);
            String message=""
                    +"<div style='border:1px solid #e2e2e2; padding:20px'>"
                    +"<h1>"
                    +"Your OTP is"
                    +"<b>"+otp
                    +"</b>"
                    +"</n>"
                    +"</h1"
                    +"</div>";
            //mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");
            //Step-3 Send the message using transport class
            Transport.send(mimeMessage);
            System.out.println("Sent Success");
            sent=true;
            
        } catch (MessagingException e) {
            
            e.printStackTrace();
        }
        return sent;
        
    }
    
       public  boolean sendAttachment(String recepient) {
           boolean sent=false;
         //smtp.gmail.com
            //Constants.gmailHost is the It is the server through which email has to be sent using Simple Mail transfer protocol
           Optional<User> optional=userRepo.findByEmail(recepient);
           User user=optional.get();
           if(user==null)
               return false;
            //get the system properties
            Properties properties=System.getProperties();
            System.out.println(properties);
            
            //setting important information to properties object
            
            properties.put(Constants.hostKey, Constants.gmailHost);
            properties.put(Constants.smtpPortKey,Constants.smtpPort);
            properties.put(Constants.MAIL_SMTP_SSL_ENABLE, "true");
            properties.put(Constants.MAIL_SMTP_AUTH, "true");
            
            // Step-1 Get the session object
            
            
            Session session =Session.getInstance(properties, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // TODO Auto-generated method stub
                    return new PasswordAuthentication(Constants.userName,Constants.password);//If the username or password is wrong, there will be an exception and the session object will not be obtained
                }
                
            });
            session.setDebug(true);// For debugging facility
            
            // Step-2 compose the message with [text,multimedia, etc]
            
            MimeMessage mimeMessage= new MimeMessage(session);
            
            //from email
            try {
                mimeMessage.setFrom(Constants.sender);
                
                //add recipients
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(Constants.recepient));//For many recepients create array of InternetAddresse
                mimeMessage.setSubject(Constants.subject);
                //mimeMessage.setText(Constants.message);
                
                //Set attachment
                MimeMultipart mimeMultipart =new MimeMultipart();
                
                MimeBodyPart textMime=new MimeBodyPart();
                
                MimeBodyPart fileMime=new MimeBodyPart();
                
                textMime.setText(Constants.message);
                
                File file=new File(Constants.attchmentPath);
                fileMime.attachFile(file);
                mimeMultipart.addBodyPart(textMime);
                mimeMultipart.addBodyPart(fileMime);
                mimeMessage.setContent(mimeMultipart);
                
                //Step-3 Send the message using transport class
                Transport.send(mimeMessage);
                System.out.println("Sent Success");
                sent=true;
                
            } catch (/*MessagingException*/Exception e) {
                
                e.printStackTrace();
            }
            return sent;
            
        }
}
