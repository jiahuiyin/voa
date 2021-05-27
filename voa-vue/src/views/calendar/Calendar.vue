<template>
  <div>
    <el-container>
      <el-main style="width: 100%">
        <el-calendar style="margin-left: 10px">
          <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
          <template
              slot="dateCell"
              slot-scope="{date, data}">
            <p :class="data.isSelected ? 'is-selected' : ''">
              {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : '' }}
            </p>
          </template>
        </el-calendar>
      </el-main>
      <el-aside style="background-color: #f6f6f6">
        <el-card style="height: 100%; background-color: #f6f6f6">
          <div slot="header">
            <div style="float: left">
              <span style="font-size: large">日历</span>
              <el-button class="addCalender" type="text" icon="el-icon-plus">添加日历</el-button>
            </div>
            <el-button icon="el-icon-more" type="text" class="other"></el-button>
          </div>
          <div style="margin-top: 40px; text-align: left; margin-left: 5px">
            <el-checkbox v-model="checkedF">团队日历</el-checkbox>
            <el-checkbox v-model="checkedS">法定假日</el-checkbox>
          </div>
        </el-card>
      </el-aside>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Calendar",
  data() {
    return {
      checkedF: true,
      checkedS: true
    }
  },
  methods: {

  }
}
</script>

<style scoped>
.is-selected {
  color: #1989FA;
}

/deep/ .el-container {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  text-align: center;
  padding: 0;
  margin: 0;
}

/deep/ .el-container {
  height: 100%;
}

/deep/ .el-main {
  padding: 0;

}

/deep/ .el-aside {
  padding: 0;
  margin-right: 10px;
  overflow: hidden;
}

/deep/ .el-calendar__header {
  height: 100px;
  font-size: x-large;
}

/deep/ .el-calendar__title {
  margin-left: 100px;
}

/deep/ .el-button {
  margin-top: 20px;
  margin-right: 5px;
  font-size: medium;
  height: 50px;
}

.addCalender {
  font-size: small;
  color: #999;
  margin-left: 10px;
}

.other {
  color: #999;
  float: right;
}

.addCalender:hover, .other:hover {
  color: #44acb6;
}

.el-checkbox {
  display: block;
  margin-top: 20px;
  width: 100%;
}
</style>