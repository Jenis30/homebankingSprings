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
            console.log(this.creditCards)
            this.debitCards = clientData.cards.filter(card => card.type == "DEBIT")
            console.log(this.debitCards)
        }).catch((err) => console.log(err))
        },
        
        colorCards(card) {
          if (card.color === "GOLD")
              return {'background': 'linear-gradient(to right, #ffd700 0%, #e5aa00 100%)'};
          else if (card.color === "SILVER")
              return {'background': 'linear-gradient(to right, #c0c0c0 0%, #a6a6a6 100%)'};
          else if (card.color === "TITANIUM")
              return {'background': 'linear-gradient(to right, #708090 0%, #4d555f 100%)'};
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