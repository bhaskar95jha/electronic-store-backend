package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.exceptions.BadApiRequest;
import com.bhaskar.store.management.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFileName = file.getOriginalFilename();
        log.info(originalFileName);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtension = fileName+extension;
        String fullPathWithFileName = path+ File.separator+fileNameWithExtension;
        log.info("full path image name is : {}",fullPathWithFileName);
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".jpg")){

            //file save
            log.info("file extension is : {}",extension);
            File folder = new File(path);
            if(!folder.exists()){
                //create the folder
                folder.mkdirs();
            }

            //upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        }else{
            throw new BadApiRequest("File With this "+extension+" not supported");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

}
