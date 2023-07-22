package com.learn.sys.service.impl;

import com.learn.sys.entity.History;
import com.learn.sys.mapper.HistoryMapper;
import com.learn.sys.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物历史信息 服务实现类
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

}
