package dev.SuperDuperDrive.mapper;

import dev.SuperDuperDrive.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO USERS (email, salt, password, firstname, lastname) VALUES (#{email}, #{salt}, #{password}, #{firstname}, #{lastname})")
    int insertUser(User user);

    @Select("SELECT * FROM USERS WHERE email = #{email}")
    User selectUser(@Param("email") String email);

    @Update("UPDATE USERS SET salt = #{salt}, password = #{password} WHERE email = #{email}")
    int updateUser(User user);
}
