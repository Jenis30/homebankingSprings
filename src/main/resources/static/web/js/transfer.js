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
                    Swal.fire({
                        title: "Transfer completed successfully",
                        icon: "success",
                        confirmButtonColor: "#3085d6",
                      }).then((result) => {
                        if (result.isConfirmed) {
                            location.pathname = `/WEB/pages/transfer.html`;
                        }
                      });    
                }).catch((err) => {Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: err.response.data,
                    footer: '<a href="#">Why do I have this issue?</a>'
                  });})
        }
    },
}).mount('#app');