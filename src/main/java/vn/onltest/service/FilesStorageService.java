package vn.onltest.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Service
public class FilesStorageService {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    @Value("${minio.bucket.name}")
    private String defaultBucketName;

    @Autowired
    private MinioClient minioClient;

    @PostConstruct
    private void initializeMinioClient() throws Exception {
        createBucket();
    }

    public void upload(MultipartFile file, String relativePath) {
        try {
            Path absolutePath = convertRelativeToAbsolutePath(relativePath);
            File directory = new File(absolutePath.getParent().toString());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Files.copy(file.getInputStream(), absolutePath);

            // 'minio-test'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(defaultBucketName)
                            .object(file.getOriginalFilename())
                            .filename(absolutePath.toString())
                            .build());

            String[] entries = directory.list();
            for (String s : entries) {
                File currentFile = new File(directory.getPath(), s);
                currentFile.delete();
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private String getRootPath() throws Exception {
        if (isWindows()) {
            return "D:/JavaRootPath/";
        } else if (isUnix()) {
            return "/home/JavaRootPath/";
        } else {
            throw new Exception("Cannot set root path because of unknown OS");
        }
    }

    private Path convertRelativeToAbsolutePath(String relativePath) throws Exception {
        return Paths.get(getRootPath() + relativePath);
    }

    public void saveAs(MultipartFile file, String relativePath) {
        try {
            File directory = new File(convertRelativeToAbsolutePath(relativePath).getParent().toString());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Files.copy(file.getInputStream(), convertRelativeToAbsolutePath(relativePath));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = convertRelativeToAbsolutePath(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private boolean isWindows() {
        return OS.contains("win");
    }

    private boolean isMac() {
        return OS.contains("mac");
    }

    private boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    private boolean isSolaris() {
        return OS.contains("sunos");
    }

    private void createBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Make bucket if not exist.
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(defaultBucketName).build());
        if (!found) {
            // Make a new bucket.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(defaultBucketName).build());
        }
    }
}