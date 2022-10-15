package com.aryan.blogging.bloggingapis.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


    
    @Service //To Autowire automatically
public class FileServiceImpl implements FileService{

    
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
         //We have to take data from the file and store it at the path

        //Find File name
        String name=file.getOriginalFilename();
        
        //Generate Random Name
        String randomId=UUID.randomUUID().toString();//Use this for cases when image name is same by different users
        String newFileName=randomId.concat(name.substring(name.lastIndexOf(".")));

        //Create a full path till the file. We originally get the path till the folder
        String fullFilePath=path+File.separator+newFileName;

       

        //create folder if not created if the destination folder is noT created.
        File f=new File(path);//This is till the folder where the file has to be uploaded
        if(!f.exists())//If this folder do not exist, then create the folder
        {
            f.mkdir();
        }
        //Copy the file
        Files.copy(file.getInputStream(), Paths.get(fullFilePath));//First param is the destination folder, second is the path of file to be copied
        return newFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;//separator means / according to the os
        InputStream ls=new FileInputStream(fullPath);
        //Above logic is to get a file from the folder
        //Or db logic to return inputStream
        

        return ls;
    }
 
  // also see db operations for uploading and serving
  //file name save in db
}
