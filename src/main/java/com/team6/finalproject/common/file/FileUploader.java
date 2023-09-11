package com.team6.finalproject.common.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String upload(MultipartFile file) throws IOException {
        // 파일을 요청하지 않은 경우 미리 업로드해둔 기본이미지 저장
        if (file.getOriginalFilename().equals("empty")) {
            return amazonS3.getUrl(bucketName, "a240fa66-41d3-47c9-b9fd-6a45122fad81").toString();
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType()); // 요청받은 파일의 ContentType 메타데이터에 주입

        UUID randomName = UUID.randomUUID();
        String fileName = randomName.toString();
        amazonS3.putObject(
                new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucketName, fileName).toString();   // 업로드된 파일의 S3 URL 주소 반환
    }

    public void deleteFile(String fileName) {   // S3 파일 삭제
        // URI 제거 -> 키값만 담기
        String key = fileName.substring(fileName.lastIndexOf('/') + 1);
        amazonS3.deleteObject(bucketName, key);
    }
}
