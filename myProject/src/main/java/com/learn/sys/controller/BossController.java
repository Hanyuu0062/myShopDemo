package com.learn.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.common.Result;
import com.learn.sys.entity.Boss;
import com.learn.sys.entity.Goods;
import com.learn.sys.entity.Users;
import com.learn.sys.service.IBossService;
import com.learn.sys.service.IGoodsService;
import com.learn.sys.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员信息 前端控制器
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Slf4j
@RestController
@RequestMapping("/boss")
public class BossController {
    @Autowired
    private IBossService bossService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 管理员登录
     *
     * @param boss
     * @return
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Boss boss) {
        String password = boss.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 查询,根据用户名
        LambdaQueryWrapper<Boss> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Boss::getUsername, boss.getUsername());
        Boss login_boss = bossService.getOne(queryWrapper);

        if (login_boss == null) {
            return Result.fail("登录失败，该账号不存在");
        }

        // 比对密码
        if (!login_boss.getPassword().equals(password)) {
            return Result.fail("登录失败，账号密码错误");
        } else {
            Map<String, Object> mp = new HashMap<>();
            mp.put("token", login_boss.getId());
            // 简化 token 为 id
            log.info("登陆成功...");
            return Result.success(mp);
        }
    }

    /**
     * 登录时获得信息
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo(@RequestParam("token") String token) {
        // 查询,根据用户 id
        LambdaQueryWrapper<Boss> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Boss::getId, token);
        Boss login_boss = bossService.getOne(queryWrapper);

        if (login_boss == null) {
            return Result.fail("登录失败");
        } else {
            Map<String, Object> mp = new HashMap<>();
            mp.put("name", login_boss.getId());
            mp.put("avatar", "https://i0.hdslb.com/bfs/new_dyn/490fc2e50ed85bf9fb62986f9e78499b692772.jpg");
            mp.put("roles", null);
            return Result.success(mp, "登录成功");
        }
    }

    /**
     * 退出登录
     *
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
     *
     * @param token 登录token
     * @param op    原密码密码
     * @param np    修改的密码
     * @return
     */
    @GetMapping("/passedit")
    public Result<?> updateBossPassword(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "oldPassword") String op,
                                        @RequestParam(value = "newPassword") String np) {
        Boss boss = bossService.getById(token);
        if (boss.getPassword().equals(DigestUtils.md5DigestAsHex(op.getBytes()))) {
            boss.setPassword(DigestUtils.md5DigestAsHex(np.getBytes()));
            bossService.updateById(boss);
            return Result.success("修改成功");
        } else {
            return Result.fail("原密码错误");
        }
    }

    /**
     * 用户的查询
     *
     * @param userid
     * @param username
     * @param pageNo
     * @param pageSize
     * @return 查询到的用户列表
     */
    @GetMapping("/userslist")
    public Result<?> getUserList(@RequestParam(value = "userid", required = false) String userid,
                                 @RequestParam(value = "username", required = false) String username,
                                 @RequestParam(value = "pageNo") int pageNo,
                                 @RequestParam(value = "pageSize") int pageSize) {

        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userid != null && !userid.isEmpty(), Users::getId, userid);
        queryWrapper.eq(username != null && !username.isEmpty(), Users::getUsername, username);

        Page<Users> page = new Page<>(pageNo, pageSize);
        usersService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("list", page.getRecords());
        log.info("查询成功....");
        return Result.success(data);
    }

    /**
     * 用户信息的修改
     *
     * @param user
     * @return
     */
    @PostMapping("/userupdate")
    public Result<?> userUpdate(@RequestBody Users user) {
        String password = user.getPassword();
        if (password == null || password == "") {
            password = usersService.getById(user.getId()).getPassword();
        } else {
            password = DigestUtils.md5DigestAsHex(password.getBytes());
        }
        user.setPassword(password);
        usersService.updateById(user);
        log.info("修改成功....");
        return Result.success("修改成功");
    }

    /**
     * 通过id获取用户的数据
     *
     * @param id
     * @return
     */
    @GetMapping("/iduser")
    public Result<?> getUserById(@RequestParam(value = "id") Integer id) {
        Users user = usersService.getById(id);
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 通过id进行用户删除
     * @param id
     * @return
     */
    @DeleteMapping("/userdel{id}")
    public Result<?> deleteUserById(@PathVariable Integer id) {
        usersService.removeById(id);
        log.info("删除成功" + id);
        return Result.success("删除成功");
    }


    /**
     * 货物查询
     * @param goodid
     * @param goodname
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/goodslist")
    public Result<?> getGoodList(@RequestParam(value = "goodid", required = false) String goodid,
                                 @RequestParam(value = "goodname", required = false) String goodname,
                                 @RequestParam(value = "pageNo") int pageNo,
                                 @RequestParam(value = "pageSize") int pageSize) {

        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(goodid != null && !goodid.isEmpty(), Goods::getId, goodid);
        queryWrapper.eq(goodname != null && !goodname.isEmpty(), Goods::getGoodName, goodname);

        Page<Goods> page = new Page<>(pageNo, pageSize);
        goodsService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("list", page.getRecords());
        log.info("查询成功....");
        return Result.success(data);
    }

    /**
     * 货物的新增
     *
     * @param good 前端返回的货物参数
     * @return
     */
    @PostMapping("/goodadd")
    public Result<?> addGood(@RequestBody Goods good) {
        goodsService.save(good);
        log.info("商品新增成功....");
        return Result.success("商品新增成功....");
    }

    /**
     * 货物信息修改
     *
     * @param good
     * @return
     */
    @PostMapping("/goodupdate")
    public Result<?> updateGood(@RequestBody Goods good) {
        goodsService.updateById(good);
        log.info("修改成功....");
        return Result.success("修改成功");
    }

    /**
     * 通过id获得某个货物的信息
     *
     * @param id
     * @return
     */
    @GetMapping("/idgood")
    public Result<?> getGoodById(@RequestParam(value = "id") Integer id) {
        Goods good = goodsService.getById(id);
        return Result.success(good);
    }

    /**
     * 根据id进行货物的删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/gooddel{id}")
    public Result<?> deleteGoodById(@PathVariable Integer id) {
        goodsService.removeById(id);
        log.info("删除成功" + id);
        return Result.success("删除成功");
    }
}
