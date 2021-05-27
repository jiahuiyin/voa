<template>
  <div class="project">
    <el-card class="card-box">
      <div style="margin-left: 10px">
        <div style="margin: 10px">
          <span style="font-size: x-large">个人设置</span>
          <el-button type="text" style="color: #DF3C2F;font-size: small;position: absolute;right: 30px">退出团队</el-button>
        </div>
        <div style="position: relative;margin-top: 30px">
          <el-avatar :src="Info.avatar" :size="60"></el-avatar>
          <div style="display: inline-block;margin-left: 20px">
            <el-upload
                ref="upload"
                class="upload-demo"
                action="/api/user/avatar"
                :http-request="upAvatar"
                :headers="{Authorization: token}"
                :file-list="fileList"
                :show-file-list="false"
                accept=".jpg,.jpeg,.png">
              <el-button slot="trigger" type="text" style="color: #44acb6;position: absolute;top: 0">选择新头像</el-button>
              <div slot="tip" style="font-size: x-small;color: #777;position: absolute;bottom: 10px">你可以选择 png/jpg
                图片作为头像
              </div>
            </el-upload>
            <!--            <span style="font-size: x-small;color: #777;display: block;position: absolute;bottom: 10px">你可以选择 png/jpg 图片作为头像</span>-->
          </div>
        </div>
        <div style="margin-top: 30px;width: 45%">
          <el-form :model="ruleForm" ref="ruleForm" label-width="100px" :hide-required-asterisk=true>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="gender">
                <el-radio-button label="男"></el-radio-button>
                <el-radio-button label="女"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="生日" prop="birthday">
              <el-date-picker
                  v-model="ruleForm.birthday"
                  type="date"
                  placeholder="选择日期"
                  value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="btnClick">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-card>
    <div class="foot"><span>团队ID:</span>{{ curTeam.id }}</div>
  </div>
</template>

<script>
import {request} from "../../../network/request";
import axios from "axios";

export default {
  name: "Info",
  props: ['Teams'],
  data() {
    return {
      Info: {},
      fileList: [],
      token: localStorage.getItem('token'),
      ruleForm: {
        name: '',
        birthday: ''
      },
      gender: ''
    }
  },
  methods: {
    updateInfo() {
      request({
        url: '/user',
      }).then(res => {
        // console.log('Info:');
        // console.log(res);
        if (res.data.code === 200 && res.status === 200) {
          this.Info = res.data.data;
          this.Info.avatar = 'https://voa.yinjiahui.cn/api/files/avatar/' + this.Info.avatar;
          if (this.Info.gender === 1) this.gender = '男';
          else this.gender = '女';
          this.ruleForm.name = this.Info.username;
          this.ruleForm.birthday = this.Info.birthday;
          console.log(this.Info);
        }
      }).catch(err => {
        console.log('error Info!!');
      })
    },
    upAvatar(param) {
      let form = new FormData();
      form.append('file', param.file);
      let instance = axios.create({
        baseURL: '/api',
        headers: {
          "Content-Type": "multipart/form-data",
          "Authorization": localStorage.getItem('token')
        }
      });
      instance.put('/user/avatar', form).then(res => {
        // console.log(res);
        this.updateInfo();
      })
    },
    btnClick() {
      request({
        url: '/user',
        params: {
          username: this.ruleForm.name,
          gender: this.gender === '男' ? 1 : 0,
          birthday: this.ruleForm.birthday
        },
        method: 'put'
      }).then(res => {
        // console.log('modInfo:');
        console.log(res);
        if (res.data.code === 200 && res.status === 200) {

        }
      }).catch(err => {
        console.log('error modInfo!!');
      })
    }
  },
  created() {
    this.teams = this.Teams;
    this.idIndex = localStorage.getItem('idIndex');
    this.curTeam = this.teams[this.idIndex];
    this.$store.dispatch('updateProjects', this.curTeam);
    this.updateInfo();
  }
}
</script>

<style scoped>
.project {
  width: 1000px;
  margin: 0 auto;
  position: relative;
}

.card-box {
  margin: 0 25px;
  display: block;
  padding: 0 2px 20px;
  min-height: 600px;
  box-sizing: border-box;
  position: relative;
}

.foot {
  margin-top: 20px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: small;
  color: #999;
}
</style>