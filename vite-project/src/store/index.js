import {createStore} from 'vuex'

export default createStore({
    state: {
        hasLogin: false,
        adminSelection: 2,  // 当前登录角色：1 表示教师，2 表示管理员
        loginData: {}
    },
    getters: {
        hasLogin: state => {
            let hasLogin = state.hasLogin
            if (!hasLogin) {
                hasLogin = JSON.parse(window.localStorage.getItem('hasLogin'))
                state.hasLogin = hasLogin
            }
            return hasLogin;
        },
        adminSelection: state => {
            let adminSelection = state.adminSelection
            if (!adminSelection) {
                adminSelection = JSON.parse(window.localStorage.getItem('adminSelection'))
                state.adminSelection = adminSelection
            }
            return adminSelection
        },
        loginData: state => {
            let loginData = state.loginData
            if (!loginData || !loginData.username || loginData.username === '') {
                loginData = JSON.parse(window.localStorage.getItem('loginData'))
                state.loginData = loginData
            }
            return loginData
        },
    },
    mutations: {
        login(state, provider) {
            state.hasLogin = true;
            state.adminSelection = provider.adminSelection;
            state.loginData = JSON.parse(JSON.stringify(provider.form));
            console.log("store login", state.loginData);
            window.localStorage.setItem("hasLogin", JSON.stringify(state.hasLogin));
            window.localStorage.setItem("adminSelection", JSON.stringify(state.adminSelection));
            window.localStorage.setItem("loginData", JSON.stringify(state.loginData));
        },
        logout(state) {
            console.log("store logout");
            state.hasLogin = false;
            state.loginData = {};
            window.localStorage.removeItem("hasLogin")
            window.localStorage.removeItem("adminSelection")
            window.localStorage.removeItem("loginData")
        }
    },
    actions: {
        Login({commit}, provider) {
            commit("login", provider);
        },
        Logout({commit}) {
            commit("logout");
        }
    },
    modules: {}
})
