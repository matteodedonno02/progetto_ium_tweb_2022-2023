<template>
    <div class="container login-container d-flex justify-content-center align-items-center">
        <CustomToast type="error" />
        <div class="w-25">
            <h3>Login</h3>
            <p>Effettua l'accesso per continuare</p>
            <div class="group position-relative">
                <label for="inputEmail" class="custom-placeholder col-sm-2 col-form-label position-absolute">Email</label>
                <div class="custom-input d-flex align-items-center border rounded">
                    <mdiEmailOutline class="custom-icon p-1" />
                    <input v-on:change="animate" v-on:onfocus="animations" v-model="email" type="text"
                        class="form-control shadow-none border-0" id="inputEmail">
                </div>
            </div>
            <div class="group position-relative pt-4">
                <label for="inputPassword"
                    class="custom-placeholder col-sm-2 col-form-label position-absolute">Password</label>
                <div class="d-flex align-items-center border rounded">
                    <mdiLockOutline class="custom-icon p-1" />
                    <input v-model="password" type="password" class="form-control shadow-none border-0" id="inputPassword">
                </div>
            </div>
            <div class="pt-3 d-flex justify-content-center">
                <button v-on:click="login" type="button" class="custom-button btn btn-primary rounded-pill">
                    <mdiArrowRightCircleOutline /> Login
                </button>
            </div>
            <div class="pt-3 d-flex justify-content-center">
                <a class="custom-link link-opacity-100-hove pointer" v-on:click="() => {
                    this.$emit('change-page', 'registration')
                }">Non hai un account? Registrati</a>
            </div>
            <div class="pt-3 d-flex justify-content-center">
                <a class="custom-link link-opacity-100-hove pointer" v-on:click="() => {
                    this.$emit('change-page', 'home')
                }">Continua come ospite</a>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import mdiArrowRightCircleOutline from "vue-material-design-icons/ArrowRightCircleOutline.vue"
import mdiEmailOutline from "vue-material-design-icons/EmailOutline.vue"
import mdiLockOutline from "vue-material-design-icons/LockOutline.vue"
import CustomToast from "../components/CustomToast.vue"
import { changeToastMessage } from "../util/ChangeToastMessage"
import { Toast } from "bootstrap"

export default {
    name: "LoginPage",
    components: {
        mdiArrowRightCircleOutline,
        CustomToast,
        mdiEmailOutline,
        mdiLockOutline
    },
    props: ["loggedUser"],
    data() {
        return {
            email: "",
            password: ""
        }
    },
    methods: {
        changeToastMessage,
        openToast(toastMessage) {
            const toastLiveExample = $("#liveToast")
            const toast = new Toast(toastLiveExample)
            this.changeToastMessage(toastMessage)
            toast.show()
        },
        isEmail() {
            let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailPattern.test(this.email);
        },
        checkInputs() {
            if (this.email === "" || this.password === "") {
                this.openToast("Uno o più campi vuoti!")
                return false;
            }

            if (!this.isEmail()) {
                this.openToast("Campo email non valido!")
                return false;
            }

            return true;
        },
        login() {
            if (!this.checkInputs())
                return;

            let self = this
            $.ajax("UserServlet", {
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
                    if (data.error !== undefined) {
                        self.changeToastMessage(data.error === "Wrong password" ? "Password errata" : "Email non trovata")
                        self.openToast()
                    }
                    else {
                        self.$emit("set-logged-user", data)
                    }
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
    },
    mounted() {
        window.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                this.login()
            }
        })
    }
}
</script>