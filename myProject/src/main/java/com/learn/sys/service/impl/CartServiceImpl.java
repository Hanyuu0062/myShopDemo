package com.learn.sys.service.impl;

import com.learn.sys.entity.Cart;
import com.learn.sys.mapper.CartMapper;
import com.learn.sys.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车信息 服务实现类
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
