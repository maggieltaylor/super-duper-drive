package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    private final NotesMapper notesMapper;
    private final UserMapper userMapper;

    public NotesService(NotesMapper notesMapper, UserMapper userMapper) {
        this.notesMapper = notesMapper;
        this.userMapper = userMapper;
    }

    public int addNote(Note note, String username) {
        if (note.getNoteId() != null) {
            return notesMapper.update(note);
        } else {
            User user = userMapper.selectByUsername(username);
            note.setUserId(user.getUserId());
            return notesMapper.insert(note);
        }
    }

    public List<Note> getNotes() {
        return notesMapper.selectAll();
    }

    public int deleteNote(Long id) {
        return notesMapper.delete(id);
    }
}
