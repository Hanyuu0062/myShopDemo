package com.learn.sys.service.impl;

import com.learn.sys.entity.Users;
import com.learn.sys.mapper.UsersMapper;
import com.learn.sys.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
