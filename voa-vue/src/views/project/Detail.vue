<template>
  <div style="min-height: 93%; background-color: #fff">
    <div style="margin-left: 25px">
      <div style="padding-top: 20px">
        <span style="font-size: larger">{{ curProject.name }}</span>
        <el-button type="text" icon="el-icon-s-tools" style="float: right;padding-right: 25px">设置</el-button>
        <el-button style="float: right; margin-right: 20px">添加成员</el-button>
      </div>
      <el-tabs v-model="activeName" @tab-click="handleClick" style="background-color: #fff; margin-top: 20px">
        <el-tab-pane label="列表" name="1"><List :Teams="teams"></List></el-tab-pane>
        <el-tab-pane label="看板" name="2">看板</el-tab-pane>
        <el-tab-pane label="日历" name="3">日历</el-tab-pane>
        <el-tab-pane label="时间线" name="4">时间线</el-tab-pane>
        <el-tab-pane label="文件" name="5">文件</el-tab-pane>
        <el-tab-pane label="文档" name="6">文档</el-tab-pane>
        <el-tab-pane label="进展" name="7">进展</el-tab-pane>
        <el-tab-pane label="回收站" name="8">回收站</el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {request} from "../../network/request";
import List from "./List";

export default {
  name: "Detail",
  props: ['Teams'],
  components: {List},
  data() {
    return {
      teams: [],
      curProject: {},
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
    this.curProject = JSON.parse(localStorage.getItem('curProject'));
  }
}
</script>

<style scoped>
.el-button {
  color: #315f5d;
}
</style>