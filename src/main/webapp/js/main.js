const app = new Vue({
    el: "#app",
    data: {
        loggedUser: null
    },
    methods: {
        getUserFromSession: function () {
            let self = this;
            $.ajax("UserServlet", {
                method: "POST",
                data: {
                    "operation": "getFromSession"
                },
                success: function (data) {
                    console.log(data);
                    self.loggedUser = data;
                }
            })
        }
    },
    mounted: function () {
        this.getUserFromSession();
    }
});