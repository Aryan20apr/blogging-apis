package com.aryan.blogging.bloggingapis.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.firebase.messaging.AndroidFcmOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;

@Service
public class FirebaseFcmServiceImpl {

    @Autowired
    private FirebaseMessaging firebaseMessaging;
    
    public void sendMessageToTopic(Integer catId)
    {
        String topic="CAT_"+catId;
        AndroidFcmOptions androidFcmOptions = AndroidFcmOptions.builder().setAnalyticsLabel("AnalyticsLabel").build();
     // See documentation on defining a message payload.
        Notification notification=Notification.builder().setTitle("BlogPost").setBody("Here is a new article for you").build();
        Message message = Message.builder()
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setPriority(Priority.HIGH)
                                .setFcmOptions(androidFcmOptions)
                                .build())
                .putData("Categoryid", catId.toString())
                .putData("Description", "A new article has been added!")
                .setTopic(topic)
                .setNotification(notification)
                .build();
        
     // Send a message to the devices subscribed to the provided topic.
        String response = "";
        try {
            response = firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            System.out.println("Sending message error");
            e.printStackTrace();
        }
        // Response is a message ID string.
        System.out.println("Send message to " + topic + " response: " + response);
    }
    
   
    
    boolean subscribeNotification(String registrationToken,String topic) 
    {
        List<String> registrationTokens=new ArrayList<>();
        registrationTokens.add(registrationToken);
        
        TopicManagementResponse response;
        System.out.println("Registration token is "+registrationToken+" topic="+topic);
        try {
            response = firebaseMessaging.subscribeToTopic(registrationTokens, "CAT_"+topic.strip());
            System.out.println("Subscription response is "+response.getErrors());
        } catch (FirebaseMessagingException e) {
            
            System.out.println("Error is "+e.toString());
            throw new RuntimeException(e);
        }
        int successCount=response.getSuccessCount();
        if(successCount==registrationTokens.size())
        {
            System.out.println(" Subscription to topics successfull ");
            return true;}
        else {
            System.out.println(" Subscription to topics unsuccessfull "+successCount+" "+registrationTokens.size());
            return false;
        }
    }
    
    boolean unsubscribeNotification(String registrationToken,String topic) 
    {
        List<String> registrationTokens=new ArrayList<>();
        registrationTokens.add(registrationToken);
        
        TopicManagementResponse response;
        try {
            response = firebaseMessaging.unsubscribeFromTopic(registrationTokens, "CAT_"+topic.strip());
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        int successCount=response.getSuccessCount();
        
        if(successCount==registrationTokens.size())
        {
            System.out.println(" Unsubscription to topics successfull ");
            return true;}
        else {
            System.out.println(" Unsubscription to topics unsuccessfull "+successCount+" "+registrationTokens.size());
            return false;
        }
    }
    

}

