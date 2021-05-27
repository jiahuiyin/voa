<template>
  <div class="project">
    <el-card class="card-box">
      <div>
        <el-popover placement="right-start">
          <div style="width: 200px">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
              <el-form-item prop="name" style="margin-top: 15px">
                <el-input placeholder="请输入项目名称" v-model="ruleForm.name"></el-input>
              </el-form-item>
            </el-form>
            <div style="margin-top: 25px">
              <el-button class="button" @click="btnClick('ruleForm')">确定</el-button>
            </div>
          </div>
          <el-button slot="reference" type="success" icon="el-icon-plus">创建新项目</el-button>
        </el-popover>
        <el-button icon="el-icon-s-operation" style="float: right; margin-left: 0"></el-button>
        <el-button icon="el-icon-s-grid" style="float: right"></el-button>
      </div>
      <el-table
          :data="this.$store.state.projects"
          @row-click="rowClick"
          :cell-style="{'cursor': 'pointer'}"
          height="450"
          class="item">
        <el-table-column width="28px"><i class="el-icon-time"></i></el-table-column>
        <el-table-column prop="name" width="150px" show-overflow-tooltip></el-table-column>
        <el-table-column width="28px"><i class="el-icon-warning"></i></el-table-column>
        <el-table-column width="28px"><i class="el-icon-star-off"></i></el-table-column>
        <el-table-column align="right">
          <span style="color: #999;font-size: small">待处理任务</span>
          <span style="color: #545c64"> 7</span>
        </el-table-column>
      </el-table>
      <div class="footer">
        <el-button type="text" style="color: #44acb6">项目模板</el-button>
      </div>
    </el-card>
    <div class="foot"><span>团队ID:</span>{{ curTeam.id }}</div>
  </div>
</template>

<script>
import {request} from "../../network/request";

export default {
  name: "Project",
  props: ['Teams'],
  data() {
    let validateTeam = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入项目名称'));
      } else {
        callback();
      }
    };
    return {
      teams: '',
      idIndex: '',
      curTeam: {},
      ruleForm: {
        name: ''
      },
      rules: {
        name: [
          {validator: validateTeam, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    rowClick(row, column) {
      localStorage.curProject = JSON.stringify(row);
      this.$router.push('/home/project/' + row.id + '/list');
    },
    btnClick(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            url: '/team/' + this.curTeam.id + '/project',
            params: {
              projectName: this.ruleForm.name,
              teamId: this.curTeam.id
            },
            method: 'post'
          }).then(res => {
            // console.log('addProject:');
            // console.log(res);
            if (res.data.code === 200 && res.status === 200) {
              this.$message({
                message: '创建成功！',
                type: 'success'
              });
              this.$store.state.projects = res.data.data;
            }
            // this.$message.error('创建失败！');
          }).catch(err => {
            console.log('error addProject!!');
          })
        }
      });
    }
  },
  created() {
    this.teams = this.Teams;
    this.idIndex = localStorage.getItem('idIndex');
    this.curTeam = this.teams[this.idIndex];
    this.$store.dispatch('updateProjects', this.curTeam);
  }
}
</script>

<style scoped>
.project {
  width: 1000px;
  margin: 0 auto;
  position: relative;
}

.item {
  color: #000;
  font-size: 18px;
  width: 100%;
}

.card-box {
  margin: 0 25px;
  display: block;
  padding: 0 2px 20px;
  min-height: 600px;
  box-sizing: border-box;
  position: relative;
}

.footer {
  margin: 0 auto 20px;
  padding: 10px 10px 0;
  position: absolute;
  right: 0;
  bottom: 0;
  left: 0;
  width: 900px;
  font-size: 14px;
  box-sizing: border-box;
  background-color: #fff;
}

.foot {
  margin-top: 20px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: small;
  color: #999;
}

/deep/ .el-input__inner {
  /*height: 40px !important;*/
  font-size: medium;
  padding: 5px;
  width: 170px;
  margin: auto;
  display: block;
}

/deep/ .el-dialog__header {
  padding-top: 25px !important
}

/deep/ .el-dialog__body {
  padding-top: 15px !important;
}

.button {
  display: block;
  background-color: #44acb6;
  color: #fff;
  width: 170px;
  height: 40px;
  font-size: medium;
  margin: 10px auto 8px;
}

.button:hover {
  color: #fff;
  background-color: #2796a1;
}

.el-icon-warning {
  color: #b4b7bb;
}
.el-icon-warning:hover {
  color: #999;
}

.el-icon-star-off {
  color: #E64A19;
}

.el-icon-star-off:hover {
  color: #FF5722;
}
</style>
