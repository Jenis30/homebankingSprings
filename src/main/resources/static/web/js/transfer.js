let { createApp } = Vue;

createApp({
    data() {
        return {
            accountsOwn: [],
            selectedFrom: "",
            selectedTo: "",
            transferType: "",
            accountDestination: "",
            amount: "",
            description: "",

        };
    },
    created() {
        axios.get("/api/clients/current/accounts")
            .then((response) => {
                this.accountsOwn = response.data.map(account => account.number)
            })
    },
    methods: {
        newTransaction() {
            console.log(this.amount, this.selectedFrom, this.accountDestination, this.description, this.accountsOwn)
            axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&originnumber=${this.selectedFrom}&destinationnumber=${this.accountDestination}`)
                .then((response) => {
                    console.log(response)
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');