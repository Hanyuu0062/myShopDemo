package com.learn.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.common.Result;
import com.learn.sys.entity.*;
import com.learn.sys.service.ICartService;
import com.learn.sys.service.IGoodsService;
import com.learn.sys.service.IHistoryService;
import com.learn.sys.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车信息 前端控制器
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IHistoryService historyService;

    /**
     * 购物车的商品添加
     *
     * @param cart
     * @return
     */
    @PostMapping("/goodadd")
    public Result<?> cartGoodAdd(@RequestBody Cart cart) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserid, cart.getUserid());
        queryWrapper.eq(Cart::getGoodType, cart.getGoodType());
        Cart old_cart = cartService.getOne(queryWrapper);
        if (old_cart == null) {
            cartService.save(cart);
        } else {
            old_cart.setCnt(old_cart.getCnt() + cart.getCnt());
            cartService.updateById(old_cart);
        }
        return Result.success("添加成功");
    }

    /**
     * 根据用户id获取购物车的信息
     *
     * @param goodid
     * @param pageNo
     * @param pageSize
     * @param userid
     * @return
     */
    @GetMapping("/cartlist")
    public Result<?> getGoodList(@RequestParam(value = "goodid", required = false) String goodid,
                                 @RequestParam(value = "pageNo") int pageNo,
                                 @RequestParam(value = "pageSize") int pageSize,
                                 @RequestParam(value = "userid") Integer userid) {

        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(goodid != null && !goodid.isEmpty(), Cart::getGoodType, goodid);
        queryWrapper.eq(Cart::getUserid, userid);

        Page<Cart> page = new Page<>(pageNo, pageSize);
        cartService.page(page, queryWrapper);

        List<Cart> list = page.getRecords();
        List<Map<String, Object>> res = new ArrayList<>();

        // 将货物信息传到前端
        for (int i = 0; i < list.size(); ++i) {
            Map<String, Object> mp = new HashMap<>();
            mp.put("cart", list.get(i));
            mp.put("good", goodsService.getById(list.get(i).getGoodType()));
            res.add(i, mp);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("list", res);

        log.info("查询成功....");
        return Result.success(data);
    }

    /**
     * 更新该商品数量
     *
     * @param cart
     * @return
     */
    @PostMapping("/update")
    public Result<?> updateCart(@RequestBody Cart cart) {
        int cnt = cart.getCnt();
        if (cnt <= 0) {
            return Result.fail("数量不能小于或等于0");
        }

        if (goodsService.getById(cart.getGoodType()).getStock() < cnt) {
            return Result.fail("选择数量超过库存");
        }

        cartService.updateById(cart);
        return Result.success("数量修改成功");
    }

    /**
     * 在购物车中买东西
     * @param id
     * @return
     */
    @GetMapping("/buy")
    public Result<?> buyGood(@RequestParam(value = "id") Integer id) {
        Cart cart = cartService.getById(id);
        Users buyUser = usersService.getById(cart.getUserid());
        Goods good = goodsService.getById(cart.getGoodType());

        // 货物信息的更新
        if (good.getStock() < cart.getCnt()) {
            return Result.fail("库存不足");
        }
        good.setStock(good.getStock() - cart.getCnt());
        goodsService.updateById(good);

        // 用户信息的更新
        buyUser.setCost(buyUser.getCost() + cart.getCnt() * good.getPrice());
        buyUser = UsersController.updateLevel(buyUser);
        usersService.updateById(buyUser);


        // 存储订单
        History history = new History();
        history.setUserid(cart.getUserid());
        history.setBuytype(cart.getGoodType());
        history.setBuytime(LocalDateTime.now());
        history.setCnt(cart.getCnt());
        historyService.save(history);

        cartService.removeById(cart);

        return Result.success("购买成功");
    }

    /**
     * 删除购物车中的商品
     *
     * @param id
     * @return
     */
    @DeleteMapping("/gooddel{id}")
    public Result<?> deleteGood(@PathVariable Integer id) {
        cartService.removeById(id);
        log.info("删除成功" + id);
        return Result.success("删除成功");
    }


}
