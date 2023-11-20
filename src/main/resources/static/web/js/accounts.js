const { createApp } = Vue;

  createApp({
    data() {
      return {
        accounts:[],
        loans: [],
        client:{},
        accountsActive:[],
        accountType: ""
        
      }
    },
    created(){
    this.loadData()
    },
    methods:{
    loadData(){
        axios.get("/api/clients/current")
        .then((response) => {
          console.log(response)
            this.client = response.data
            console.log(this.client)
            this.accounts = response.data.accounts
            this.loans = response.data.loans
            this.accountsActive = this.filterAccountsActive()
            console.log()
        }).catch((err) => console.log(err))
    },

    createAccount(){
      axios.post("/api/clients/current/accounts", `accountType=${this.accountType}`)
      .then((response) =>{
        location.href = "http://localhost:8080/WEB/pages/accounts.html"
      })
    },
    filterAccountsActive(){
      return this.accounts.filter(account => account.active)
    },
    
    deleteAccount(id){
      axios.put("/api/clients/current/accounts",`id=${id}`)
      .then((response) =>{
        location.href = "http://localhost:8080/WEB/pages/accounts.html"
      })
    },

    formatNumber(number) {
      return number.toLocaleString("De-DE", {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
      })
  },


    signOut() {
      axios.post("/api/logout")
          .then((response) => {
              console.log(response)
              location.href = "http://localhost:8080/WEB/index.html"
          }).catch((err) => console.log(err))
  }
    }

  }).mount('#app')