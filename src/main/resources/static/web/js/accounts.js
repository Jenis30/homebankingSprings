const { createApp } = Vue;

  createApp({
    data() {
      return {
        accounts:[],
        loans: []
        
      }
    },
    created(){
    this.loadData()
    },
    methods:{
    loadData(){
        axios.get("/api/clients/current")
        .then((response) => {
            this.accounts = response.data.accounts
            this.loans = response.data.loans
        })
    },
    signOut() {
      axios.post("/app/logout")
          .then((response) => {
              console.log(response)
              location.href = "http://localhost:8080/web/index.html"
          }).catch((err) => console.log(err))
  }
    }

  }).mount('#app')