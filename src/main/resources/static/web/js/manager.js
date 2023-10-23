const { createApp } = Vue

createApp({
  data() {
    return {
      clients:[],
   lastName:"",
   firstName:"",
   email:"",
   datos:{}
    };
},
    created(){
     this.loadData()
    },
  
  methods:{
    loadData(){
        axios.get("http://localhost:8080/clients")
        .then((response) =>{
          this.clients= response.data._embedded.clients
          this.datos = response.data
        }).catch((err) => console.log(err));
    },

    addClients(firstName,lastName,email){
      if(firstName == "" || lastName == "" || email == ""){
          alert ("Debe llenar todos los campos")
      } else{
        this.postClient(firstName,lastName,email)
      }
    },
    postClient(firstName,lastName,email){
      axios.post("http://localhost:8080/clients",{firstName:firstName,lastName:lastName,email:email})
      .then((response) =>{
        console.log(response)
        this.loadData()
        this.clearData()
      }).catch((err) => console.log(err));
    },
    clearData(){
      this.firstName = "",this.lastName = "" ,this.email = ""
    }
    
   
  }
}).mount('#app')






