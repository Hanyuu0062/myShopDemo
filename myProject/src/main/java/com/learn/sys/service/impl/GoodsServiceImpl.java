package com.learn.sys.service.impl;

import com.learn.sys.entity.Goods;
import com.learn.sys.mapper.GoodsMapper;
import com.learn.sys.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 货物信息 服务实现类
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
