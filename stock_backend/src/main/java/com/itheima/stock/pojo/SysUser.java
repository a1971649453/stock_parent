package com.itheima.stock.pojo;

import com.itheima.stock.vo.resp.PermissionRespNodeVo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

/**
 * 用户表
 * @TableName sys_user
 */
@Data
public class SysUser implements UserDetails {
    /**
     * 用户id
     */
    private String id;

    /**
     * 账户
     */
    private String username;

    /**
     * 用户密码密文
     */
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱(唯一)
     */
    private String email;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    private Integer status;

    /**
     * 性别(1.男 2.女)
     */
    private Integer sex;

    /**
     * 是否删除(1未删除；0已删除)
     */
    private Integer deleted;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 更新人
     */
    private String updateId;

    /**
     * 创建来源(1.web 2.android 3.ios )
     */
    private Integer createWhere;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 权限集合
     */
    private List<GrantedAuthority> authorities;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

    /**
     * 默认账户没有过期
     *  true:账户没有过期
     */
    private boolean isAccountNonExpired=true;
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }


    /**
     * true：账户默认不锁定
     */
    private boolean isAccountNonLocked=true;
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }

    /**
     * true:默认密码没有过期
     */
    private boolean isCredentialsNonExpired=true;
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }

    /**
     * true:账户有效
     */
    private boolean isEnabled=true;

//    @Override
//    public boolean isEnabled() {
//        return false;
//    }

    /**
     * 组装的是用户拥有的权限对应的前端的侧边菜单栏（不包含按钮）
     */
    private List<PermissionRespNodeVo> menus;

    /**
     * 用户用户的按钮权限
     */
    private List<String> permissions;
}