<template>
  <div>
    <div style="padding: 100px">
      <el-card class="box-card">
        <el-form :model="ruleFormR" :rules="rulesR" ref="ruleFormR" style="margin: 10px 20px 0">
          <el-form-item>
            <span style="font-size: xxx-large">欢迎注册</span>
          </el-form-item>
          <el-form-item prop="username">
            <el-input placeholder="姓名" v-model="ruleFormR.username" prefix-icon="el-icon-user"></el-input>
            <!--          <el-radio-group v-model="gender">-->
            <!--            <el-radio :label="1">男</el-radio>-->
            <!--            <el-radio :label="0">女</el-radio>-->
            <!--          </el-radio-group>-->
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" placeholder="密码" v-model="ruleFormR.password" autocomplete="off"
                      prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <el-form-item prop="checkPass">
            <el-input type="password" placeholder="确认密码" v-model="ruleFormR.checkPass" autocomplete="off"
                      prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input placeholder="手机号" v-model="ruleFormR.phone" prefix-icon="el-icon-mobile-phone"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <el-input placeholder="短信验证码" v-model="ruleFormR.code" style="width: 35%"
                      prefix-icon="el-icon-message"></el-input>
            <el-button type="primary" @click="getCode('ruleFormR')" style="margin-left: 5%;width: 60%" v-if="!isSend">发送短信验证码</el-button>
            <el-button type="primary" style="margin-left: 5%; width: 60%; opacity: 0.7" v-if="isSend">重新发送（{{ time }}s）</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="register('ruleFormR')" style="width: 100%">立即注册</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
    <div align="center" style="padding: 300px" v-show="!show">
      <span style="font-size: xxx-large; color: #409EFF">注册成功！</span>
    </div>
  </div>
</template>

<script>
import {request} from "../../network/request";

export default {
  name: "Register",
  data() {
    let validateCheckPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleFormR.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback();
      }
    };
    return {
      gender: 1,
      isSend: false,
      time: 60,
      ruleFormR: {
        phone: '',
        password: '',
        checkPass: '',
        username: '',
        code: ''
      },
      rulesR: {
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        checkPass: [
          {validator: validateCheckPass, trigger: 'blur'}
        ],
        username: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    register(formNameR) {
      this.$refs[formNameR].validate((valid) => {
        if (valid) {
          request({
            url: '/sso/register',
            params: {
              username: this.ruleFormR.username,
              password: this.ruleFormR.password,
              phone: this.ruleFormR.phone,
              code: this.ruleFormR.code,
              gender: this.gender
            },
            method: 'post'
          }).then(res => {
            console.log('register:');
            console.log(res);
            if (res.data.code === 200 && res.status === 200) {
              this.$message({
                message: '注册成功',
                type: 'success'
              });
            }
          });
        }
      })
    },
    getCode() {
      if (this.ruleFormR.phone) {
        request({
          url: '/sso/code',
          params: {
            phone: this.ruleFormR.phone,
          },
          method: 'post'
        }).then(res => {
          console.log('getCode:');
          console.log(res);
          if (res.data.code === 200 && res.status === 200) {
            this.time = 60;
            this.isSend = true;
            let t = setInterval(() => {
              if(this.time <= 0) {
                clearInterval(t);
                this.isSend = false;
              } else {
                this.time --;
              }
            }, 1000)
          }
        });
      }
    }
  }
}
</script>

<style scoped>
.box-card {
  width: 600px;
  margin: auto;
}

/deep/ .el-input__inner, /deep/ .el-button {
  height: 50px;
}
</style>