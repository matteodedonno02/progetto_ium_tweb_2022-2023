Vue.component("repetition-card", {
    props: ["course", "teacher", "date"],
    template: '<div class="card" style="width: 18rem;">\n' +
        '  <div class="card-body">\n' +
        '    <h5 class="card-title">{{course}}</h5>\n' +
        '    <h6 class="card-subtitle mb-2 text-muted">{{teacher}}</h6>\n' +
        '    <p class="card-text">{{date}}</p>\n' +
        '  </div>\n' +
        '</div>'
})


const app = new Vue({
    el: "#app",
    data: {
        loggedUser: null,
        loggedUserRepetition: null
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
                    self.loggedUser = data;
                    self.getLoggedUserRepetition(data.email);
                }
            })
        },
        getLoggedUserRepetition: function (email) {
            let self = this;
            $.ajax("RepetitionServlet", {
                method: "GET",
                data: {
                    "operation": "selectByEmail",
                    "email": email
                },
                success: function (data) {
                    self.loggedUserRepetition = data;
                    console.log(data)
                }
            })
        }
    },
    mounted: function () {
        this.getUserFromSession();
    }
});