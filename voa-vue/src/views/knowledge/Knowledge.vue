<template>
  <div style="height: 93%">
    <div class="nav">
      <el-menu default-active="0" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" @select="handleSelect"
               background-color="#f6f6f6" text-color="#000" active-text-color="#ffd04b">
        <el-menu-item index="0">
          <i class="el-icon-office-building"></i>
          <span slot="title">团队知识库</span>
        </el-menu-item>
        <el-menu-item index="1">
          <i class="el-icon-s-custom"></i>
          <span slot="title">个人知识库</span>
        </el-menu-item>
        <el-menu-item index="n">
          <i class="el-icon-plus"></i>
          <span slot="title">创建知识库</span>
        </el-menu-item>
      </el-menu>
    </div>
    <kform :curNav="curNav" :Teams="teams"></kform>
  </div>
</template>

<script>
import Kform from "./Kform";

export default {
  name: "Knowledge",
  components: {Kform},
  props: ['Teams'],
  data() {
    return {
      teams: '',
      idIndex: '',
      curTeam: {},
      curNav: '团队知识库'
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      if(key == 0)this.curNav = '团队知识库';
      else if(key == 1)this.curNav = '个人知识库';
      else {

      }
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
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
.nav {
  float: left;
  width: 15%;
  height: 100%;
  background-color: #f6f6f6;
}
</style>