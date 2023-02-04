<template>
    <div class="container login-container d-flex justify-content-center align-items-center">
        <div class="w-25">
            <div class="">
                <label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-10">
                    <input v-model="email" type="text" class="form-control" id="staticEmail"
                        placeholder="Inserisci la tua email">
                </div>
            </div>
            <div class="">
                <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
                <div class="col-sm-10">
                    <input v-model="password" type="password" class="form-control" id="inputPassword"
                        placeholder="Inserisci la tua password...">
                </div>
            </div>
            <div class="pt-3">
                <button v-on:click="login" type="button" class="btn btn-primary">Entra</button>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'

export default {
    name: "LoginPage",
    props: ["loggedUser"],
    data() {
        return {
            email: "",
            password: ""
        }
    },
    methods: {
        login() {
            let self = this
            $.ajax(process.env.VUE_APP_BASE_URL + "UserServlet", {
                method: "POST",
                data: {
                    operation: "login",
                    email: "matteodedonno02@gmail.com",
                    password: "matteo"
                },
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    self.$emit("set-logged-user", data)
                }
            })
        }
    }
}
</script>