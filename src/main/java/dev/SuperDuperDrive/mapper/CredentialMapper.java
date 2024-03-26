package dev.SuperDuperDrive.mapper;

import dev.SuperDuperDrive.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, keyy, password, email) VALUES (#{url}, #{username}, #{keyy}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    void insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, keyy = #{keyy}, password = #{password} WHERE credentialid = #{credentialid} AND email = #{email}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND email = #{email}")
    int deleteCredential(@Param("credentialid") Integer credentialid, @Param("email") String email);

    @Select("SELECT * FROM CREDENTIALS WHERE email = #{email}")
    List<Credential> selectCredentials(@Param("email") String email);

    @Select("SELECT * FROM CREDENTIALS WHERE email = #{email} AND (url LIKE CONCAT('%', #{keyword}, '%') OR username LIKE CONCAT('%', #{keyword}, '%'))")
    List<Credential> searchCredentials(@Param("email") String email, @Param("keyword") String keyword);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid} AND email = #{email}")
    Credential getCredential(@Param("credentialid") Integer credentialid, @Param("email") String email);
}
