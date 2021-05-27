<template>
  <div class="command-bar">
    <el-button style="background-color: #f4f7ed; color: #999" icon="el-icon-search" round>搜索</el-button>
    <el-button type="success" icon="el-icon-plus" circle></el-button>
    <el-dropdown trigger="click">
      <el-button type="success" icon="el-icon-question" circle class="el-dropdown-link"></el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item icon="el-icon-plus">新手入门</el-dropdown-item>
        <el-dropdown-item icon="el-icon-circle-plus">视频中心</el-dropdown-item>
        <el-dropdown-item icon="el-icon-circle-plus-outline">帮助中心</el-dropdown-item>
        <el-dropdown-item icon="el-icon-check">新功能</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-button type="success" icon="el-icon-bell" circle></el-button>

    <el-dropdown trigger="click" @command="handleCommand">
      <el-button type="success" icon="el-icon-s-tools" circle class="el-dropdown-link"></el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
          <span style="font-weight: bold; font-size: medium">{{ user }} </span>
          <span>{{ curTeam.teamName }}</span>
        </el-dropdown-item>
        <el-dropdown-item icon="el-icon-plus" divided command="info">个人设置</el-dropdown-item>
        <el-dropdown-item icon="el-icon-circle-plus">团队设置</el-dropdown-item>
        <el-dropdown-item icon="el-icon-circle-plus-outline">应用中心</el-dropdown-item>
        <el-dropdown-item icon="el-icon-check" divided command="quit">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Right",
  props: ['Teams'],
  watch: {
    Teams(val) {
      this.teams = val;
      this.idIndex = localStorage.getItem('idIndex');
      this.curTeam = this.teams[this.idIndex];
    }
  },
  data() {
    return {
      teams: '',
      idIndex: '',
      curTeam: {},
      user: localStorage.getItem('name')
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'quit') {
        localStorage.clear();
        location.reload();
      } else if (command === 'info') {
        this.$router.push('/home/user');
      } else {

      }
    }
  }
}
</script>

<style scoped>
.command-bar {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  margin-right: 10px;
  display: flex;
  align-items: center;
}

.el-button {
  margin-right: 10px;
  margin-left: 0;
}
</style>