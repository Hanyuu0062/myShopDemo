package com.learn.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.learn.common.Result;
import com.learn.sys.entity.*;
import com.learn.sys.service.IHistoryService;
import com.learn.sys.service.IUsersService;
import com.learn.sys.service.IGoodsService;
import com.learn.sys.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private IUsersService usersService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IHistoryService historyService;


    /**
     * 用户的注册
     * @param users
     * @return
     */
    @PostMapping("/sign")
    public Result<?> sign(@RequestBody Users users) {
        // 用户名不重复
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, users.getUsername());
        Users user = usersService.getOne(queryWrapper);
        if (user!=null){
            return Result.fail("该用户名已存在");
        }

        // 密码是否符合规范
        String regex =  "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\.)[A-Za-z\\d.]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(users.getPassword());
        if(!matcher.matches()){
            return Result.fail("密码格式不对，必须是大小写字母、数字、符号的组合");
        }

        // 密码md5加密
        users.setPassword(DigestUtils.md5DigestAsHex(users.getPassword().getBytes()));
        users.setCreateTime(LocalDateTime.now());
        usersService.save(users);

        return Result.success("注册成功");
    }

    /**
     * 忘记密码
     * @param name
     * @param email
     * @return
     */
    @GetMapping("/resetps")
    public Result<?> resetPassword(@RequestParam("username") String name,
                                   @RequestParam("email") String email) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, name);
        Users user = usersService.getOne(queryWrapper);

        if (!user.getEmail().equals(email)){
            return Result.fail("邮箱和注册邮箱不同");
        }

        user.setPassword(DigestUtils.md5DigestAsHex("12345678".getBytes()));
        usersService.updateById(user);
        return Result.success("密码重置成功");
    }

    /**
     * 用户登录
     * @param users
     * @return
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Users users) {
        String password = users.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 查询,根据用户名
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, users.getUsername());
        Users login_users = usersService.getOne(queryWrapper);

        if (login_users == null) {
            return Result.fail("登录失败，该账号不存在");
        }

        if (login_users.getStatus() == 0) {
            return Result.fail("登录失败，该账号处于封禁状态");
        }

        // 比对密码
        if (!login_users.getPassword().equals(password)) {
            // 封号
            Integer times = login_users.getEtimes();
            if (times == 4) {
                login_users.setStatus(0);
            } else {
                login_users.setEtimes(times + 1);
            }
            usersService.updateById(login_users);
            return Result.fail("登录失败，密码错误");
        }

        login_users.setEtimes(0);
        usersService.updateById(login_users);
        Map<String, Object> mp = new HashMap<>();
        mp.put("token", login_users.getId());
        // 简化 token 为 id
        log.info("登陆成功...");
        return Result.success(mp);
    }

    /**
     * 登录时获得信息
     * @param token
     * @return
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo(@RequestParam("token") String token) {
        // 查询,根据用户 id
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getId, token);
        Users login_users = usersService.getOne(queryWrapper);

        if (login_users == null) {
            return Result.fail("登录失败");
        } else {
            Map<String, Object> mp = new HashMap<>();
            mp.put("name", login_users.getId());
            mp.put("avatar", "https://i0.hdslb.com/bfs/new_dyn/490fc2e50ed85bf9fb62986f9e78499b692772.jpg");
            mp.put("roles", null);
            return Result.success(mp, "登录成功");
        }
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        // delete token
        return Result.success("Success logout");
    }


    /**
     * 管理员密码的修改
     * @param token 登录token
     * @param op    原密码密码
     * @param np    修改的密码
     * @return
     */
    @GetMapping("/passedit")
    public Result<?> updateBossPassword(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "oldPassword") String op,
                                        @RequestParam(value = "newPassword") String np) {
        Users user = usersService.getById(token);
        if (user.getPassword().equals(DigestUtils.md5DigestAsHex(op.getBytes()))) {
            user.setPassword(DigestUtils.md5DigestAsHex(np.getBytes()));
            usersService.updateById(user);
            return Result.success("修改成功");
        } else {
            return Result.fail("原密码错误");
        }
    }

    /**
     * 更新等级
     * @param user
     * @return
     */
    public static Users updateLevel(Users user) {
        double cost = user.getCost();
        if (cost < 100) {
            user.setLevel("铜牌客户");
        } else if (cost < 1000) {
            user.setLevel("银牌客户");
        } else if (cost < 10000) {
            user.setLevel("金牌客户");
        } else {
            user.setLevel("魔王客户");
        }
        return user;
    }

    /**
     * 购买单一商品
     * @param cart
     * @return
     */
    @PostMapping("/goodbuy")
    public Result<?> buyOneGood(@RequestBody Cart cart) {
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
        buyUser = updateLevel(buyUser);
        usersService.updateById(buyUser);

        // 存储订单
        History history=new History();
        history.setUserid(cart.getUserid());
        history.setBuytype(cart.getGoodType());
        history.setBuytime(LocalDateTime.now());
        history.setCnt(cart.getCnt());
        historyService.save(history);

        return Result.success("购买成功");
    }
}
