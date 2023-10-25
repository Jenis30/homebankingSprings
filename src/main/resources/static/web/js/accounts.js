const { createApp } = Vue;

  createApp({
    data() {
      return {
        accounts:[],
        loans: [],
        client:{}
        
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
        }).catch((err) => console.log(err))
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