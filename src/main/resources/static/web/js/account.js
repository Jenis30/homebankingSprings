let { createApp } = Vue;

createApp({
    data() {
        return {
            account: [],
            transactions: [],
            
        };
    },
    created() {
        this.loadAccount();

    },
    methods: {
        loadAccount() {
            let queryParams = new URLSearchParams(window.location.search);
            let id = queryParams.get("id")
            console.log(id)
            axios.get("/api/clients/current/accounts")
                .then((response) => {
                    this.account = response.data.find(account => account.id == id)
                    console.log(this.account)
                    this.transactions = this.account.transactions.sort((a, b) => b.id - a.id);
                    console.log(this.transactions)
                })
                .catch((err) => console.log(err));
        },

        formatNumber(number) {
            return number.toLocaleString("De-DE", {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2,
            });
        },

        
    deleteAccount(id){
        axios.put("/api/clients/current/accounts",`id`)
        .then((response) =>{
          location.href = "http://localhost:8080/WEB/pages/accounts.html"
        })
      },

        signOut() {
            axios.post("/api/logout")
                .then((response) => {
                    console.log(response)
                    location.href = "http://localhost:8080/WEB/index.html"
                }).catch((err) => console.log(err))
        },

        dateFormat(date) {
            return moment(date).format('lll');
        }


    },
}).mount('#app');