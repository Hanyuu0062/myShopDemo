package com.learn.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 货物信息
 * </p>
 *
 * @author Hanyuu
 * @since 2023-07-13
 */
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 物品名
     */
    private String goodName;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 生产时间
     */
    private LocalDateTime productTime;

    /**
     * 物品型号
     */
    private String model;

    /**
     * 出售价
     */
    private Float price;

    /**
     * 进货价
     */
    private Float purchasePrice;

    /**
     * 生产厂商
     */
    private String productor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public LocalDateTime getProductTime() {
        return productTime;
    }

    public void setProductTime(LocalDateTime productTime) {
        this.productTime = productTime;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    @Override
    public String toString() {
        return "Goods{" +
            "id=" + id +
            ", goodName=" + goodName +
            ", stock=" + stock +
            ", productTime=" + productTime +
            ", model=" + model +
            ", price=" + price +
            ", purchasePrice=" + purchasePrice +
            ", productor=" + productor +
        "}";
    }
}
