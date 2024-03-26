package dev.SuperDuperDrive.mapper;

import dev.SuperDuperDrive.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("""
            INSERT INTO FILES (filename, contenttype, filesize, filedata, email) \
            VALUES (#{filename}, #{contenttype}, #{filesize}, #{filedata}, #{email})\
            """)
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int storeFile(File file);

    @Select("SELECT * FROM FILES WHERE email = #{email}")
    List<File> selectAllFiles(@Param("email") String email);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND email = #{email}")
    int deleteFile(@Param("fileId") Integer fileId, @Param("email") String email);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND email = #{email}")
    File getFileById(@Param("fileId") Integer fileId, @Param("email") String email);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND email = #{email}")
    File getFileByName(@Param("filename") String filename, @Param("email") String email);
}
