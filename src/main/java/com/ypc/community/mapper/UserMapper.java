package com.ypc.community.mapper;

import com.ypc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *@创建人 yinpengcheng
 *@创建时间 2021/4/29
 */
@Mapper
@Repository//避免AuthorizeController中报红线错误
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
