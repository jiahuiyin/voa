import Vue from 'vue'
import Vuex from 'vuex'
import {request} from "../network/request";

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        teams: [],
        projects: [],
        member: [],
        totalTasks: []
    },
    mutations: {
        setTeams(state, teams) {
            state.teams = teams;
        },
        setProjects(state, projects) {
            state.projects = projects;
        },
        setMember(state, member) {
            state.member = member;
        },
        setTotalTasks(state, total) {
            state.totalTasks = total;
        }
    },
    actions: {
        updateTeams(context) {
            return new Promise((resolve, reject) => {
                request({
                    url: '/team'
                }).then(res => {
                    // console.log('updateTeam:');
                    // console.log(res);
                    if (res.data.code === 200 && res.status === 200) {
                        context.commit('setTeams', res.data.data)
                        resolve();
                    }
                }).catch(err => {
                    console.log('error team!!')
                })
            })
        },
        updateProjects(context, curTeam) {
            return new Promise((resolve, reject) => {
                request({
                    url: '/team/' + curTeam.id + '/project'
                }).then(res => {
                    // console.log('updateProjects:');
                    // console.log(res);
                    if (res.data.code === 200 && res.status === 200) {
                        context.commit('setProjects', res.data.data);
                        resolve();
                    }
                }).catch(err => {
                    console.log('error projects!!');
                })
            })
        },
        updateMember(context, curTeam) {
            return new Promise((resolve, reject) => {
                request({
                    url: '/team/' + curTeam.id + '/member'
                }).then(res => {
                    // console.log('updateMember:');
                    // console.log(res);
                    if (res.data.code === 200 && res.status === 200) {
                        context.commit('setMember', res.data.data.teamMember);
                        resolve();
                    }
                }).catch(err => {
                    console.log('error member!!');
                })
            })
        },
        updateTotalTasks(context, curTeam) {
            return new Promise((resolve,reject) => {
                request({
                    url: '/team/' + curTeam.id + '/myproject'
                }).then(res => {
                    // console.log('updateTotalTask:');
                    // console.log(res);
                    if (res.data.code === 200 && res.status === 200) {
                        context.commit('setTotalTasks', res.data.data);
                        resolve();
                    }
                }).catch(err => {
                    console.log('error totalTask!!');
                })
            })
        }
    },
    getters: {
        // getToken(state) {
        //     if (!state.token) {
        //         state.token = localStorage.getItem('token');
        //     }
        //     return state.token;
        // }
    },
    modules: {}
})

export default store