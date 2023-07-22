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
                    <el-button @click='openAddUi' type="success" icon="el-icon-plus" circle></el-button>
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
                <el-table-column prop="stock" label="库存" width="100">
                </el-table-column>
                <el-table-column prop="productTime" label="生产时间" width="180">
                </el-table-column>
                <el-table-column prop="model" label="商品型号" width="200">
                </el-table-column>
                <el-table-column prop="price" label="售价" width="120">
                </el-table-column>
                <el-table-column prop="purchasePrice" label="采购价格" width="120">
                </el-table-column>
                <el-table-column prop="productor" label="生产厂商">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button @click="openEditUi(scope.row.id)" type="primary" size="mini">修改</el-button>
                        <el-button @click="deleteGood(scope.row.id)" type="danger" size="mini">删除</el-button>
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
            <el-form :model="goodform" :rules="rules" ref="goodFormRef">
                <el-form-item label="货物名称" prop="goodName" :label-width="formLabelWidth">
                    <el-input v-model="goodform.goodName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="生产厂家" prop="productor" :label-width="formLabelWidth">
                    <el-input v-model="goodform.productor" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="生产日期" prop="productTime" :label-width="formLabelWidth">
                    <el-date-picker v-model="goodform.productTime" type="datetime" placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="型号" prop="model" :label-width="formLabelWidth">
                    <el-input v-model="goodform.model" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="进货价" prop="purchasePrice" :label-width="formLabelWidth">
                    <el-input v-model="goodform.purchasePrice" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="售价" prop="price" :label-width="formLabelWidth">
                    <el-input v-model="goodform.price" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="数量" prop="stock" :label-width="formLabelWidth">
                    <el-input v-model="goodform.stock" autocomplete="off"></el-input>
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
export default {
    data() {
        return {
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

            rules: {
                goodName: [
                    { required: true, message: '请输入商品名称', trigger: 'blur' },
                    { min: 2, max: 64, message: '长度在 2 到 64 个字符', trigger: 'blur' }
                ],
                productor: [
                    { required: true, message: '请输入生产厂家', trigger: 'change' }
                ],
                productTime: [
                    { required: true, message: '请选择日期时间', trigger: 'change' }
                ],
                model: [
                    { required: true, message: '请输入型号', trigger: 'change' }
                ],
                purchasePrice: [
                    { required: true, message: '请输入购买价格', trigger: 'change' }
                ],
                price: [
                    { required: true, message: '请输入售价', trigger: 'change' }
                ],
                stock: [
                    { required: true, message: '请输入库存量', trigger: 'blur' }
                ]
            },
        }
    },

    methods: {
        saveGood() {
            this.$refs["goodFormRef"].validate((valid) => {
                if (valid) {
                    goodApi.saveGood(this.goodform).then(response => {
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
            });
        },

        deleteGood(id) {
            this.$confirm('此操作将永久删除该货物, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                goodApi.deleteGood(id);
                this.getGoodList();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        clearForm() {
            this.goodform = {};
            this.$refs.goodFormRef.clearValidate();
        },

        openEditUi(id) {
            this.title = '修改信息';
            goodApi.getGoodById(id).then(response => {
                this.goodform = response.data;
            });
            this.dialogFormVisible = true;

        },

        openAddUi() {
            this.title = '新增货物';
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