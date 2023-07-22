package com.learn.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.common.Result;
import com.learn.sys.entity.Cart;
import com.learn.sys.entity.History;
import com.learn.sys.service.IGoodsService;
import com.learn.sys.service.IHistoryService;
import com.learn.sys.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物历史信息 前端控制器
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private IHistoryService historyService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IGoodsService goodsService;

    @GetMapping("list")
    public Result<?> getHistoryList(@RequestParam(value = "goodid", required = false) String goodid,
                                    @RequestParam(value = "pageNo") int pageNo,
                                    @RequestParam(value = "pageSize") int pageSize,
                                    @RequestParam(value = "userid") Integer userid){
        LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(goodid != null && !goodid.isEmpty(), History::getBuytype, goodid);
        queryWrapper.eq(History::getUserid, userid);

        Page<History> page = new Page<>(pageNo, pageSize);
        historyService.page(page, queryWrapper);

        List<History> list = page.getRecords();
        List<Map<String, Object>> res = new ArrayList<>();

        // 将货物信息传到前端
        for (int i = 0; i < list.size(); ++i) {
            Map<String, Object> mp = new HashMap<>();
            mp.put("history", list.get(i));
            mp.put("good", goodsService.getById(list.get(i).getBuytype()));
            res.add(i, mp);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("list", res);

        return Result.success(data);
    }
}
