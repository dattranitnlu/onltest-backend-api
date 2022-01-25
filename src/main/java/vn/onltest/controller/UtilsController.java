package vn.onltest.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.error.BaseErrorResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.service.FilesStorageService;
import vn.onltest.service.UserExcelService;
import vn.onltest.util.ExcelHelper;
import vn.onltest.util.PathUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/utils")
@AllArgsConstructor
public class UtilsController {
    private final FilesStorageService filesStorageService;
    private final UserExcelService fileService;

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

    @PostMapping("importUser")
    public AbstractResponse uploadBookFile(@RequestPart(required = true) MultipartFile file) {
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                fileService.importBookFromExcel(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return new BaseResultResponse<>(HttpStatus.OK.value(), message);
            } catch (Exception e) {
                return new BaseErrorResponse(500, "Upload file failured");
            }
        }

        return new BaseResultResponse<>(HttpStatus.OK.value(), message);
    }

    @GetMapping("exportAllUser")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=book_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        fileService.exportAllBook(response);
    }
}
