<template>
  <div class="header-left">
    <el-dropdown trigger="click" @command="handleCommand">
          <span class="el-dropdown-link">{{ curTeam.teamName }}<i
              class="el-icon-arrow-down el-icon--right"></i></span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-for="(team,index) in teams" :command="index" :class="{'current-team':index==idIndex}">
          {{ team.teamName }}
        </el-dropdown-item>
        <el-dropdown-item icon="el-icon-plus" command="n">创建新团队</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-button type="text" icon="el-icon-s-opportunity" style="color: #f17852; margin-left: 5px">免费试用付费版</el-button>
  </div>
</template>

<script>
export default {
  name: "Left",
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
      curTeam: {}
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'n') this.$router.push('/team');
      else {
        localStorage.idIndex = command;
        location.reload();
      }
    }
  }
}
</script>

<style scoped>
.header-left {
  display: flex;
  align-items: center;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  margin: auto;
}

.el-dropdown-link {
  font-size: medium;
  font-weight: bold;
  cursor: pointer;
  color: #000;
  margin-left: 20px;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.current-team {
  display: none;
}
</style>