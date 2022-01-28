import {createRouter, createWebHistory} from 'vue-router'
import {store} from "core-js/internals/reflect-metadata";

const routes = [
    {
        path: '/',
        name: 'Layout',
        meta: { requiresAuth: true },
        component: () => import("@/layout/Layout"),
        redirect: "/home",
        children: [
            {
                path: "/home",
                name: "Home",
                component: () => import("@/views/Home")
            },
            {
                path: "/user",
                name: "User",
                component: () => import("@/views/User")
            },
            {
                path: "/course",
                name: "Course",
                component: () => import("@/views/Course")
            },
            {
                path: "/score",
                name: "Score",
                component: () => import("@/views/Score")
            },
            {
                path: "/teacher",
                name: "Teacher",
                component: () => import("@/views/Teacher")
            },
            {
                path: "/info",
                name: "Information",
                component: () => import("@/views/Information")
            },
            {
                path: "/exercises",
                name: "Exercises",
                component: () => import("@/views/Exercises")
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/Login")
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

// router.beforeEach((to, from, next) => {
//     if (to.matched.some(record => record.meta.requiresAuth)) {
//         // this route requires auth, check if logged in
//         // if not, redirect to login page.
//         console.log("hasLogin", store.getters.hasLogin)
//         if (!store.getters.hasLogin) {
//             next({
//                 path: '/login',
//                 query: { redirect: to.fullPath }
//             })
//         } else {
//             next()
//         }
//     } else {
//         next() // 确保一定要调用 next()
//     }
// })

export default router
