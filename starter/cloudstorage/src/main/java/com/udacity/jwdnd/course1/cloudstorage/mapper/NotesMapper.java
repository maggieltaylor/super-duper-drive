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

    @Select("SELECT * FROM NOTES")
    List<Note> selectAll();

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    int delete(Long id);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid =#{noteId}")
    int update(Note note);
}
