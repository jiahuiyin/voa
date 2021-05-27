<template>
  <div style="height: 100%">
    <div class="header">
        <Left :Teams="teams"></Left>
        <div class="nav">
          <el-menu
              :default-active="activeIndex"
              mode="horizontal"
              @select="handleSelect"
              background-color="#f4f7ed"
              text-color="#999"
              active-text-color="#000">
            <el-menu-item index="1"><span>项目</span></el-menu-item>
            <el-menu-item index="2"><span>进展</span></el-menu-item>
            <el-menu-item index="3"><span>知识库</span></el-menu-item>
            <el-menu-item index="4"><span>日历</span></el-menu-item>
            <el-menu-item index="5"><span>汇报</span></el-menu-item>
            <el-menu-item index="6"><span>动态</span></el-menu-item>
            <el-menu-item index="7"><span>团队</span></el-menu-item>
            <el-menu-item index="8"><span>我自己</span></el-menu-item>
          </el-menu>
        </div>
        <Right :Teams="teams"></Right>
    </div>
    <router-view :Teams="teams" v-if="teams.length"></router-view>
  </div>
</template>

<script>
import Left from "./Left";
import Right from "./Right";

export default {
  name: "Menu",
  components: {
    Right,
    Left
  },
  data() {
    return {
      teams: [],
      curTeam: {},
      activeIndex: '1',
      path: ['/project', '/progress', '/knowledge', '/calendar',
        '/report', '/dynamic', '/team', '/myproject']
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key);
      this.activeIndex = key;
      localStorage.navIndex = key;
      const path = '/home/team/' + this.curTeam.id + this.path[key - 1];
      if (this.$route.path !== path) {
        this.$router.push(path);
      }
    }
  },
  created() {
    this.$store.dispatch('updateTeams').then(res => {
      this.teams = this.$store.state.teams;
      this.idIndex = localStorage.getItem('idIndex');
      this.curTeam = this.teams[this.idIndex];
      this.activeIndex = localStorage.getItem('navIndex');
    })
  }
}
</script>

<style scoped>
.header {
  width: 100%;
  min-width: 1200px;
  margin: 0 auto;
  padding: 0px 0px;
  box-sizing: border-box;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  height: 7%;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f4f7ed;
}

.nav {
  display: flex;
  align-items: center;
  background-color: #545c64;
}

span {
  font-size: medium;
}

.el-menu-item {
  height: 100%;
}
</style>