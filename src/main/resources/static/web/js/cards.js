const { createApp } = Vue;

  createApp({
    data() {
      return {
        creditCards:[],
        debitCards:[]
      
        
      }
    },
    created(){
    this.loadData()
    },
    methods:{
    loadData(){
        axios.get("/api/clients/current")
        .then((response) => {
            console.log(response.data)
            let clientData = response.data
            this.creditCards = clientData.cards.filter(card => card.type == "CREDIT")
            
            this.debitCards = clientData.cards.filter(card => card.type == "DEBIT")
          
        }).catch((err) => console.log(err))
    },
    signOut() {
      axios.post("/app/logout")
          .then((response) => {
              console.log(response)
              location.href = "http://localhost:8080/WEB/index.html"
          }).catch((err) => console.log(err))
  }
    }

  }).mount('#app')