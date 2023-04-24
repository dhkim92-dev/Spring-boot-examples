package kr.dhkim92.examples.S3Upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

  private final AmazonS3Client s3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public Optional<String> upload(MultipartFile file){
    try {

      String originalFileName = file.getOriginalFilename();
      String fileUri = "media/video/" + originalFileName;
      String contentType = file.getContentType();

      log.info("content type : {}", contentType);

      if(!contentType.startsWith("video/")){
        throw new RuntimeException("Bad request. Invalid file format.");
      }

      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentType);
      metadata.setContentLength(file.getSize());
      s3Client.putObject(bucket, fileUri, file.getInputStream(), metadata);

      return Optional.of(fileUri);
    }catch(IOException e) {
      log.error("S3 file upload failed. cause {}", e.getMessage(), e);
    }

    return Optional.empty();
  }
}
