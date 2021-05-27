<template>
  <div class="team">
    <el-card class="card-box">
      <div>
        <el-button type="success" round>所有成员</el-button>
        <el-button style="background-color: #f6f6f6" icon="el-icon-user-solid" round>访客</el-button>
        <el-button type="text" style="color: #67C23A">分组管理</el-button>
      </div>
      <div class="head">
        <span class="name">{{ curTeam.teamName }}</span>
        <span style="color: #999">{{ '（共' + member.length + '人）' }}</span>

        <el-popover placement="left-start" @hide="hide">
          <div style="width: 300px">
            <div>
              <el-input placeholder="请输入手机号" v-model="phone" clearable style="width: 180px"></el-input>
              <el-button type="success" icon="el-icon-search" round style="margin-left: 10px" @click="search">搜索</el-button>

              <el-table ref="multipleTable" :data="addMem" class="item" :show-header="false" v-if="showMem"
                        tooltip-effect="dark" style="width: 100%" :cell-style="{padding: '0'}"
                        @selection-change="handleSelectionChange">
                <el-table-column width="66xp">
                  <template slot-scope="scope">
                    <el-avatar :src="'https://voa.yinjiahui.cn/api/files/avatar/'+scope.row.avatar"></el-avatar>
                  </template>
                </el-table-column>
                <el-table-column prop="username"></el-table-column>
                <el-table-column align="right">
                  <template slot-scope="scope">
                    <el-button type="text" @click="addMember" circle icon="el-icon-plus"></el-button>
                  </template>
                </el-table-column>
              </el-table>

            </div>
          </div>
          <el-button slot="reference" type="success" icon="el-icon-plus" style="float: right">邀请新成员</el-button>
        </el-popover>

      </div>
      <div>
        <el-table ref="multipleTable" :data="member" class="item" :show-header="false" height="400"
                  tooltip-effect="dark" style="width: 100%" :cell-style="{padding: '0'}"
                  @selection-change="handleSelectionChange">
          <el-table-column width="66xp">
            <template slot-scope="scope">
              <el-avatar :src="'https://voa.yinjiahui.cn/api/files/avatar/'+scope.row.avatar"></el-avatar>
            </template>
          </el-table-column>
          <el-table-column prop="username"></el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button type="success" round @click="btnClick(scope.$index, scope.row)">聊天</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Auto :targetUser="targetUser" :dialogVisible="dialogVisible" :chatHistory="chatHistory"
              @closeDialog="closeDialog" @send="send($event)"></Auto>
      </div>
    </el-card>
    <div class="foot"><span>团队ID:</span>{{ curTeam.id }}</div>
  </div>
</template>

<script>
import Auto from '../chat/Auto'
import {request} from "../../network/request";

export default {
  name: "Team",
  components: {
    Auto
  },
  props: ['Teams'],
  data() {
    return {
      teams: '',
      idIndex: '',
      curTeam: {},
      member: [],
      targetUser: {},
      dialogVisible: false,
      chatHistory: [],
      phone: '',
      addMem: [{}],
      showMem: false
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    btnClick(index, row) {
      request({
        url: '/message',
        params: {
          receiverId: row.id
        }
      }).then(res => {
        // console.log('chatHistory:');
        // console.log(row.id,res);
        if (res.data.code === 200 && res.status === 200) {
          this.chatHistory = res.data.data;
        }
      })
      this.targetUser = row;
      this.dialogVisible = true;
    },
    closeDialog() {
      this.dialogVisible = false;
      this.chatHistory = [];
    },
    send(val) {
      let n = this.chatHistory.length;
      this.chatHistory.splice(n, 0, val);
    },
    search() {
      this.addMem[0] = {};
      request({
        url: '/user/phone/' + this.phone,
      }).then(res => {
        // console.log('searchMember:');
        console.log(res);
        if (res.data.code === 200 && res.status === 200) {
          this.addMem[0] = res.data.data;
          this.showMem = true;
        } else if(res.data.code === 500 && res.status === 200) {
          this.$message.error('没有此用户！');
        }
      }).catch(err => {
        console.log('error phone!!');
      })
    },
    addMember() {
      request({
        url: '/team/' + this.curTeam.id + '/member',
        params: {
          teamId: this.curTeam.id,
          userId: this.addMem[0].id
        },
        method: 'post'
      }).then(res => {
        // console.log('addMember:');
        console.log(res);
        if (res.data.code === 200 && res.status === 200) {
          this.$message.success('邀请成功！')
        }
      }).catch(err => {
        console.log('error addMember!!');
      })
    },
    hide() {
      this.showMem = false;
    }
  },
  created() {
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
.team {
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

.name {
  font-size: 27px;
  font-weight: bold;
}

.head {
  margin: 30px auto 20px;
}

/deep/ .el-table td, /deep/ .el-table th {
  height: 80px !important;
}

.item {
  color: #000;
  font-size: larger;
  width: 100%;
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