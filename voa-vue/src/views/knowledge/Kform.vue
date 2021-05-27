<template>
  <div class="form">
    <span class="title">{{ curNav }}</span>
    <div style="float: right; margin-right: 30px">
      <el-button type="text" icon="el-icon-edit-outline" class="top"></el-button>
      <el-button type="text" icon="el-icon-more" class="top"></el-button>
    </div>
    <el-divider></el-divider>
    <span class="title" style="font-size: medium">团队知识库的内容对团队内所有成员公开，你可以将它用于和团队成员共享与同步知识。</span>
    <el-table
        :data="form"
        class="item">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="fileName"></el-table-column>
      <el-table-column prop="fileSize"></el-table-column>
      <el-table-column align="right">
        <template slot-scope="scope">
          <el-dropdown trigger="click" @command="handleCommand">
            <el-button icon="el-icon-more" type="text" class="el-dropdown-link"></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-plus">
                <a :href="'https://voa.yinjiahui.cn/api/files/'+scope.row.trueName" target="_blank">下载</a>
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-circle-plus">移动</el-dropdown-item>
              <el-dropdown-item icon="el-icon-circle-plus-outline" :command="scope.row.id">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

          <!--        <a :href="'http://39.101.201.242:8080/files/'+scope.row.trueName" target="_blank">下载</a>-->
        </template>
      </el-table-column>
    </el-table>
    <div class="up">
      <el-upload
          class="upload-demo"
          action="/api/file"
          :headers="{Authorization: token}"
          :data="{teamId: curTeam.id}"
          :on-change="handleChange"
          :file-list="fileList"
          align="center">
        <el-button slot="trigger" type="primary" icon="el-icon-upload2">上传文件</el-button>
      </el-upload>
    </div>
  </div>
</template>

<script>
import {request} from "../../network/request";

export default {
  name: "Kform",
  props: ['curNav', 'Teams'],
  data() {
    return {
      teams: '',
      idIndex: '',
      curTeam: {},
      form: [],
      fileList: [],
      token: localStorage.getItem('token')
    }
  },
  methods: {
    handleChange(file, fileList) {
      // console.log(file);
      request({
        url: '/file',
        params: {
          teamId: this.curTeam.id
        }
      }).then(res => {
        // console.log('file:');
        // console.log(res);
        if (res.data.code === 200 && res.status === 200) {
          this.form = res.data.data;
        }
      })
    },
    handleCommand(command) {
      console.log(command);
      if (typeof command !== "undefined") {
        request({
          url: '/file',
          params: {
            fileId: command
          },
          method: 'delete'
        }).then(res => {
          console.log('deleteFile:');
          console.log(res);
          if (res.data.code === 200 && res.status === 200) {
            //删除后更新
            request({
              url: '/file',
              params: {
                teamId: this.curTeam.id
              }
            }).then(res => {
              // console.log('file:');
              // console.log(res);
              if (res.data.code === 200 && res.status === 200) {
                this.form = res.data.data;
              }
            })
          }
        }).catch(err => {
          console.log('error delete!!');
        })
      }
    }
  },
  created() {
    this.teams = this.Teams;
    this.idIndex = localStorage.getItem('idIndex');
    this.curTeam = this.teams[this.idIndex];
    request({
      url: '/file',
      params: {
        teamId: this.curTeam.id
      }
    }).then(res => {
      // console.log('file:');
      // console.log(res);
      if (res.data.code === 200 && res.status === 200) {
        this.form = res.data.data;
      }
    })
  }
}
</script>

<style scoped>
.item {
  color: #000;
  font-size: large;
  width: 100%;
}

.form {
  float: right;
  width: 85%;
  height: 100%;
  background-color: #fff;
}

.el-table {
  width: 85%;
}

.title {
  display: inline-block;
  font-size: larger;
  margin-top: 20px;
  margin-left: 20px;
}

/deep/ .top {
  font-size: x-large;
  color: #999;
}

.up {
  position: absolute;
  top: 150px;
  right: 20px;
  width: 200px;
}

/deep/ .el-table {
  font-size: medium;
}

a {
  color: #606266;
  text-decoration: none;
}

.el-dropdown-link {
  color: #999;
}
</style>