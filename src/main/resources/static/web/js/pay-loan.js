let { createApp } = Vue;

createApp({
    data() {
        return {
            client: {},
            loans : [],
            accounts:[],
            idloans: 0,
            idAccount: 0,
            amount:0
           
        };
    },
    created() {
        
        axios.get("/api/clients/current")
        .then((response) => {
            this.client = response.data
           this.loans = this.client.loans
           console.log(this.loans)
           this.accounts = this.client.accounts 
        }).catch((err) => console.log(err))
    },
    methods: {
       
        formatNumber(number) {
            return number.toLocaleString("De-DE", {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2,
            })
        },
      
        payLoan() {
            axios.post("/api/loans/payments", `idLoan=${this.idLoan}&idAccount=${this.idAccount}&amount=${this.amount}`
               
            ).then((response) => {
                Swal.fire({
                    title: "Are you sure?",
                    text: "You won't be able to revert this!",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Yes, Confirm!"
                  }).then((result) => {
                    Swal.fire({
                        title: "Successful pay loan",
                        icon: "success",
                        confirmButtonColor: "#3085d6",
                      }).then((result) => {
                        if (result.isConfirmed) {
                            location.pathname = `/WEB/pages/pay-loan.html`;
                        }
                      });             
                    }
                  )

            })  .catch(error => {
                Swal.fire({
                  icon: 'error',
                  text: error.response.data,
                  confirmButtonColor: "#7c601893", 
                })});
            }
            },
        
    
}).mount('#app');