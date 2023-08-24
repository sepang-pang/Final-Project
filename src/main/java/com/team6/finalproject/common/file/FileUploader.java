package com.team6.finalproject.common.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String upload(MultipartFile file) throws IOException {
        File uploadFile = convert(file); // 받아온 MultipartFile -> File 변환
        String fileName = uploadFile.getName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType()); // 요청받은 파일의 ContentType 메타데이터에 주입

        String uploadFileUrl = putS3(uploadFile, fileName, metadata);   // S3에 업로드

        uploadFile.delete();    // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        return uploadFileUrl;   // 업로드된 파일의 S3 URL 주소 반환
    }

    private File convert(MultipartFile file) throws IOException {
        File convertFile = new File(UUID.randomUUID().toString()); // 파일명 UUID로 설정

        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());

        return convertFile;
    }

    private String putS3(File uploadFile, String fileName, ObjectMetadata metadata) {
        amazonS3.putObject(
                new PutObjectRequest(bucketName, fileName, uploadFile)
                        .withMetadata(metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)  // PublicRead 권한으로 업로드
        );
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    public void deleteFile(String fileName) {   // S3 파일 삭제
        // URI 제거 -> 키값만 담기
        String key = fileName.substring(fileName.lastIndexOf('/') + 1);
        amazonS3.deleteObject(bucketName, key);
    }
}
