import {createStore} from 'vuex'
import persistedState from 'vuex-persistedstate'

export default createStore({
    plugins: [
        persistedState({storage: window.sessionStorage})
    ],
    state: {
        hasLogin: false,
        adminSelection: 2,  // 当前登录角色：1 表示教师，2 表示管理员
        loginData: {}
    },
    getters: {
        hasLogin: state => state.hasLogin,
        adminSelection: state => state.adminSelection,
        loginData: state => state.loginData,
    },
    mutations: {
        login(state, provider) {
            state.hasLogin = true;
            state.adminSelection = provider.adminSelection;
            state.loginData = JSON.parse(JSON.stringify(provider.form));
            console.log("store login", state.loginData);
        },
        logout(state) {
            console.log("store logout");
            state.hasLogin = false;
            state.loginData = {};
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
