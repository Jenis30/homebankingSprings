const { createApp } = Vue;

  createApp({
    data() {
      return {
        cards:[],
        cardType:"",
        cardColor:""
        
      }
    },
    created(){
   
    },
    methods:{
    generateCard(){
        axios.post("/api/client/current/cards", `type=${this.cardType}&color=${this.cardColor}`)
        .then((response) => {
            location.href = "http://localhost:8080/WEB/pages/cards.html"
          
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