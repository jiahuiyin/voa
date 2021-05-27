<template>
  <div class="box-card">
    <div>
      <div>
        <el-avatar class="avatar" icon="el-icon-user-solid"></el-avatar>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="user" style="margin-bottom: 22px">
          <el-input placeholder="请输入用户名" v-model="ruleForm.user" clearable prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="pwd" style="margin-bottom: 18px">
          <el-input placeholder="请输入密码" v-model="ruleForm.pwd" show-password prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 10px">
          <el-checkbox type="text" v-model="remember" class="remember">记住密码</el-checkbox>
          <div style="display: inline-block; float: right">
            <el-button type="text"><a href="/register" target="_blank">注册账号</a></el-button>
            <span style="color: #409EFF"> | </span>
            <el-button type="text">找回密码</el-button>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 300px" @click="btnClick('ruleForm')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {request} from '../../network/request'

export default {
  name: "Login",
  data() {
    let validateUser = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'));
      } else {
        callback();
      }
    };
    let validatePwd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      remember: false,
      ruleForm: {
        // user: '15003411932',
        // pwd: '123'
        user: '15213123123',
        pwd: '123'
        // user: '18179181127',
        // pwd: '999'
      },
      rules: {
        user: [
          {validator: validateUser, trigger: 'blur'}
        ],
        pwd: [
          {validator: validatePwd, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    // 登录
    btnClick(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            url: '/sso/login',
            params: {
              username: this.ruleForm.user,
              password: this.ruleForm.pwd
            },
            method: 'post'
          }).then(res => {
            // console.log('login:');
            // console.log(res);
            if (res.data.code === 200 && res.status === 200) {
              localStorage.navIndex = '1';
              localStorage.idIndex = 0;
              localStorage.userId = res.data.data.id;
              localStorage.name = res.data.data.name;
              localStorage.token = res.data.data.token;
              this.$store.dispatch('updateTeams').then(res => {
                if (!this.$store.state.teams.length) this.$router.push('/team');
                else this.$router.push('/home/team/' + this.$store.state.teams[0].id + '/project');
              }).catch(err => {

              })
            } else if (res.data.code === 404) {
              this.$message.error('用户名或密码错误！');
            }
          });
        }
      });
    },
    register() {
      this.$router.push('/register');
    }
  },
  created() {
    this.$store.dispatch('updateTeams').then(res => {
      localStorage.navIndex = '1';
      if (this.$store.state.teams.length) this.$router.push('/home/team/' + this.$store.state.teams[localStorage.idIndex].id + '/project');
    })
  }
}
</script>

<style scoped>
.box-card {
  width: 300px;
  padding-top: 100px;
  margin: 0 auto;
  background-color: #f4f7ed;
}

.avatar {
  color: #545c64;
  font-size: 100px;
  display: block;
  width: 100px;
  height: 100px;
  margin: 30px auto;
  background-color: #f4f7ed;
}

.remember {
  color: #409EFF;
}

.remember:hover, a:hover {
  opacity: 0.8;
}

.el-form-item {
  margin: 0;
}

a {
  text-decoration: none;
  color: #409EFF;
}
</style>
