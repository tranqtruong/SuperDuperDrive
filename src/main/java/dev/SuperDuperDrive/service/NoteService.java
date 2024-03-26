package dev.SuperDuperDrive.service;

import dev.SuperDuperDrive.entity.Note;
import dev.SuperDuperDrive.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void saveNote(Note note) {
        if (note.getNoteid() == null) {
            noteMapper.insertNote(note);
            return;
        }

        if (doesNoteBelongToUser(note.getNoteid(), note.getEmail())) {
            noteMapper.updateNote(note);
        }
    }

    public boolean doesNoteBelongToUser(int nodeid, String email) {
        Note note = noteMapper.selectNoteById(nodeid);
        if (note == null) {
            return false;
        }
        return note.getEmail().equals(email);
    }

    public int calculateTotalPages(String email, int pageSize) {
        int totalNotes = noteMapper.countNotesByEmail(email);
        return (int) Math.ceil((double) totalNotes / pageSize);
    }

    public List<Note> pagingNotes(String email, int pageNumber, int pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        return noteMapper.selectPageNotes(email, pageSize, startIndex);
    }

    public int deleteNote(int noteid, String email) {
        return noteMapper.deleteNote(noteid, email);
    }
}
