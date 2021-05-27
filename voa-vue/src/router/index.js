import Vue from 'vue'
import VueRouter from 'vue-router'

const Login = () => import('../views/login/Login')
const Menu = () => import('../components/common/Menu/Menu')
const Project = () => import('../views/project/Project')
const Profile = () => import('../views/profile/Profile')
const Calendar = () => import('../views/calendar/Calendar')
const Dynamic = () => import('../views/dynamic/Dynamic')
const Knowledge = () => import('../views/knowledge/Knowledge')
const Progress = () => import('../views/progress/Progress')
const Report = () => import('../views/report/Report')
const Team = () => import('../views/team/Team')
const Detail = () => import('../views/project/Detail')
const Register = () => import('../views/login/Register')
const JoinTeam = () => import('../views/login/JoinTeam')
const Info = ()=> import('../components/common/Menu/Info')

Vue.use(VueRouter)

const routes = [
    {
        path: '',
        redirect: '/login'
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/register',
        component: Register
    },
    {
        path: '/team',
        component: JoinTeam
    },
    {
        path: '/home',
        component: Menu,
        children: [
            {
                path: 'team/:teamId/project',
                component: Project
            },
            {
                path: 'team/:teamId/myproject',
                component: Profile
            },
            {
                path: 'team/:teamId/Calendar',
                component: Calendar
            },
            {
                path: 'team/:teamId/Dynamic',
                component: Dynamic
            },
            {
                path: 'team/:teamId/Knowledge',
                component: Knowledge
            },
            {
                path: 'team/:teamId/Progress',
                component: Progress
            },
            {
                path: 'team/:teamId/Report',
                component: Report
            },
            {
                path: 'team/:teamId/Team',
                component: Team
            },
            {
                path: 'project/:projectId/list',
                component: Detail
            },
            {
                path: 'user',
                component: Info
            }
        ]
    }
]

const router = new VueRouter({
    routes,
    mode: 'history'
})

router.beforeEach((to, from, next) => {
    // console.log('router:');
    // console.log(from);
    if (!(to.path === '/login' || to.path === '/register') && !localStorage.getItem('token')) {
        next('/login');
    }
    next();
})

export default router
