package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> selectAll(Integer userId);

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileId}, #{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId} AND userid=#{userId}")
    int delete(@Param("fileId") Long id, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId} AND userid=#{userId}")
    File findById(@Param("fileId") Long id, Integer userId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename} AND userid=#{userId}")
    File findByFilename(String filename, Integer userId);
}
