package dev.SuperDuperDrive.service;

import dev.SuperDuperDrive.entity.File;
import dev.SuperDuperDrive.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int storeFile(MultipartFile fileUpload, String email) throws IOException {
        File file = new File();
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContenttype(fileUpload.getContentType());
        file.setEmail(email);
        file.setFilesize(String.valueOf(fileUpload.getSize()));
        file.setFiledata(fileUpload.getBytes());

        return fileMapper.storeFile(file);
    }

    public int deleteFile(Integer fileId, String email) {
        return fileMapper.deleteFile(fileId, email);
    }

    public boolean isFileAlreadyExists(String filename, String email) {
        File file = fileMapper.getFileByName(filename, email);
        if (file != null) {
            return true;
        }
        return false;
    }

    public File getFile(Integer fileId, String email) {
        return fileMapper.getFileById(fileId, email);
    }

    public List<File> getFiles(String email) {
        return fileMapper.selectAllFiles(email);
    }
}
