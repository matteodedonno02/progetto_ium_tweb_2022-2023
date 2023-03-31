<template>
    <div class="container login-container d-flex justify-content-center align-items-center">
        <div class="w-25">
            <h3>Login</h3>
            <p>Effettua l'accesso per continuare</p>
            <div class="group position-relative">
                <label for="inputEmail" class="custom-placeholder col-sm-2 col-form-label position-absolute">Email</label>
                <div class="">
                    <input v-on:change="animate" v-on:onfocus="animations" v-model="email" type="text"
                        class="form-control shadow-none" id="inputEmail">
                </div>
            </div>
            <div class="group position-relative pt-3">
                <label for="inputPassword"
                    class="custom-placeholder col-sm-2 col-form-label position-absolute">Password</label>
                <div class="">
                    <input v-model="password" type="password" class="form-control shadow-none" id="inputPassword">
                </div>
            </div>
            <div class="pt-3 d-flex justify-content-center">
                <button v-on:click="login" type="button" class="custom-button btn btn-primary rounded-pill">
                    <mdiArrowRightCircleOutline /> Login
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import mdiArrowRightCircleOutline from "vue-material-design-icons/ArrowRightCircleOutline.vue"

export default {
    name: "LoginPage",
    components: {
        mdiArrowRightCircleOutline
    },
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
                    email: self.email,
                    password: self.password
                },
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    self.$emit("set-logged-user", data)
                }
            })
        },
    },
    watch: {
        email(newEmail) {
            if (newEmail === "") {
                $("label[for='inputEmail']").removeClass("focus-label")
            } else {
                $("label[for='inputEmail']").addClass("focus-label")
            }
        },
        password(newPassword) {
            if (newPassword === "") {
                $("label[for='inputPassword']").removeClass("focus-label")
            } else {
                $("label[for='inputPassword']").addClass("focus-label")
            }
        }
    }
}
</script>