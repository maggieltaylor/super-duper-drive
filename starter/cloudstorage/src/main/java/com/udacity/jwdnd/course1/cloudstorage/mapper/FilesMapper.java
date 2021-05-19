package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Select("SELECT * FROM FILES")
    List<File> selectAll();

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileId}, #{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int delete(@Param("fileId") Long id);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File findById(Long id);

    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    File findByFilename(String filename);
}
