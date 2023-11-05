let { createApp } = Vue;

createApp({
    data() {
        return {
            loansAvailable: [],
            accountsOwn: [],
            TypeLoan: "",
            amount: "",
            accountTransfer: "",
            payments: "",
            loansAvailableCoutas: [],
            calculateQuota: "",
            advertencia: "",
        };
    },
    created() {

    },
    methods: {
        loans() {
            axios.get("/api/loans")
                .then((response) => {
                    this.loansAvailable = response.data

                }).catch((err) => console.log(err))

        },
        accounts() {
            axios.get("/api/clients/current/accounts")
                .then((response) => {
                    this.accountsOwn = response.data.map(account => account.number)
                }).catch((err) => console.log(err))
        },
        newloan() {
            axios.post("/api/loans", {
                id: this.TypeLoan.id,
                amount: this.amount,
                payments: this.payments,
                numberAccount: this.accountTransfer,

            }
            ).then((response) => {
                console.log(response.data)

            }).catch((err) => console.log(err))
        },

    },
    computed: {
        metodo() {
            console.log(this.TypeLoan)
            console.log(this.TypeLoan.id, this.amount, this.payments, this.accountTransfer)
        },
        typeloan() {
            this.loansAvailableCoutas = this.TypeLoan.payments

        },
        capturarValorCuota() {
            if (this.amount && this.payments) {
                let valorAmount = parseFloat(this.amount)
                this.calculateQuota = (valorAmount + (valorAmount * 0.20)) / this.payments;
            } else {
                this.calculateQuota = ""
            }
        },
        maxAmount() {
            if (this.amount > this.TypeLoan.maxAmount) {
                this.advertencia = `El monto maximo disponible a solicitar es ${this.TypeLoan.maxAmount}`
                console.log(this.advertencia)
            } else {
                this.advertencia = ""
            }

        }
    }
}).mount('#app');