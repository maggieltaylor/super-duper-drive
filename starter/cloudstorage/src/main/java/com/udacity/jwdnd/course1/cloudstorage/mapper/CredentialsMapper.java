package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Insert({"INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) " +
            "VALUES(#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userId})"})
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> selectAll();

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    int delete(Long id);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialid =#{credentialId}")
    int update(Credential credential);
}
