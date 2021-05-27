import axios from "axios";

export function request(config) {
    const instance = axios.create({
        baseURL: '/agent/api'
    })
    instance.interceptors.request.use(config => {
        // console.log(config);
        let token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = token;
        }
        return config;
    })
    return instance(config);
}