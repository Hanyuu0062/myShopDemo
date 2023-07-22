package com.learn.sys.service.impl;

import com.learn.sys.entity.Boss;
import com.learn.sys.mapper.BossMapper;
import com.learn.sys.service.IBossService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员信息 服务实现类
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Service
public class BossServiceImpl extends ServiceImpl<BossMapper, Boss> implements IBossService {

}
