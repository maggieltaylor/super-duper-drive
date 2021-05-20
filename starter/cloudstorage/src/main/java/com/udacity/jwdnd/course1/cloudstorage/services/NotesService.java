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
        User user = userMapper.selectByUsername(username);
        if (note.getNoteId() != null) {
            return notesMapper.update(note, user.getUserId());
        } else {
            note.setUserId(user.getUserId());
            return notesMapper.insert(note);
        }
    }

    public List<Note> getNotes(String username) {
        User user = userMapper.selectByUsername(username);
        return notesMapper.selectAll(user.getUserId());
    }

    public int deleteNote(Long id, String username) {
        User user = userMapper.selectByUsername(username);
        return notesMapper.delete(id, user.getUserId());
    }

    public boolean duplicateNote(Note note, String username) {
        User user = userMapper.selectByUsername(username);
        return notesMapper.selectByTitleAndDescription(note.getNoteTitle(), note.getNoteDescription(), user.getUserId()) != null;
    }
}
