<template>
  <div>
    <div style="margin: 10px auto">
      <el-dropdown trigger="click">
        <el-button plain round size="small" icon="el-icon-sort" class="el-dropdown">按截止日期排序</el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>按创建日期排序</el-dropdown-item>
          <el-dropdown-item>按截止日期排序</el-dropdown-item>
          <el-dropdown-item>自定义排序</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dropdown trigger="click">
        <el-button plain round size="small" icon="el-icon-s-management" class="el-dropdown">按项目分组</el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>不分组</el-dropdown-item>
          <el-dropdown-item>按项目分组</el-dropdown-item>
          <el-dropdown-item>按优先级分组</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dropdown trigger="click">
        <!--          <span class="el-dropdown-link">-->
        <!--            <i class="el-icon-picture-outline-round"></i>所有项目-->
        <!--          </span>-->
        <el-button plain round size="small" icon="el-icon-picture-outline-round" class="el-dropdown">所有项目</el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>所有项目</el-dropdown-item>
          <el-dropdown-item v-for="item in this.$store.state.projects">{{ item.name }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <div style="display: inline-block; float: right">
        <el-button type="text" size="small">查看已完成的任务</el-button>
        <el-button type="text" size="small">查看创建的任务</el-button>
      </div>
    </div>
    <el-table ref="multipleTable" v-for="project in totalTasks" :data="project.tasks"
              tooltip-effect="dark" style="width: 100%" height="300px"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="title" width="200" :label="project.name" label-class-name="label"></el-table-column>
      <el-table-column prop="deadline" align="right"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import {request} from '../../network/request'

export default {
  name: "Task",
  props: ['Teams'],
  data() {
    return {
      teams: '',
      idIndex: '',
      curTeam: {},
      totalTasks: [],
      multipleSelection: ''
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(val);
    }
  },
  created() {
    this.teams = this.Teams;
    this.idIndex = localStorage.getItem('idIndex');
    this.curTeam = this.teams[this.idIndex];
    this.$store.dispatch('updateProjects', this.curTeam);
    this.$store.dispatch('updateTotalTasks', this.curTeam).then(res => {
      this.totalTasks = this.$store.state.totalTasks;
    })
  }
}
</script>

<style scoped>
/deep/ .label {
  font-size: medium;
  font-weight: bold;
  color: #000;
}

.el-button {
  color: #44acb6;
  margin-left: 20px;
}
</style>