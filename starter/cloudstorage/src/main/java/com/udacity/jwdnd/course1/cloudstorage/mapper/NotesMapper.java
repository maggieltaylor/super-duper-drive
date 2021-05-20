package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Insert({"INSERT INTO NOTES (noteid, notetitle, notedescription, userid) " +
            "VALUES(#{noteId}, #{noteTitle}, #{noteDescription}, #{userId})"})
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> selectAll(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId} AND userid=#{userId}")
    int delete(Long id, Integer userId);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid =#{noteId} AND userid=#{userId}")
    int update(Note note, Integer userId);

    @Select("SELECT * FROM NOTES WHERE notetitle=#{noteTitle} AND notedescription=#{noteDescription} AND userid=#{userId}")
    Note selectByTitleAndDescription(String noteTitle, String noteDescription, Integer userId);
}
