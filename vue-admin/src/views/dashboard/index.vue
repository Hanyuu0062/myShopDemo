<template>
  <div class="dashboard-container">
    <el-card class="box-card">
      <h2>修改密码</h2>
      <el-form :model="bossForm" :rules="rules" ref="bossFormRef">
        <el-form-item label="原密码" prop="p" :label-width="formLabelWidth">
          <el-input v-model="bossForm.oldPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="p" :label-width="formLabelWidth">
          <el-input v-model="bossForm.newPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <el-button @click="updateBossPass" type="primary" round>确定</el-button>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import boosApi from '@/api/boss'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },

  data() {
    return {
      formLabelWidth: "100px",
      bossForm: {},
      rules: {
        p: [
          { required: false, message: '请输入密码', trigger: 'blur'},
          { min: 8, max: 64, message: '长度在 8 到 64 个字符', trigger: 'blur' }
        ],
      }
    }
  },

  methods: {
    updateBossPass() {
      this.$refs["bossFormRef"].validate((valid) => {
        if (valid) {
          this.bossForm.token=localStorage.getItem('token');
          boosApi.update(this.bossForm).then(response => {
            if (response.code == 1) {
              this.$message({
                message: response.message,
                type: 'success'
              });
              this.$refs.bossFormRef.clearValidate();
              this.bossForm = {};
            } else {
              this.$message({
                message: response.message,
                type: 'false'
              });
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  },
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.el-form .el-input {
  width: 30%;
}

.el-card .el-button {
  margin-left: 250px;
}
</style>
