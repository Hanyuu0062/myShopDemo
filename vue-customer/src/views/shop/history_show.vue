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
                <el-table-column prop="history.id" label="订单号" width="180">
                </el-table-column>
                <el-table-column prop="good.id" label="商品ID" width="180">
                </el-table-column>
                <el-table-column prop="good.goodName" label="商品名" width="180">
                </el-table-column>
                <el-table-column prop="history.cnt" label="购买数量" width="180">
                </el-table-column>
                <el-table-column prop="history.buytime" label="购买时间" width="250">
                </el-table-column>
                <el-table-column prop="good.model" label="商品型号" width="200">
                </el-table-column>
                <el-table-column prop="cost" label="花费" width="180">
                </el-table-column>
                <el-table-column prop="good.productor" label="生产厂商">
                </el-table-column>
            </el-table>
        </el-card>

        <!--分页组件-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="searchModel.pageNo" :page-sizes="[5, 10]" :page-size="searchModel.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>

    </div>
</template>

<script>
import historyApi from "@/api/history"
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
            historyApi.getGoodList(this.searchModel).then(response => {
                this.total = response.data.total;
                this.goodsList = response.data.list;
                this.goodsList.forEach(item => {
                    const cnt = item.history.cnt;
                    const price = item.good.price;
                    item.cost = (cnt * price).toFixed(2);
                });
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