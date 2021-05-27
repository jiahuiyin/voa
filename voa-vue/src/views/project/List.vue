<template>
  <div>
    <div style="margin-bottom: 15px">
      <el-dropdown split-button type="success" @click="handleClick">添加任务
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>添加清单</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <div style="display: inline-block; float: right; margin-right: 25px">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <i class="el-icon-finished"></i>下拉菜单
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>未完成任务</el-dropdown-item>
            <el-dropdown-item>已完成任务</el-dropdown-item>
            <el-dropdown-item>所有状态</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-button slot="reference" type="text" icon="el-icon-picture-outline-round" class="btn">筛选</el-button>

        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <i class="el-icon-sort"></i>排序
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>手动排序</el-dropdown-item>
            <el-dropdown-item>自定义排序</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <i class="el-icon-connection"></i>按清单分组
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>按清单分组</el-dropdown-item>
            <el-dropdown-item>按负责人分组</el-dropdown-item>
            <el-dropdown-item>按创建人分组</el-dropdown-item>
            <el-dropdown-item>按优先级分组</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-button type="text" icon="el-icon-s-management" class="btn">任务字段</el-button>

        <el-dropdown>
          <el-button plain size="small">
            默认<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>默认</el-dropdown-item>
            <el-dropdown-item>我负责的</el-dropdown-item>
            <el-dropdown-item>本周的</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <i class="el-icon-more"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>批量导入任务</el-dropdown-item>
            <el-dropdown-item>查看已完成任务</el-dropdown-item>
            <el-dropdown-item>查看已归档清单</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <el-table :data="curTasks" border style="width: 100%" ref="multipleTable"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="title" label="任务" width="180"></el-table-column>
      <el-table-column prop="headName" label="负责人" width="180"></el-table-column>
      <el-table-column prop="deadline" label="截止时间"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="pri" label="优先级"></el-table-column>
      <el-table-column prop="finish" label="完成"></el-table-column>
    </el-table>

    <el-dialog
        title="添加任务"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="headId">
          <el-select v-model="ruleForm.headId" placeholder="请选择负责人">
            <el-option v-for="item in member" :label="item.username" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="截止日期" required>
          <el-form-item prop="deadline">
            <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.deadline"
                            style="width: 100%;" value-format="yyyy-MM-ddTHH:mm:ss.000Z">
            </el-date-picker>
          </el-form-item>
        </el-form-item>
        <el-form-item label="优先级" prop="priority">
          <el-select v-model="ruleForm.priority" placeholder="请选择优先级">
            <el-option v-for="item in 4" :label="pri[item-1]" :value="item-1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addTask('ruleForm')">确 定</el-button>
          <el-button @click="centerDialogVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {request} from "../../network/request";

export default {
  name: "List",
  props: ['Teams'],
  data() {
    return {
      curProject: {},
      curTasks: [],
      teams: '',
      idIndex: '',
      curTeam: {},
      member: '',
      multipleSelection: [],
      centerDialogVisible: false,
      pri: ['较低', '普通', '较高', '最高'],
      ruleForm: {
        headId: '',
        title: '',
        deadline: '',
        description: '',
        priority: '',
        projectId: ''
      },
      rules: {
        headId: [
          {required: true, message: '请选择负责人', trigger: 'change'},
        ],
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'}
        ],
        deadline: [
          {required: true, message: '请选择截止日期', trigger: 'change'}
        ],
        description: [
          {required: true, message: '请输入描述', trigger: 'blur'}
        ],
        priority: [
          {required: true, message: '请选择优先级', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    updateTasks() {
      request({
        url: '/project/' + this.curProject.id + '/task',
        params: {
          projectId: this.curProject.id
        }
      }).then(res => {
        // console.log('updateProject:');
        console.log(res);
        if (res.data.code === 200 && res.status === 200) {
          this.curTasks = res.data.data;
          // this.toggleSelection(this.curTasks);
          for (let i of this.curTasks) {
            i['pri'] = this.pri[i.priority];
          }
        }
      }).catch(err => {
        console.log('error project!!');
      })
    },
    handleClick() {
      this.centerDialogVisible = true
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val)
    },
    addTask(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.centerDialogVisible = false;
          request({
            url: '/project/' + this.curProject.id + '/task',
            params: this.ruleForm,
            method: 'post'
          }).then(res => {
            console.log('addTask:');
            console.log(res);
            if (res.status === 200) {
              this.$message({
                message: '创建成功！',
                type: 'success'
              });
              this.curTasks = res.data.data;
              // this.toggleSelection(this.curTasks);
            }
            // this.$message.error('创建失败！');
          }).catch(err => {
            console.log('error addTask!!');
          })
        }
      });
    },
    toggleSelection(val) {
      val.forEach(task => {
        if (!task.finish) this.$refs.multipleTable.toggleRowSelection(task, true);
      });
    }
  },
  created() {
    this.curProject = JSON.parse(localStorage.getItem('curProject'));
    this.ruleForm.projectId = this.curProject.id;
    this.updateTasks();
    this.teams = this.Teams;
    this.idIndex = localStorage.getItem('idIndex');
    this.curTeam = this.teams[this.idIndex];
    this.$store.dispatch('updateMember', this.curTeam).then(res => {
      this.member = this.$store.state.member;
    })
  }
}
</script>

<style scoped>
/deep/ .el-dialog__body {
  padding-right: 40px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #999;
  margin-left: 20px;
}

.el-dropdown > .el-button, .btn {
  color: #999;
  margin-left: 20px;
}
</style>