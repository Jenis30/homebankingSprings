let { createApp } = Vue;

createApp({
    data() {
        return {
            account: {},
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
            axios
                .get(`/api/accounts/${id}`)
                .then((response) => {
                    this.account = response.data
                    this.transactions = response.data.transactions
                    console.log(response)
                })
                .catch((err) => console.log(err));
        },
        dateFormat(date) {
            return moment(date).format('lll');
        }


    },
}).mount('#app');