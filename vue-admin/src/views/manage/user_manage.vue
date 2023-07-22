<template>
    <div>
        <!--搜索栏-->
        <el-card id="search">
            <el-row>
                <el-col :span="12">
                    <el-input v-model="searchModel.userid" placeholder="用户ID"></el-input>
                    <el-input v-model="searchModel.username" placeholder="用户名"></el-input>
                    <el-button @click="getUserList" type="primary">查询</el-button>
                </el-col>
                <el-col :span="12">

                </el-col>
            </el-row>
        </el-card>

        <!--用户信息展示-->
        <el-card>
            <el-table :data="usersList" stripe style="width: 100%">
                <el-table-column prop="id" label="用户ID" width="100">
                </el-table-column>
                <el-table-column prop="username" label="用户名" width="100">
                </el-table-column>
                <el-table-column prop="level" label="用户级别" width="120">
                </el-table-column>
                <el-table-column prop="cost" label="累计消费" width="120">
                </el-table-column>
                <el-table-column prop="createTime" label="注册时间" width="200">
                </el-table-column>
                <el-table-column prop="email" label="电子邮件" width="180">
                </el-table-column>
                <el-table-column prop="phoneNum" label="手机号" width="180">
                </el-table-column>
                <el-table-column prop="status" label="账号状态">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.status == 1" type="success">正常</el-tag>
                        <el-tag v-else type="danger">封号</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button @click="openEditUi(scope.row.id)" type="primary" size="mini">编辑</el-button>
                        <el-button @click="deleteUser(scope.row.id)" type="danger" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!--分页组件-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page.sync="searchModel.pageNo" :page-size="searchModel.pageSize" layout="total, prev, pager, next"
            :total="total">
        </el-pagination>

        <!--用户信息编辑对话框-->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="userForm" ref="usersFormRef">
                <el-form-item label="重置密码为" prop="password" :label-width="formLabelWidth">
                    <el-input v-model="userForm.password" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="设置用户状态" :label-width="formLabelWidth">
                    <el-switch style="display: block" v-model="userForm.status" active-color="#13ce66"
                        inactive-color="#ff4949" active-text="正常" inactive-text="封号" :active-value='1' :inactive-value='0'>
                    </el-switch>
                </el-form-item>

            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveUser">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import userApi from "@/api/userManage"
export default {
    data() {
        return {
            title: '',
            userForm: {},
            dialogFormVisible: false,
            formLabelWidth: "130px",
            total: 0,
            usersList: [],
            searchModel: {
                pageNo: 1,
                pageSize: 10,
            },
        }
    },

    methods: {
        saveUser() {
            userApi.updateUser(this.userForm).then(response => {
                this.$message({
                    message: response.message,
                    type: 'success'
                });
                this.dialogFormVisible = false;
                this.getUserList(this.searchModel);
            });
        },

        openEditUi(id) {
            this.title = '修改信息';
            userApi.getUserById(id).then(response => {
                this.userForm = response.data;
            });
            this.dialogFormVisible = true;
        },

        deleteUser(id) {
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                userApi.deleteUser(id);
                this.getUserList();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        clearForm() {
            this.userForm = {};
        },

        handleSizeChange(pageSize) {
            this.searchModel.pageSize = pageSize;
            this.getUserList();
        },

        handleCurrentChange(pageNo) {
            this.searchModel.pageNo = pageNo;
            this.getUserList();
        },

        getUserList() {
            userApi.getUserList(this.searchModel).then(response => {
                this.total = response.data.total;
                this.usersList = response.data.list;
            });
        }

    },

    created() {
        this.getUserList();
    },
}

</script>

<style>
#search .el-input {
    width: 200px;
    margin: 10px;
}

.el-switch {
    margin-top: 8px;
}
</style>