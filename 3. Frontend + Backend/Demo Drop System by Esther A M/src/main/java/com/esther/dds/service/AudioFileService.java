package com.esther.dds.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AudioFileService {
    private final Logger logger = LoggerFactory.getLogger(AudioFileService.class);



    public void saveAudio(MultipartFile audioFile) throws Exception{
        /*Verander hieronder (folder) de absolute PATH naar de locatie waar deze map: "Demo Drop System by Esther A M" zich bevindt*/ //let op de dubbele \\
        String uwMap = "D:\\1_Novi_Examenproject\\3. Frontend + Backend";
        String mijnMappenStruktuur = "\\Demo Drop System by Esther A M\\src\\main\\resources\\static\\audio\\";

        byte[] bytes = audioFile.getBytes();
        Path path = Paths.get(uwMap + mijnMappenStruktuur + audioFile.getOriginalFilename());
        Files.write(path, bytes);

        System.out.println(path.toAbsolutePath());
    }
}
