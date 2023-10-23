let { createApp } = Vue;

createApp({
    data() {
        return {
            inputName: "",
            inputLastName: "",
            inputPassword: "",
            inputEmail: "",
            loginEmail: "",
            loginPassword: "",
        };
    },
    created() {

    },
    methods: {
        register() {
            console.log(this.inputEmail, this.inputName)
            axios.post("/api/clients",
                `name=${this.inputName}&lastName=${this.inputLastName}&email=${this.inputEmail}&password=${this.inputPassword}`
            )
                .then((response) => {
                    // this.clearData();
                    console.log(response)
                    axios.post("/app/login", `email=${this.inputEmail}&password=${this.inputPassword}`)
                        .then((response) => {
                            console.log(response)
                            location.href = "http://localhost:8080/index.html"
                        }).catch((err) => console.log(err))


                })
                .catch((err) => console.log(err));
        },
        clearData() {
            this.inputName = "",
                this.inputLastName = "",
                this.inputPassword = "";
            this.inputEmail = "";
        },
        login() {
            axios.post("/app/login", `email=${this.loginEmail}&password=${this.loginPassword}`)
                .then((response) => {
                    console.log(response)
                    location.href = "http://localhost:8080/Web/Pages/accounts.html"
                }).catch((err) => console.log(err))
        },
        signOut() {
            axios.post("/app/logout")
                .then((response) => {
                    console.log(response)
                    location.href = "http://localhost:8080/web/index.html"
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');