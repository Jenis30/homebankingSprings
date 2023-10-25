let { createApp } = Vue;

createApp({
    data() {
        return {
            loginEmail: "",
            loginPassword: "",
        };
    },
    created() {
    },
    methods: {
        login() {
            axios.post("/api/login", `email=${this.loginEmail}&password=${this.loginPassword}`)
                .then((response) => {
                    console.log(response)
                    location.pathname = "/WEB/pages/accounts.html"
                }).catch((err) => console.log(err))
        },
       
    },
}).mount('#app');