-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
id int auto_increment COMMENT'主键',
username varchar(32) COLLATE utf8_bin not null COMMENT '用户名',
password varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
etimes int DEFAULT 0 COMMENT'连续失败的登录次数',
status int DEFAULT 1 COLLATE utf8_bin NOT NULL COMMENT '1正常 0封号状态',
phone_num varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
email varchar(32) COLLATE utf8_bin NOT NULL COMMENT '邮箱',
cost float DEFAULT 0.0 COLLATE utf8_bin NOT NULL COMMENT '累计消费',
level varchar(32) DEFAULT '铜牌客户' COLLATE utf8_bin NOT NULL COMMENT '用户级别',
create_time datetime NOT NULL COMMENT '创建时间',
PRIMARY KEY (id) USING BTREE,
UNIQUE KEY `idx_username` (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO users (id, username, password, status, phone_num, email, cost, level, create_time)
VALUES (1, 'user1', '25d55ad283aa400af464c76d713c07ad', 1, '12345678901', 'user1@example.com', 0.0, '铜牌客户', '2022-01-01 09:00:00');

INSERT INTO users (id, username, password, status, phone_num, email, cost, level, create_time)
VALUES (2, 'user2', '25d55ad283aa400af464c76d713c07ad', 1, '12345678902', 'user2@example.com', 0.0, '铜牌客户', '2022-02-15 14:30:00');

INSERT INTO users (id, username, password, status, phone_num, email, cost, level, create_time)
VALUES (3, 'user3', '25d55ad283aa400af464c76d713c07ad', 1, '12345678903', 'user3@example.com', 0.0, '铜牌客户', '2022-03-05 15:45:00');

INSERT INTO users (id, username, password, status, phone_num, email, cost, level, create_time)
VALUES (4, 'user4', '25d55ad283aa400af464c76d713c07ad', 1, '12345678904', 'user4@example.com', 0.0, '铜牌客户', '2022-02-28 10:15:00');


-- ----------------------------
-- Table structure for boss
-- ----------------------------
DROP TABLE IF EXISTS boss;
CREATE TABLE boss (
id int not null COMMENT'主键',
username varchar(32) COLLATE utf8_bin not null COMMENT '用户名',
password varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
PRIMARY KEY (id) USING BTREE,
UNIQUE KEY `idx_username` (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理员信息';

-- ----------------------------
-- Records of boss
-- ----------------------------
INSERT INTO boss (id, username, password)
VALUES
(1, 'admin', '0ff93d26c75567d143ff942b5b547488');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS goods;
CREATE TABLE goods (
id int auto_increment COMMENT'主键',
good_name varchar(64) COLLATE utf8_bin not null COMMENT '商品名',
stock int not null COMMENT '库存数量',
product_time datetime NOT NULL COMMENT '生产时间',
model varchar(64) COLLATE utf8_bin not null COMMENT '商品型号',
price float not null COMMENT '出售价',
purchase_price float not null COMMENT '进货价',
productor varchar(64) COLLATE utf8_bin NOT NULL COMMENT '生产厂商',
PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='货物信息';

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (1, 'iphone 12', 100, '2022-01-01 09:00:00', '8+256g', 9999.99, 8000.00, 'Apple');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (2, 'Samsung Galaxy S21', 50, '2022-02-01 10:30:00', '12+512g', 8999.99, 7500.00, 'Samsung');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (3, 'Sony PlayStation 5', 20, '2022-03-01 14:45:00', 'Standard Edition', 3999.99, 3200.00, 'Sony');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (4, 'Dell XPS 13', 30, '2022-04-01 16:20:00', 'i7-1165G7, 16GB RAM, 512GB SSD', 7999.99, 6500.00, 'Dell');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (5, 'HP Pavilion 15', 50, '2022-05-01 09:00:00', 'i5-1135G7, 8GB RAM, 256GB SSD', 4999.99, 4000.00, 'HP');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (6, 'Canon EOS Rebel T7i', 30, '2022-06-01 10:30:00', '18-55mm Lens Kit', 899.99, 700.00, 'Canon');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (7, 'Nike Air Max 270', 100, '2022-07-01 14:45:00', 'Black/White, Size 10', 1299.99, 900.00, 'Nike');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (8, 'LG OLED55C1PUB', 20, '2022-08-01 16:20:00', '55" 4K Smart OLED TV', 5999.99, 5000.00, 'LG');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (9, 'Lenovo ThinkPad X1 Carbon', 40, '2022-09-01 11:15:00', 'i7-1165G7, 16GB RAM, 1TB SSD', 8999.99, 7500.00, 'Lenovo');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (10, 'Apple AirPods Pro', 80, '2022-10-01 13:30:00', 'Bluetooth Earphones', 1299.99, 1000.00, 'Apple');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (11, 'Samsung 65" QLED TV', 15, '2022-11-01 15:45:00', 'Q80A Series, 4K UHD', 7999.99, 6500.00, 'Samsung');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (12, 'Sony WH-1000XM4', 25, '2022-12-01 17:30:00', 'Wireless Noise-Canceling Headphones', 1599.99, 1200.00, 'Sony');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (13, 'Xiaomi Mi 11', 60, '2023-01-01 10:00:00', '12+256GB, Snapdragon 888', 3999.99, 3000.00, 'Xiaomi');

INSERT INTO goods (id, good_name, stock, product_time, model, price, purchase_price, productor)
VALUES (14, 'Logitech G502 HERO', 70, '2023-02-01 12:15:00', 'Wired Gaming Mouse', 299.99, 200.00, 'Logitech');



-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS history;
CREATE TABLE history (
id int auto_increment not null COMMENT'主键',
userid int not null COMMENT '用户id',
cnt int not null COMMENT'购买数量',
buytime datetime NOT NULL COMMENT '购买时间',
buytype int not null COMMENT '购买物品的id',
PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='购物历史信息';

-- ----------------------------
-- Records of history
-- ----------------------------



-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS cart;
CREATE TABLE cart (
id int auto_increment not null COMMENT'主键',
cnt int DEFAULT 1 not null COMMENT '货物数量',
userid int not null COMMENT '用户id',
good_type int not null COMMENT '货物id',

PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='购物车信息';

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO cart (id, cnt, userid, good_type)
VALUES (1, 2, 1, 1);

INSERT INTO cart (id, cnt, userid, good_type)
VALUES (2, 3, 1, 3);

INSERT INTO cart (id, cnt, userid, good_type)
VALUES (3, 1, 2, 2);

INSERT INTO cart (id, cnt, userid, good_type)
VALUES (4, 4, 2, 4);
