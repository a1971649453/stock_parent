package com.itheima.stock.mapper;

import com.itheima.stock.pojo.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.itheima.stock.pojo.SysUserRole
 */
@Mapper
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * 根据用户id查询角色集合
     * @param userId
     * @return
     */
    List<String> findRoleIdsByUserId(@Param("userId") String userId);

    /**
     * 根据用户id删除关联的角色
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 批量插入信息
     * @param list
     * @return
     */
    int insertBatch(@Param("urs") List<SysUserRole> list);
}




