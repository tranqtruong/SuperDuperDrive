package dev.SuperDuperDrive.mapper;

import dev.SuperDuperDrive.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, email) VALUES (#{notetitle}, #{notedescription}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    void insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    void updateNote(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note selectNoteById(@Param("noteid") Integer noteid);

    @Select("SELECT * FROM NOTES WHERE email = #{email} LIMIT #{pageSize} OFFSET #{start}")
    List<Note> selectPageNotes(@Param("email") String email, @Param("pageSize") Integer pageSize, @Param("start") Integer start);

    @Select("SELECT COUNT(*) FROM NOTES WHERE email = #{email}")
    int countNotesByEmail(@Param("email") String email);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND email = #{email}")
    int deleteNote(@Param("noteid") Integer noteid, @Param("email") String email);
}
