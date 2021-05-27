<template>
  <div>
    <el-dialog
        title="聊天"
        center
        :visible.sync="isShow"
        width="50%"
        @opened="handleOpened"
        @close="handleClose">
      <div style="height: 350px; overflow: auto" id="win">
        <div v-for="item in cHistory"
             :class="{receiver: item.receiver==targetUser.id, sender: item.receiver!=targetUser.id}">
          <div>
            <el-avatar style="background-color: #fff; color: #000" class="ava">{{ item.sender }}</el-avatar>
          </div>
          <div>
            <div class="bg">
              <div
                  :class="{right_triangle: item.receiver==targetUser.id, left_triangle: item.receiver!=targetUser.id}"></div>
              <span>{{ item.content }}</span>
            </div>
          </div>
        </div>
      </div>
      <el-divider class="divid"></el-divider>
      <el-button icon="el-icon-eleme" style="border-color: #fff"></el-button>
      <el-button icon="el-icon-picture-outline" style="border-color: #fff"></el-button>
      <el-button icon="el-icon-folder-opened" style="border-color: #fff" slot="reference"></el-button>
      <el-input type="textarea" v-model="input" @keydown.enter.native="btnClick"></el-input>
      <div align="right" style="margin-top: 20px">
        <el-button @click="btnClick">发送</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {onConnect, onbindAccount} from './websocket/cim.web.sdk.js';
import {request} from "../../network/request";

export default {
  name: "Auto",
  props: ['targetUser', 'dialogVisible', 'chatHistory'],
  watch: {
    dialogVisible(val) {
      this.isShow = val;
    },
    chatHistory(val) {
      // console.log(val);
      this.cHistory = val;
    }
  },
  data() { // 数据
    return {
      isShow: false,
      input: '',
      cHistory: [],
      fileList: []
    }
  },
  created() { // 挂载
    this.getlist()
    window.onMessageReceived = this.onMessageReceived
    window.onReplyReceived = this.onReplyReceived
    window.onConnectFinished = this.onConnectFinished
  },
  methods: { // 方法
    btnClick(index, row) {
      if (!this.input) return;
      // console.log(this.chatHistory)
      request({
        url: '/message',
        params: {
          receiver: this.targetUser.id,
          sender: localStorage.getItem('userId'),
          content: this.input,
          action: '1',
        },
        method: 'post'
      }).then(res => {
        // console.log('chat:');
        // console.log(res);
        if (res.data.code === 200 && res.status === 200) {
          this.$emit('send', {
            content: this.input,
            sender: localStorage.getItem('userId'),
            receiver: this.targetUser.id
          })
          this.input = '';
          this.$nextTick(() => {
            let msg = document.getElementById('win') // 获取对象
            msg.scrollTop = msg.scrollHeight // 滚动高度
            console.log(msg.scrollHeight);
          })
        }
      })
    },
    handleOpened() {
      this.$nextTick(() => {
        let msg = document.getElementById('win') // 获取对象
        msg.scrollTop = msg.scrollHeight // 滚动高度
        console.log(msg.scrollHeight, msg.scrollTop);
      })
    },
    handleClose(done) {
      this.$emit('closeDialog');
    },
    getlist() {
      console.log('Console')
      onConnect() // 初始化
    },
    onConnectFinished() { // 登陆
      onbindAccount(localStorage.getItem('userId'))
    },
    onReplyReceived(reply) { // 上线响应
      console.log(reply)
      if (reply.key === 'client_bind' && reply.code === 200) {
        console.log('上线响应')
      }
    },
    onMessageReceived(message) { // 收消息
      console.log(message)
      console.log(message.sender + ': ' + message.content)
      let n = this.cHistory.length;
      this.cHistory.splice(n, 0, {
        content: message.content,
        sender: this.targetUser.id,
        receiver: localStorage.getItem('userId')
      });
      this.$nextTick(() => {
        let msg = document.getElementById('win') // 获取对象
        msg.scrollTop = msg.scrollHeight // 滚动高度
        console.log(msg.scrollHeight, msg.scrollTop);
      })
      if (message.action === 'ACTION_999') { // 账号在其他设备登陆
        return false
      }
    }
  },
  computed: { // 计算机属性

  }
}
</script>

<style scoped>
/deep/ .el-dialog__body {
  padding-bottom: 15px;
}

.sender {
  overflow: hidden;
  position: relative;
  margin-bottom: 20px;
}

.sender > div:nth-of-type(1) {
  position: absolute;
}

.sender > div:nth-of-type(2) {
  min-width: 100px;
  padding: 0 35px;
  line-height: 23px;
}

.sender div.bg {
  margin: 0 20px 10px 15px;
  border-radius: 7px;
  padding: 10px;
  display: inline-block;
  position: relative;
  background-color: aquamarine;
}

.receiver div:first-child img,
.sender div:first-child img {
  width: 50px;
  height: 50px;
}

.receiver {
  overflow: hidden;
  position: relative;
  margin-bottom: 20px;
}

.receiver > div:nth-child(1) {
  position: absolute;
  right: 0;
}

.receiver > div:nth-of-type(2) {
  min-width: 100px;
  padding: 0 35px;
  line-height: 23px;
}

.receiver div .bg {
  margin: 0 20px 10px 15px;
  border-radius: 7px;
  padding: 10px;
  display: inline-block;
  position: relative;
  background-color: gold;
  float: right;
}

.left_triangle {
  height: 0px;
  width: 0px;
  border-width: 8px;
  border-style: solid;
  border-color: transparent aquamarine transparent transparent;
  position: absolute;
  left: -15px;
  top: 15px;
}

.right_triangle {
  height: 0px;
  width: 0px;
  border-width: 8px;
  border-style: solid;
  border-color: transparent transparent transparent gold;
  position: absolute;
  right: -15px;
  top: 15px;
}

/deep/ .divid {
  margin-bottom: 0;
}

/deep/ .el-textarea__inner {
  border-color: #fff
}

/deep/ .avatar {
  border-color: #FF5722 !important
}
</style>
