(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0dc12e68"],{"1bfe":function(t,e,r){},"1f73":function(t,e,r){"use strict";r("1bfe")},b0c0:function(t,e,r){var a=r("83ab"),o=r("9bf2").f,n=Function.prototype,s=n.toString,l=/^\s*function ([^ (]*)/,i="name";a&&!(i in n)&&o(n,i,{configurable:!0,get:function(){try{return s.call(this).match(l)[1]}catch(t){return""}}})},ede4:function(t,e,r){"use strict";r.r(e);var a=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"box-card"},[r("div",[r("div",[r("el-avatar",{staticClass:"avatar",attrs:{icon:"el-icon-user-solid"}})],1),r("el-form",{ref:"ruleForm",attrs:{model:t.ruleForm,rules:t.rules}},[r("el-form-item",{staticStyle:{"margin-bottom":"22px"},attrs:{prop:"user"}},[r("el-input",{attrs:{placeholder:"请输入用户名",clearable:"","prefix-icon":"el-icon-user"},model:{value:t.ruleForm.user,callback:function(e){t.$set(t.ruleForm,"user",e)},expression:"ruleForm.user"}})],1),r("el-form-item",{staticStyle:{"margin-bottom":"18px"},attrs:{prop:"pwd"}},[r("el-input",{attrs:{placeholder:"请输入密码","show-password":"","prefix-icon":"el-icon-lock"},model:{value:t.ruleForm.pwd,callback:function(e){t.$set(t.ruleForm,"pwd",e)},expression:"ruleForm.pwd"}})],1),r("el-form-item",{staticStyle:{"margin-bottom":"10px"}},[r("el-checkbox",{staticClass:"remember",attrs:{type:"text"},model:{value:t.remember,callback:function(e){t.remember=e},expression:"remember"}},[t._v("记住密码")]),r("div",{staticStyle:{display:"inline-block",float:"right"}},[r("el-button",{attrs:{type:"text"}},[r("a",{attrs:{href:"/register",target:"_blank"}},[t._v("注册账号")])]),r("span",{staticStyle:{color:"#409EFF"}},[t._v(" | ")]),r("el-button",{attrs:{type:"text"}},[t._v("找回密码")])],1)],1),r("el-form-item",[r("el-button",{staticStyle:{width:"300px"},attrs:{type:"primary"},on:{click:function(e){return t.btnClick("ruleForm")}}},[t._v("登录")])],1)],1)],1)])},o=[],n=(r("b0c0"),r("1bab")),s={name:"Login",data:function(){var t=function(t,e,r){""===e?r(new Error("请输入用户名")):r()},e=function(t,e,r){""===e?r(new Error("请输入密码")):r()};return{remember:!1,ruleForm:{user:"15213123123",pwd:"123"},rules:{user:[{validator:t,trigger:"blur"}],pwd:[{validator:e,trigger:"blur"}]}}},methods:{btnClick:function(t){var e=this;this.$refs[t].validate((function(t){t&&Object(n["a"])({url:"/sso/login",params:{username:e.ruleForm.user,password:e.ruleForm.pwd},method:"post"}).then((function(t){200===t.data.code&&200===t.status?(localStorage.navIndex="1",localStorage.idIndex=0,localStorage.userId=t.data.data.id,localStorage.name=t.data.data.name,localStorage.token=t.data.data.token,e.$store.dispatch("updateTeams").then((function(t){e.$store.state.teams.length?e.$router.push("/home/team/"+e.$store.state.teams[0].id+"/project"):e.$router.push("/team")})).catch((function(t){}))):404===t.data.code&&e.$message.error("用户名或密码错误！")}))}))},register:function(){this.$router.push("/register")}},created:function(){var t=this;this.$store.dispatch("updateTeams").then((function(e){localStorage.navIndex="1",t.$store.state.teams.length&&t.$router.push("/home/team/"+t.$store.state.teams[localStorage.idIndex].id+"/project")}))}},l=s,i=(r("1f73"),r("2877")),c=Object(i["a"])(l,a,o,!1,null,"50513644",null);e["default"]=c.exports}}]);
//# sourceMappingURL=chunk-0dc12e68.897d026e.js.map