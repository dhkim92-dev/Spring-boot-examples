package kr.dhkim92.examples.S3Upload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

/**
 * File upload Controller
 * @version 1.0
 * @author dhkim92.dev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("s3/")
@Tag(name = "S3 File Upload API")
public class FileController {

  private final FileService fileService;

  @PutMapping(value = "upload",
          consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary="파일 업로드", description = "S3 File upload.")
  @ApiResponse(responseCode = "200", description = "업로드 완료 이후 URL 반환")
  public ResponseEntity<String> uploadFile(
          @Parameter(description = "영상 파일을 multipart/form-data 형식으로 받는다.")
          @RequestParam("file") MultipartFile file){
    String uri = this.fileService.upload(file)
            .orElse(null);

    if(uri == null){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity.ok(uri);
  }

}
