import {createRouter, createWebHashHistory} from 'vue-router'

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
    history: createWebHashHistory(),
    routes
})

export default router
