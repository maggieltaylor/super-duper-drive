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

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> selectAll(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId} AND userid=#{userId}")
    int delete(Long id, Integer userId);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialid=#{credentialId} AND userid=#{userId}")
    int update(Credential credential, Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE username=#{username} AND userid=#{userId}")
    Credential selectByUsername(String username, Integer userId);
}
