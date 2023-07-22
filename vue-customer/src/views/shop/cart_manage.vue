<template>
    <div>
        <!--搜索栏-->
        <el-card id="search">
            <el-row>
                <el-col :span="12">
                    <el-input v-model="searchModel.goodid" placeholder="商品ID"></el-input>
                    <el-button @click="getGoodList" type="primary">查询</el-button>
                </el-col>
            </el-row>
        </el-card>

        <!--商品信息展示-->
        <el-card>
            <el-table :data="goodsList" stripe style="width: 100%">
                <el-table-column prop="good.id" label="商品ID" width="80">
                </el-table-column>
                <el-table-column prop="good.goodName" label="商品名" width="180">
                </el-table-column>
                <el-table-column prop="cart.cnt" label="已选数量" width="100">
                </el-table-column>
                <el-table-column prop="good.productTime" label="生产时间" width="180">
                </el-table-column>
                <el-table-column prop="good.model" label="商品型号" width="200">
                </el-table-column>
                <el-table-column prop="good.price" label="售价" width="120">
                </el-table-column>
                <el-table-column prop="good.productor" label="生产厂商">
                </el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button @click="openEditUi(scope.row.cart.id, scope.row.cart.cnt, scope.row.cart.goodType)"
                            type="primary" size="mini">修改数量</el-button>
                        <el-button @click="deleteGood(scope.row.cart.id)" type="danger" size="mini">移出购物车</el-button>
                        <el-button type="success" @click="buy(scope.row.cart.id)" size="mini">购买</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!--分页组件-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="searchModel.pageNo" :page-sizes="[5, 10]" :page-size="searchModel.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>

        <!--用户信息编辑对话框-->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="cartForm" ref="goodFormRef">
                <el-form-item label="修改数量" :label-width="formLabelWidth">
                    <el-input v-model="cartForm.cnt" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveGood">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import goodApi from "@/api/goodManage"
import cartApi from "@/api/cartManage"

export default {
    data() {
        return {
            cartForm: {},
            goodform: {},
            formLabelWidth: '130px',
            dialogFormVisible: false,
            title: "",
            total: 0,
            goodsList: [],
            searchModel: {
                pageNo: 1,
                pageSize: 10,
            },

        }
    },

    methods: {
        buy(id) {
            this.$confirm('将使用支付宝进行付款?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                cartApi.buy(id).then(response => {
                    this.dialogFormVisible = false;
                    this.getGoodList();
                    this.$message({
                        type: 'success',
                        message: '购买成功!'
                    });
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消支付'
                });
            });
        },

        saveGood() {
            cartApi.updateGood(this.cartForm).then(response => {
                this.$message({
                    message: response.message,
                    type: 'success'
                });
                this.dialogFormVisible = false;
                this.getGoodList(this.searchModel);
            });
        },

        deleteGood(id) {
            this.$confirm('是否将该货物移除购物车, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                cartApi.deleteGood(id).then(response => {
                    if (response.code == 1) {
                        this.$message({
                            type: 'success',
                            message: response.message
                        });
                    } else {
                        this.$message({
                            type: 'false',
                            message: response.message
                        });
                    }
                });
                this.getGoodList();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        clearForm() {
            this.cartForm = {};
            this.$refs.goodFormRef.clearValidate();
        },

        openEditUi(id, cnt, good) {
            this.title = '修改信息';
            this.cartForm.userid = localStorage.getItem('token');
            this.cartForm.goodType = good;
            this.cartForm.id = id;
            this.dialogFormVisible = true;
        },

        handleSizeChange(pageSize) {
            this.searchModel.pageSize = pageSize;
            this.getGoodList();
        },

        handleCurrentChange(pageNo) {
            this.searchModel.pageNo = pageNo;
            this.getGoodList();
        },

        getGoodList() {
            this.searchModel.userid = localStorage.getItem('token');
            cartApi.getGoodList(this.searchModel).then(response => {
                this.total = response.data.total;
                this.goodsList = response.data.list;
            });
        },
    },

    created() {
        this.getGoodList();
    }
}

</script>

<style>
#search .el-input {
    width: 200px;
    margin: 10px;
}

#search .el-button {
    margin: 8px;
}

.el-dialog .el-input {
    width: 70%;
}
</style>