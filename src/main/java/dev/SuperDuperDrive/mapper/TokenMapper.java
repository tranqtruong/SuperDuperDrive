package dev.SuperDuperDrive.mapper;

import dev.SuperDuperDrive.entity.Token;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TokenMapper {
    @Insert("INSERT INTO TOKEN (token) VALUES (#{token})")
    int insertToken(Token token);

    @Select("SELECT * FROM TOKEN WHERE token = #{token}")
    Token selectToken(@Param("token") String token);

    @Update("UPDATE TOKEN SET used = #{used} WHERE token = #{token}")
    int updateToken(Token token);

    @Delete("DELETE FROM TOKEN WHERE token = #{token}")
    int deleteToken(@Param("token") String token);
}
