<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
      label-position="left">

      <div class="title-container">
        <h3 class="title">用户登录</h3>
      </div>

      <el-form-item class="el-form-item-1" prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input class="el-input-1" ref="username" v-model="loginForm.username" placeholder="账号" name="username"
          type="text" tabindex="1" auto-complete="on" />
      </el-form-item>

      <el-form-item class="el-form-item-1" prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input class='el-input-1' :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType"
          placeholder="密码" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>


      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
        @click.native.prevent="handleLogin">登录</el-button>

      <div class="tips">
        <span style="margin-right:20px;"><el-button type="text" @click="openEditUi">注册</el-button></span>
        <span style="margin-left: 330px;"><el-button type="text" @click="openForget">忘记密码</el-button></span>
      </div>
    </el-form>

    <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="signForm" :rules="rules" ref="goodFormRef">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="signForm.username" autocomplete="off" style="width: 70%;"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input v-model="signForm.password" autocomplete="off" style="width: 70%;"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="signForm.email" autocomplete="off" style="width: 70%;"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="signForm.phoneNum" autocomplete="off" style="width: 70%;"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="sign">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog @close="clearForm" :title="title" :visible.sync="vis">
      <el-form :model="signForm" :rules="rules" ref="goodFormRef">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="signForm.username" autocomplete="off" style="width: 70%;"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="signForm.email" autocomplete="off" style="width: 70%;"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="vis = false">取 消</el-button>
        <el-button type="primary" @click="resetUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import userApi from '@/api/userv2'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('请输入正确的账号'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 8) {
        callback(new Error('密码至少为8位'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'user1',
        password: '12345678'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      dialogFormVisible: false,
      title: '',
      formLabelWidth: '150px',
      signForm: {},
      vis:false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 5, max: 16, message: '长度在 5 到 16个字符', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, max: 16, message: '长度在 8 到 16个字符', trigger: 'blur' },
        ],
      },
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    openForget(){
      this.title = '忘记密码';
      this.vis = true;
    },

    resetUser() {
      this.$refs["goodFormRef"].validate((valid) => {
        if (valid) {
          userApi.reset(this.signForm).then(response => {
            if (response.code == 1) {
              this.vis = false;
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
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    clearForm() {
      this.signForm = {};
      this.$refs.goodFormRef.clearValidate();
    },

    sign() {
      this.$refs["goodFormRef"].validate((valid) => {
        if (valid) {
          userApi.sign(this.signForm).then(response => {
            if (response.code == 1) {
              this.$message({
                type: 'success',
                message: response.message
              });
              this.dialogFormVisible = false;
            } else {
              this.$message({
                type: 'false',
                message: response.message
              });
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    openEditUi() {
      this.dialogFormVisible = true;
      this.title = '注册';
    },

    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #ffffff8c;




/* reset element-ui css */

.el-input-1 {
  $cursor: #fff;
  display: inline-block;
  height: 47px;
  width: 85%;

  input {
    background: transparent;
    border: 0px;
    -webkit-appearance: none;
    border-radius: 0px;
    padding: 12px 5px 12px 15px;
    color: $light_gray;
    height: 47px;
    caret-color: $cursor;

    &:-webkit-autofill {
      box-shadow: 0 0 0px 1000px $bg inset !important;
      -webkit-text-fill-color: $cursor !important;
    }
  }
}

.el-form-item-1 {
  border: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(0, 0, 0, 0.7);
  border-radius: 5px;
  color: #ffffff;
}
</style>

<style lang="scss" scoped>
$bg: #4b2d2d;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  background-image: url('../../assets/login_image/bg1.png');
  background-size: 100%;
  overflow: hidden;

  display: flex;
  align-items: center;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 35px 35px 0;
    margin: 0 auto;
    overflow: hidden;
    background-color: rgb(34, 39, 40);
    border-radius: 8px;
    opacity: .95;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
