package vn.onltest.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.service.FilesStorageService;
import vn.onltest.util.PathUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/utils")
@AllArgsConstructor
public class UtilsController {
    private final FilesStorageService filesStorageService;

    // this is api for demo
    @PostMapping("uploadFile")
    public AbstractResponse uploadFile(@RequestPart MultipartFile file) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        filesStorageService.saveAs(file, "uploads/" + currentDateTime + file.getOriginalFilename());
        return new BaseResultResponse<>(HttpStatus.OK.value(), "Upload file successfully");
    }

    // this is api for demo
    @GetMapping("downloadFile")
    public ResponseEntity downloadFile() {
        Resource file = filesStorageService.load("uploads/BookImport.xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
