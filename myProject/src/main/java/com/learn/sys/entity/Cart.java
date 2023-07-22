package com.learn.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 购物车信息
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@TableName("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 货物数量
     */
    private Integer cnt;

    /**
     * 用户名
     */
    private Integer userid;

    /**
     * 货物id
     */
    private Integer goodType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public Integer getGoodType() {
        return goodType;
    }

    public void setGoodType(Integer goodType) {
        this.goodType = goodType;
    }

    @Override
    public String toString() {
        return "Cart{" +
            "id=" + id +
            ", cnt=" + cnt +
            ", username=" + userid +
            ", goodType=" + goodType +
        "}";
    }
}
