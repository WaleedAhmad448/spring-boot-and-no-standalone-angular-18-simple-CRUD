/**
 * 
 */
package com.students.students.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.students.students.service.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    private final Path storagePath;

    @Autowired
    public StorageServiceImpl() throws IOException {
        // Using Path.of for better readability
        this.storagePath = Path.of("src", "main", "resources", "static", "media");
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }
    }

    @Override
    public String create(MultipartFile file, String fileType) {
        String filePath = null;

        try (InputStream inputStream = file.getInputStream()) {
            String fileName = Instant.now().getEpochSecond() + "_"
                    + StringUtils.cleanPath(file.getOriginalFilename());

            Files.copy(inputStream, this.storagePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // Use switch expression for better readability
            switch (fileType) {
			case "video/mp4" :
				filePath = "/media/mp4/" + fileName;
				break;
			case "image/jpg" :
			case "image/jpeg" :
				filePath = "/media/jpg/" + fileName;
				break;
			case "image/png" :
			default :
				filePath = "/media/png/" + fileName;
				break;
		}
        } catch (IOException e) {
            LOGGER.error("Failed to create file", e);
        }

        return filePath;
    }

    @Override
    public byte[] load(String fileName) {
        byte[] retBytes = null;

        try {
            Path filePath = this.storagePath.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                retBytes = resource.getInputStream().readAllBytes();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load file", e);
        }

        return retBytes;
    }

    @Override
    public boolean delete(String filePath) {
        Path fileToDelete = this.storagePath.resolve(filePath);

        try {
            if (Files.exists(fileToDelete) && !Files.isDirectory(fileToDelete)) {
                Files.delete(fileToDelete);
                return true;
            } else {
                LOGGER.warn("File not found or is a directory: {}", filePath);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to delete file: {}", filePath, e);
        }

        return false;
    }

    @Override
    public String update(MultipartFile file, String fileType, String filepath) {
        String newFilePath = null;

        try (InputStream inputStream = file.getInputStream()) {
            String fileName = Instant.now().getEpochSecond() + "_"
                    + StringUtils.cleanPath(file.getOriginalFilename());

            // Copy the new file to storagePath
            Files.copy(inputStream, this.storagePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // Use switch expression for better readability
            switch (fileType) {
                case "video/mp4":
                    newFilePath = "/media/mp4/" + fileName;
                    break;
                case "image/jpg":
                case "image/jpeg":
                    newFilePath = "/media/jpg/" + fileName;
                    break;
                case "image/png":
                default:
                    newFilePath = "/media/png/" + fileName;
                    break;
            }

            // Delete the old file if the filepath is not null or empty
            if (StringUtils.hasText(filepath)) {
                delete(filepath);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to update file", e);
        }

        return newFilePath;
    }


    @Override
    public void clearAll() {
        try {
            // Use Files.walk to traverse the directory and delete files
            Files.walk(this.storagePath)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        LOGGER.error("Failed to delete file during clearAll: {}", file, e);
                    }
                });
        } catch (IOException e) {
            LOGGER.error("Failed to clear all files", e);
        }
    }


    @Override
    public boolean check(String filePath) {
        Path filePathPath = this.storagePath.resolve(filePath);

        try {
            return Files.exists(filePathPath) && Files.isRegularFile(filePathPath) && Files.isReadable(filePathPath);
        } catch (SecurityException e) {
            LOGGER.error("Security exception while checking file: {}", filePath, e);
            return false;
        }
    }
}
