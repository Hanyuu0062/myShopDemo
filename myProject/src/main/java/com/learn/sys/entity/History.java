package com.learn.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物历史信息
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-19
 */
@TableName("history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userid;

    /**
     * 购买数量
     */
    private Integer cnt;

    /**
     * 购买时间
     */
    private LocalDateTime buytime;

    /**
     * 购买物品的id
     */
    private Integer buytype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
    public LocalDateTime getBuytime() {
        return buytime;
    }

    public void setBuytime(LocalDateTime buytime) {
        this.buytime = buytime;
    }
    public Integer getBuytype() {
        return buytype;
    }

    public void setBuytype(Integer buytype) {
        this.buytype = buytype;
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + id +
            ", userid=" + userid +
            ", cnt=" + cnt +
            ", buytime=" + buytime +
            ", buytype=" + buytype +
        "}";
    }
}
