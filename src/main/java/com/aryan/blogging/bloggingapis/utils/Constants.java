package com.aryan.blogging.bloggingapis.utils;



public class Constants {
    public static final String PAGE_NUMBER="0";
    public static final String PAGE_SIZE="10";
    public static final String SORT_BY="id";
    public static final String SORT_DIR="desc";
    
    public static final int ADMIN_ROLE_ID=1024;
    public static final int NORMAL_ROLE_ID=504;
    
public static String subject="Account Verification Code";
    
    public static String message="Your verification code is . Do not share this with anybody.";
    
    public static String recepient="apraryan.20@gmail.com";
    
    public static String sender="aryanrandom20@gmail.com";
    
    public static String userName="aryanrandom20@gmail.com";
    
    public static String password="twctnohubfniojcx";
    
    public static String gmailHost="smtp.gmail.com";//It is the server through which email has to be sent using Simple Mail transfer protocol
    
    public static String hostKey="mail.smtp.host";
    
    public static String smtpPortKey="mail.smtp.port";
    
    public static String smtpPort="465";//Other ports may also work, search on google
    
    public static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";

    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

    public static final String attchmentPath="D:\\birthday-designer-chocolate-cake-1-kg_1.jpg";
    
    public static enum PasswordChangeStatus {
            PASSWORD_CHANGED,
            PASSWORD_INCORRECT,
            PASSWORD_NOT_CHANGED,
            USER_DOES_NOT_EXIST;
    }
    
}
