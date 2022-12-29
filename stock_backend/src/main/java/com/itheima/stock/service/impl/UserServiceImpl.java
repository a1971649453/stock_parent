package com.itheima.stock.service.impl;

import com.google.common.base.Strings;
import com.itheima.stock.mapper.SysUserMapper;
import com.itheima.stock.pojo.SysPermission;
import com.itheima.stock.pojo.SysUser;
import com.itheima.stock.service.PermissionService;
import com.itheima.stock.service.UserService;
import com.itheima.stock.utils.IdWorker;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.PermissionRespNodeVo;
import com.itheima.stock.vo.resp.R;
import com.itheima.stock.vo.resp.ResponseCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 金宗文
 * @version 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IdWorker idWorker;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        //1.判断vo是否存在或者用户名是否存在 密码是否存在 或者验证码 或者校验码(SessionId)是否存在
        if (vo==null || Strings.isNullOrEmpty(vo.getUsername()) || Strings.isNullOrEmpty(vo.getPassword())
        || Strings.isNullOrEmpty(vo.getCode()) || Strings.isNullOrEmpty(vo.getRkey())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //校验验证码
        String redisCode = (String) redisTemplate.opsForValue().get(vo.getRkey());
        //比对
        if (redisCode == null || !redisCode.equals(vo.getCode())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //比对成功,快速淘汰校验码,合理利用redis空间 不删的话也会有过期时间
        redisTemplate.delete(vo.getRkey());

        //进行用户逻辑的校验
        //2.根据用户名查询用户信息
        SysUser userInfo = sysUserMapper.findUserByUserName(vo.getUsername());
        if (userInfo == null){
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }

        //3.判断密码是否一致
        if (!passwordEncoder.matches(vo.getPassword(), userInfo.getPassword())) {
            return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
        }
        //4.返回用户信息 属性复制 (两个类之间属性名称一致)
        LoginRespVo respVo = new LoginRespVo();
        BeanUtils.copyProperties(userInfo, respVo);
        //5.权限树组装
        //获取指定用户的权限集合
        List<SysPermission> permissions = permissionService.getPermissionByUserId(userInfo.getId());
        //获取树状权限菜单数据
        List<PermissionRespNodeVo> tree = permissionService.getTree(permissions,"0",true);
        //获取菜单按钮集合
        List<String> authBthPerms = permissions.stream().filter(per -> !Strings.isNullOrEmpty(per.getCode()) && per.getType() == 3)
                .map(per -> per.getCode()).collect(Collectors.toList());
        respVo.setMenus(tree);
        respVo.setPermissions(authBthPerms);


        return R.ok(respVo);
    }

    @Override
    public R<Map> generateCaptcha() {
        //1.生成随机校验码 长度为4位
        String checkCode = RandomStringUtils.randomNumeric(4);
        //2.生成类似sessionId的id作为key,然后校验码作为value存入redis中,设置有效期60s
        String sessionId = String.valueOf(idWorker.nextId());
        redisTemplate.opsForValue().set(sessionId, checkCode, 60, TimeUnit.SECONDS);
        //3.组装map对象
        HashMap<String, String> map = new HashMap<>();
        map.put("code",checkCode);
        map.put("rkey",sessionId);

        //4.返回
        return R.ok(map);
    }
}


























