let { createApp } = Vue;

createApp({
    data() {
        return {
            inputName: "",
            inputLastName: "",
            inputPassword: "",
            inputEmail: "",
        };
    },
    created() {

    },
    methods: {
        register() {
            axios.post("/api/clients",
                `firstName=${this.inputName}&lastName=${this.inputLastName}&email=${this.inputEmail}&password=${this.inputPassword}`
            )
                .then(() => {
                    axios.post("/api/login", `email=${this.inputEmail}&password=${this.inputPassword}`)
                        .then(() => {                    
                            location.pathname = "/WEB/pages/accounts.html"
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
      
        signOut() {
            axios.post("/api/logout")
                .then((response) => {
                    console.log(response)
                    location.pathname = "/WEB/index.html"
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');