<template>
  <div class="profile">
    <el-card class="card-box">
      <div style="height: 120px">
        <el-avatar class="avatar" icon="el-icon-platform-eleme"></el-avatar>
        <span class="text">{{ user }}</span>
        <el-button type="text" icon="el-icon-edit" class="sign">填写工作签名</el-button>
      </div>
      <div>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="任务" name="1"><Task :Teams="teams"></Task></el-tab-pane>
          <el-tab-pane label="收藏" name="2">收藏</el-tab-pane>
          <el-tab-pane label="日程" name="3">日程</el-tab-pane>
          <el-tab-pane label="汇报" name="4">汇报</el-tab-pane>
        </el-tabs>
      </div>
    </el-card>
    <div class="foot"><span>团队ID:</span>{{ curTeam.id }}</div>
  </div>
</template>

<script>
import Task from "./Task";

export default {
  name: "Profile",
  components: {Task},
  props: ['Teams'],
  data() {
    return {
      teams: '',
      idIndex: '',
      curTeam: {},
      user: localStorage.getItem('name'),
      activeName: '1'
    }
  },
  methods: {
    handleClick(tab, event) {
      // console.log(tab, event);
    }
  },
  created() {
    this.teams = this.Teams;
    this.idIndex = localStorage.getItem('idIndex');
    this.curTeam = this.teams[this.idIndex];
  }
}
</script>

<style scoped>
.profile {
  width: 1000px;
  margin: 0 auto;
}

.card-box {
  margin: 0 25px;
  display: block;
  padding: 0 2px 20px;
  min-height: 600px;
  box-sizing: border-box;
  position: relative;
}

.avatar {
  color: #545c64;
  font-size: 100px;
  display: block;
  width: 100px;
  height: 100px;
  margin: 10px;
  background-color: #fff;
  float: left;
}

.text {
  font-size: medium;
  font-weight: bold;
  position: absolute;
  top: 50px;
  margin-left: 15px;
}

.sign {
  font-size: small;
  color: #999;
  position: absolute;
  top: 80px;
  margin-left: 12px;
}

/deep/.el-tabs__item {
  font-size: 17px !important;
}

.el-tab-pane {
  font-size: 17px;
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