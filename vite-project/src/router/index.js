import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Layout',
        meta: { requiresAuth: true },
        component: () => import("@/layout/Layout.vue"),
        redirect: "/home",
        children: [
            {
                path: "/home",
                name: "Home",
                component: () => import("@/views/Home.vue")
            },
            {
                path: "/user",
                name: "User",
                component: () => import("@/views/User.vue")
            },
            {
                path: "/course",
                name: "Course",
                component: () => import("@/views/Course.vue")
            },
            {
                path: "/score",
                name: "Score",
                component: () => import("@/views/Score.vue")
            },
            {
                path: "/teacher",
                name: "Teacher",
                component: () => import("@/views/Teacher.vue")
            },
            {
                path: "/info",
                name: "Information",
                component: () => import("@/views/Information.vue")
            },
            {
                path: "/exercises",
                name: "Exercises",
                component: () => import("@/views/Exercises.vue")
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/Login.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
