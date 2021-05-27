<template>
  <div style="padding: 200px">
    <el-card>
      <div style="margin-bottom: 30px">
        <span class="head">下午好，{{ user }}</span><br/>
        <span class="head">介绍一下您的团队吧：</span>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="name">
          <el-input placeholder="请输入团队名称" v-model="ruleForm.name" clearable></el-input>
        </el-form-item>
        <div style="margin-bottom: 20px">
          <span style="font-size: larger; color: #999">请选择团队中要协作的部门：</span>
        </div>
        <el-checkbox-group v-model="checkList" style="height: 100px">
          <div class="check">
            <el-checkbox label="客服"></el-checkbox>
            <el-checkbox label="人事"></el-checkbox>
            <el-checkbox label="研发"></el-checkbox>
          </div>
          <div class="check">
            <el-checkbox label="市场"></el-checkbox>
            <el-checkbox label="产品"></el-checkbox>
            <el-checkbox label="运营"></el-checkbox>
          </div>
          <div class="check">
            <el-checkbox label="其它"></el-checkbox>
          </div>
        </el-checkbox-group>
        <el-button class="button" @click="btnClick('ruleForm')" style="width: 100%">确定</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {request} from "../../network/request";

export default {
  name: "JoinTeam",
  data() {
    let validateTeam = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入团队名称'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        name: ''
      },
      rules: {
        name: [
          {validator: validateTeam, trigger: 'blur'}
        ]
      },
      checkList: [],
      user: localStorage.getItem('name')
    }
  },
  methods: {
    btnClick(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            url: '/team',
            params: {
              teamName: this.ruleForm.name
            },
            method: 'post'
          }).then(res => {
            console.log('addTeam:');
            console.log(res);
            if (res.data.code === 200 && res.status === 200) {
              this.$message({
                message: '创建成功！',
                type: 'success'
              });
              let n = res.data.data.length;
              localStorage.idIndex = Number(n-1);
              this.$router.push('/home/team/' + res.data.data[n-1].id + '/project');
            }
          });
        }
      });
    }
  }
}
</script>

<style scoped>
/deep/ .el-card {
  width: 420px;
  margin: auto;
  padding: 15px;
}

/deep/ .el-input__inner {
  height: 70px !important;
  font-size: larger;
}

/deep/ .el-checkbox__label {
  font-size: larger;
  width: 60px;
}

.head {
  font-size: x-large;
  color: #000;
  display: block;
}

.check {
  height: 30px;
}

.button {
  display: block;
  width: 320px;
  background-color: #44acb6;
  color: #fff;
  height: 50px;
  font-size: larger;
  margin: 10px auto 0;
}

.button:hover {
  color: #fff;
  background-color: #2796a1;
}
</style>