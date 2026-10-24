package com.aryan.blogging.bloggingapis.services;

import com.aryan.blogging.bloggingapis.AzureBlobProperties;
import com.aryan.blogging.bloggingapis.entities.Post;
import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.repositories.PostRepo;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MyBlobService {
    @Autowired
    private AzureBlobProperties azureBlobProperties;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PostRepo postRepo;

    private BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(azureBlobProperties.getConnectionstring()).buildClient();
        BlobContainerClient container = serviceClient.getBlobContainerClient(azureBlobProperties.getContainer());
        return container;
    }

    public List<String> listFiles() {
        log.info("List blobs BEGIN");
        BlobContainerClient container = containerClient();
        val list = new ArrayList<String>();
        for (BlobItem blobItem : container.listBlobs()) {
            log.info("Blob {}", blobItem.getName());
            list.add(blobItem.getName());
        }
        log.info("List blobs END");
        return list;
    }

    public ByteArrayOutputStream downloadFile(String blobitem) {
        log.info("Download BEGIN {}", blobitem);
        BlobContainerClient containerClient = containerClient();
        BlobClient blobClient = containerClient.getBlobClient(blobitem);
        String urlString= blobClient.getBlobUrl();
        System.out.println("url of file is "+urlString);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        blobClient.download(os);
        log.info("Download END");
        return os;
    }

    public String storeFile(int postId,String filename, InputStream content, long length) {
//        /*
//         * log.info("Azure store file BEGIN {}", filename);
//         * BlobClient client = containerClient().getBlobClient(filename);
//         * if (client.exists()) {
//         * log.warn("The file was already located on azure");
//         * } else {
//         * client.upload(content, length);
//         * }
//         * 
//         * log.info("Azure store file END");
//         * return "File uploaded with success!";
//         */
        log.info("Azure store file BEGIN {}", filename);
        BlobContainerClient containerClient = containerClient();
        BlobClient newblobClient = containerClient.getBlobClient(filename);
        
        Post post=postRepo.findById(postId).get();
        // First delete old profile image
        String oldFileName=post.getImageName();
        if(oldFileName!=null)
        {
        BlobClient oldblobClient = containerClient.getBlobClient(oldFileName);
        oldblobClient.deleteIfExists();}
        
        post.setImageName(filename);
        
        String newurl;
        
        
        if (newblobClient.exists()) {
            log.warn("The file was already located on azure");
        } else {
            newblobClient.upload(content, length);
            post.setImageName(filename);
            post.setImageUrl(newblobClient.getBlobUrl());
           postRepo.save(post);
        }
        

        log.info("Azure store file END");
        return post.getImageUrl();
    }
    
    public String changeProfilePicture(int userId,String filename, InputStream content, long length) {
        
        log.info("Azure store file BEGIN {}", filename);
        BlobContainerClient containerClient = containerClient();
        BlobClient newblobClient = containerClient.getBlobClient(filename);
        
        User user=userRepo.findById(userId).get();
        // First delete old profile image
        
        String oldFileName=user.getImageName();
        
        if(oldFileName!=null)
        {
        BlobClient oldblobClient = containerClient.getBlobClient(oldFileName);
        oldblobClient.deleteIfExists();
        }
        user.setImageName(filename);
        
        String newurl;
        
        
        if (newblobClient.exists()) {
            log.warn("The file was already located on azure");
        } else {
            newblobClient.upload(content, length);
            user.setImageName(filename);
            user.setImageurl(newblobClient.getBlobUrl());
            userRepo.save(user);
        }
        

        log.info("Azure store file END");
        return user.getImageurl();
    }
    
    public boolean deleteImage(String filename)
    {
        BlobContainerClient containerClient = containerClient();
        BlobClient blob = containerClient.getBlobClient(filename);
        blob.deleteIfExists();
        return true;
    }

}

