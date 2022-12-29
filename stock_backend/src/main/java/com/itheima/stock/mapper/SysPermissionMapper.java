package com.itheima.stock.mapper;

import com.itheima.stock.pojo.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.itheima.stock.pojo.SysPermission
 */
@Mapper
public interface SysPermissionMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    /**
     * 获取所有权限集合
     * @return
     */
    List<SysPermission> findAll();

    /**
     * 根据权限父类id查询对应子集权限
     * @param permissionId
     * @return
     */
    int findChildrenCountByParentId(@Param("permissionId") String permissionId);

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    List<SysPermission> getPermissionByUserId(@Param("userId") String userId);
}




