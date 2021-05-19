package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FilesService {
    private final FilesMapper filesMapper;
    private final UserMapper userMapper;

    public FilesService(FilesMapper filesMapper, UserMapper userMapper) {
        this.filesMapper = filesMapper;
        this.userMapper = userMapper;
    }

    public int addFile(MultipartFile multipartFile, String username) throws IOException {
        User user = userMapper.selectByUsername(username);
        File file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize(), user.getUserId(), multipartFile.getBytes());
        return filesMapper.insert(file);
    }

    public List<File> getFiles() {
        return filesMapper.selectAll();
    }

    public int deleteFile(Long id) {
        return filesMapper.delete(id);
    }

    public File getFile(Long id) {
        return filesMapper.findById(id);
    }

    public boolean duplicateFilename(String filename) {
        return filesMapper.findByFilename(filename) != null;
    }
}
