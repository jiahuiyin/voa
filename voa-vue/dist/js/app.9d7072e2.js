(function(e){function t(t){for(var a,r,u=t[0],d=t[1],i=t[2],f=0,s=[];f<u.length;f++)r=u[f],Object.prototype.hasOwnProperty.call(c,r)&&c[r]&&s.push(c[r][0]),c[r]=0;for(a in d)Object.prototype.hasOwnProperty.call(d,a)&&(e[a]=d[a]);l&&l(t);while(s.length)s.shift()();return o.push.apply(o,i||[]),n()}function n(){for(var e,t=0;t<o.length;t++){for(var n=o[t],a=!0,r=1;r<n.length;r++){var u=n[r];0!==c[u]&&(a=!1)}a&&(o.splice(t--,1),e=d(d.s=n[0]))}return e}var a={},r={app:0},c={app:0},o=[];function u(e){return d.p+"js/"+({}[e]||e)+"."+{"chunk-042f7f76":"96c0f20c","chunk-0dc12e68":"897d026e","chunk-1baca7a2":"fa4be5b7","chunk-1e06a13a":"356d6fbe","chunk-2d0be0d9":"f173a282","chunk-2d0dd433":"af033fc2","chunk-2d22ca87":"0900979e","chunk-38ad4a29":"c8417c91","chunk-44d33b86":"27794d47","chunk-45bf1ef6":"d420b5f8","chunk-93f4abca":"7bd34496","chunk-b1ebc0d6":"64d00873","chunk-dd4b4112":"e6bcdcfb","chunk-e0bbaed6":"7a5592e8"}[e]+".js"}function d(t){if(a[t])return a[t].exports;var n=a[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,d),n.l=!0,n.exports}d.e=function(e){var t=[],n={"chunk-042f7f76":1,"chunk-0dc12e68":1,"chunk-1baca7a2":1,"chunk-1e06a13a":1,"chunk-38ad4a29":1,"chunk-44d33b86":1,"chunk-45bf1ef6":1,"chunk-93f4abca":1,"chunk-b1ebc0d6":1,"chunk-dd4b4112":1,"chunk-e0bbaed6":1};r[e]?t.push(r[e]):0!==r[e]&&n[e]&&t.push(r[e]=new Promise((function(t,n){for(var a="css/"+({}[e]||e)+"."+{"chunk-042f7f76":"260fa454","chunk-0dc12e68":"70ee1c90","chunk-1baca7a2":"1247b040","chunk-1e06a13a":"543eb7d9","chunk-2d0be0d9":"31d6cfe0","chunk-2d0dd433":"31d6cfe0","chunk-2d22ca87":"31d6cfe0","chunk-38ad4a29":"20e3643b","chunk-44d33b86":"dca45892","chunk-45bf1ef6":"699f4d23","chunk-93f4abca":"b529a25c","chunk-b1ebc0d6":"cdfeeeaa","chunk-dd4b4112":"cadd2f4d","chunk-e0bbaed6":"36000ce8"}[e]+".css",c=d.p+a,o=document.getElementsByTagName("link"),u=0;u<o.length;u++){var i=o[u],f=i.getAttribute("data-href")||i.getAttribute("href");if("stylesheet"===i.rel&&(f===a||f===c))return t()}var s=document.getElementsByTagName("style");for(u=0;u<s.length;u++){i=s[u],f=i.getAttribute("data-href");if(f===a||f===c)return t()}var l=document.createElement("link");l.rel="stylesheet",l.type="text/css",l.onload=t,l.onerror=function(t){var a=t&&t.target&&t.target.src||c,o=new Error("Loading CSS chunk "+e+" failed.\n("+a+")");o.code="CSS_CHUNK_LOAD_FAILED",o.request=a,delete r[e],l.parentNode.removeChild(l),n(o)},l.href=c;var h=document.getElementsByTagName("head")[0];h.appendChild(l)})).then((function(){r[e]=0})));var a=c[e];if(0!==a)if(a)t.push(a[2]);else{var o=new Promise((function(t,n){a=c[e]=[t,n]}));t.push(a[2]=o);var i,f=document.createElement("script");f.charset="utf-8",f.timeout=120,d.nc&&f.setAttribute("nonce",d.nc),f.src=u(e);var s=new Error;i=function(t){f.onerror=f.onload=null,clearTimeout(l);var n=c[e];if(0!==n){if(n){var a=t&&("load"===t.type?"missing":t.type),r=t&&t.target&&t.target.src;s.message="Loading chunk "+e+" failed.\n("+a+": "+r+")",s.name="ChunkLoadError",s.type=a,s.request=r,n[1](s)}c[e]=void 0}};var l=setTimeout((function(){i({type:"timeout",target:f})}),12e4);f.onerror=f.onload=i,document.head.appendChild(f)}return Promise.all(t)},d.m=e,d.c=a,d.d=function(e,t,n){d.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},d.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},d.t=function(e,t){if(1&t&&(e=d(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(d.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var a in e)d.d(n,a,function(t){return e[t]}.bind(null,a));return n},d.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return d.d(t,"a",t),t},d.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},d.p="/",d.oe=function(e){throw console.error(e),e};var i=window["webpackJsonp"]=window["webpackJsonp"]||[],f=i.push.bind(i);i.push=t,i=i.slice();for(var s=0;s<i.length;s++)t(i[s]);var l=f;o.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"034f":function(e,t,n){"use strict";n("9085")},"1bab":function(e,t,n){"use strict";n.d(t,"a",(function(){return c}));var a=n("bc3a"),r=n.n(a);function c(e){var t=r.a.create({baseURL:"/agent/api"});return t.interceptors.request.use((function(e){var t=localStorage.getItem("token");return t&&(e.headers.Authorization=t),e})),t(e)}},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var a=n("2b0e"),r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("router-view")],1)},c=[],o={name:"App",data:function(){return{}}},u=o,d=(n("034f"),n("2877")),i=Object(d["a"])(u,r,c,!1,null,null,null),f=i.exports,s=(n("d3b7"),n("3ca3"),n("ddb0"),n("8c4f")),l=function(){return n.e("chunk-0dc12e68").then(n.bind(null,"ede4"))},h=function(){return n.e("chunk-1baca7a2").then(n.bind(null,"da76"))},b=function(){return n.e("chunk-1e06a13a").then(n.bind(null,"b7bc"))},p=function(){return n.e("chunk-e0bbaed6").then(n.bind(null,"3b42"))},m=function(){return n.e("chunk-b1ebc0d6").then(n.bind(null,"5e9a"))},k=function(){return n.e("chunk-2d22ca87").then(n.bind(null,"f3bf"))},g=function(){return n.e("chunk-93f4abca").then(n.bind(null,"fa92"))},v=function(){return n.e("chunk-2d0be0d9").then(n.bind(null,"2f2a"))},y=function(){return n.e("chunk-2d0dd433").then(n.bind(null,"8148"))},j=function(){return n.e("chunk-44d33b86").then(n.bind(null,"74b5"))},w=function(){return n.e("chunk-dd4b4112").then(n.bind(null,"b512"))},T=function(){return n.e("chunk-38ad4a29").then(n.bind(null,"a9e32"))},P=function(){return n.e("chunk-042f7f76").then(n.bind(null,"06c1"))},O=function(){return n.e("chunk-45bf1ef6").then(n.bind(null,"751c"))};a["default"].use(s["a"]);var I=[{path:"",redirect:"/login"},{path:"/login",component:l},{path:"/register",component:T},{path:"/team",component:P},{path:"/home",component:h,children:[{path:"team/:teamId/project",component:b},{path:"team/:teamId/myproject",component:p},{path:"team/:teamId/Calendar",component:m},{path:"team/:teamId/Dynamic",component:k},{path:"team/:teamId/Knowledge",component:g},{path:"team/:teamId/Progress",component:v},{path:"team/:teamId/Report",component:y},{path:"team/:teamId/Team",component:j},{path:"project/:projectId/list",component:w},{path:"user",component:O}]}],S=new s["a"]({routes:I,mode:"history"});S.beforeEach((function(e,t,n){"/login"===e.path||"/register"===e.path||localStorage.getItem("token")||n("/login"),n()}));var E=S,_=n("2f62"),x=n("1bab");a["default"].use(_["a"]);var A=new _["a"].Store({state:{teams:[],projects:[],member:[],totalTasks:[]},mutations:{setTeams:function(e,t){e.teams=t},setProjects:function(e,t){e.projects=t},setMember:function(e,t){e.member=t},setTotalTasks:function(e,t){e.totalTasks=t}},actions:{updateTeams:function(e){return new Promise((function(t,n){Object(x["a"])({url:"/team"}).then((function(n){200===n.data.code&&200===n.status&&(e.commit("setTeams",n.data.data),t())})).catch((function(e){console.log("error team!!")}))}))},updateProjects:function(e,t){return new Promise((function(n,a){Object(x["a"])({url:"/team/"+t.id+"/project"}).then((function(t){200===t.data.code&&200===t.status&&(e.commit("setProjects",t.data.data),n())})).catch((function(e){console.log("error projects!!")}))}))},updateMember:function(e,t){return new Promise((function(n,a){Object(x["a"])({url:"/team/"+t.id+"/member"}).then((function(t){200===t.data.code&&200===t.status&&(e.commit("setMember",t.data.data.teamMember),n())})).catch((function(e){console.log("error member!!")}))}))},updateTotalTasks:function(e,t){return new Promise((function(n,a){Object(x["a"])({url:"/team/"+t.id+"/myproject"}).then((function(t){200===t.data.code&&200===t.status&&(e.commit("setTotalTasks",t.data.data),n())})).catch((function(e){console.log("error totalTask!!")}))}))}},getters:{},modules:{}}),C=A,M=n("bc3a"),L=n.n(M),N=n("2106"),q=n.n(N),B=n("5c96"),D=n.n(B);n("0fae");a["default"].config.productionTip=!1,a["default"].use(q.a,L.a),a["default"].use(D.a),new a["default"]({render:function(e){return e(f)},router:E,store:C}).$mount("#app")},9085:function(e,t,n){}});
//# sourceMappingURL=app.9d7072e2.js.map