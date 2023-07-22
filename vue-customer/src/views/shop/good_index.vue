<template>
    <div>
        <!--搜索栏-->
        <el-card id="search">
            <el-row>
                <el-col :span="12">
                    <el-input v-model="searchModel.goodid" placeholder="商品ID"></el-input>
                    <el-input v-model="searchModel.goodname" placeholder="商品名称"></el-input>
                    <el-button @click="getGoodList" type="primary">查询</el-button>
                </el-col>
                <el-col :span="12" align="right">
                </el-col>
            </el-row>
        </el-card>

        <!--商品信息展示-->
        <el-card>
            <el-table :data="goodsList" stripe style="width: 100%">
                <el-table-column prop="id" label="ID" width="80">
                </el-table-column>
                <el-table-column prop="goodName" label="商品名" width="180">
                </el-table-column>
                <el-table-column prop="productTime" label="生产时间" width="180">
                </el-table-column>
                <el-table-column prop="model" label="商品型号" width="200">
                </el-table-column>
                <el-table-column prop="price" label="售价" width="120">
                </el-table-column>
                <el-table-column prop="stock" label="剩余数量" width="100">
                </el-table-column>
                <el-table-column prop="productor" label="生产厂商">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button @click="openEditUi(scope.row.id)" type="primary" size="mini">加入购物车</el-button>
                        <el-button @click="buyGood(scope.row.id)" type="danger" size="mini">购买</el-button>
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
            <el-form :model="cardForm" ref="goodFormRef">
                <el-form-item label="选择数量" prop="stock" :label-width="formLabelWidth">
                    <el-input v-model="cardForm.cnt" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="controlGood">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import goodApi from "@/api/goodManage"
import cartApi from "@/api/cartManage"
import userApi from "@/api/userv2"

export default {
    data() {
        return {
            buy: false,
            goodform: {},
            cardForm: {},
            formLabelWidth: '130px',
            dialogFormVisible: false,
            title: "",
            total: 0,
            goodsList: [],
            searchModel: {
                pageNo: 1,
                pageSize: 10,
            },

            rules: {
                stock: [
                    { required: true, message: '请输入需要的数量' }
                ]
            },
        }
    },

    methods: {
        controlGood() {
            this.cardForm.userid = localStorage.getItem('token');
            this.$refs["goodFormRef"].validate((valid) => {
                if (this.buy) {
                    if (valid) {
                        this.$confirm('将使用支付宝进行付款?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            userApi.buyGood(this.cardForm).then(response => {
                                this.dialogFormVisible=false;
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
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                } else {
                    if (valid) {
                        cartApi.addGood(this.cardForm).then(response => {
                            this.$message({
                                message: response.message,
                                type: 'success'
                            });
                            this.dialogFormVisible = false;
                            this.getGoodList(this.searchModel);
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                }
            });
        },

        buyGood(id) {
            this.title = '选择数量';
            this.cardForm.goodType = id;
            this.buy = true;
            this.dialogFormVisible = true;
        },

        clearForm() {
            this.cardForm = {};
            this.$refs.goodFormRef.clearValidate();
        },

        openEditUi(id) {
            this.title = '选择数量';
            this.cardForm.goodType = id;
            this.buy = false;
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
            goodApi.getGoodList(this.searchModel).then(response => {
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