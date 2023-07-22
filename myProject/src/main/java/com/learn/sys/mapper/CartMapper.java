package com.learn.sys.mapper;

import com.learn.sys.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 购物车信息 Mapper 接口
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */

@Mapper
public interface CartMapper extends BaseMapper<Cart> {

}
