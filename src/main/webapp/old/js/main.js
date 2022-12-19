const Foo = {template: '<div>foo</div>'}
const Bar = {template: '<div>bar</div>'}

// 2. Define some routes
// Each route should map to a component. The "component" can
// either be an actual component constructor created via
// `Vue.extend()`, or just a component options object.
// We'll talk about nested routes later.
const routes = [
    {path: '/foo', component: Foo},
    {path: '/bar', component: Bar}
]

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
        },
        rememberToRemoveThisFunction: function (email, password) {
            let self = this;
            $.ajax({
                url: "UserServlet",
                method: "POST",
                data: {
                    "operation": "login",
                    "email": email,
                    "password": password
                },
                success: function (data) {
                    self.loggedUser = data;
                }
            })
        }
    },
    mounted: function () {
        this.getUserFromSession();
    }
});

app.use(router);