<template>
    <div class="container login-container d-flex justify-content-center align-items-center">
        <CustomToast type="error" />
        <div class="w-25">
            <h3>Registrazione</h3>
            <p>Registrati per usufruire del servizio</p>
            <div class="group position-relative">
                <label for="inputName" class="custom-placeholder col-sm-2 col-form-label position-absolute">Nome</label>
                <div class="d-flex align-items-center border rounded">
                    <mdiAccount class="custom-icon p-1" />
                    <input v-on:change="animate" v-on:onfocus="animations" v-model="name" type="text"
                        class="form-control shadow-none border-0" id="inputName">
                </div>
            </div>
            <div class="group position-relative pt-4">
                <label for="inputSurname"
                    class="custom-placeholder col-sm-2 col-form-label position-absolute">Cognome</label>
                <div class="d-flex align-items-center border rounded">
                    <mdiAccount class="custom-icon p-1" />
                    <input v-on:change="animate" v-on:onfocus="animations" v-model="surname" type="text"
                        class="form-control shadow-none border-0" id="inputSurname">
                </div>
            </div>
            <div class="group position-relative pt-4">
                <label for="inputEmail" class="custom-placeholder col-sm-2 col-form-label position-absolute">Email</label>
                <div class="d-flex align-items-center border rounded">
                    <mdiEmailOutline class="custom-icon p-1" />
                    <input v-on:change="animate" v-on:onfocus="animations" v-model="email" type="email"
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
                <button v-on:click="register" type="button" class="custom-button btn btn-primary rounded-pill">
                    <mdiArrowRightCircleOutline /> Registrati
                </button>
            </div>
            <div class="pt-3 d-flex justify-content-center">
                <a class="custom-link link-opacity-100-hove pointer" v-on:click="() => {
                    this.$emit('change-page', 'login')
                }">Hai già un account? Effettua il login</a>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import mdiArrowRightCircleOutline from "vue-material-design-icons/ArrowRightCircleOutline.vue"
import mdiEmailOutline from "vue-material-design-icons/EmailOutline.vue"
import mdiLockOutline from "vue-material-design-icons/LockOutline.vue"
import mdiAccount from "vue-material-design-icons/Account.vue"
import CustomToast from "../components/CustomToast.vue"
import { changeToastMessage } from "../util/ChangeToastMessage"
import { Toast } from "bootstrap"

export default {
    name: "LoginPage",
    components: {
        mdiArrowRightCircleOutline,
        CustomToast,
        mdiEmailOutline,
        mdiLockOutline,
        mdiAccount
    },
    props: ["loggedUser"],
    data() {
        return {
            name: "",
            surname: "",
            email: "",
            password: "",
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
        register() {
            let self = this
            $.ajax(process.env.VUE_APP_BASE_URL + "UserServlet", {
                method: "POST",
                data: {
                    operation: "add",
                    name: self.name,
                    surname: self.surname,
                    email: self.email,
                    password: self.password
                },
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    if (data.error !== undefined) {
                        self.changeToastMessage("Esiste già un account con l'email specificata")
                        self.openToast()
                    }
                    else {
                        self.changeToastMessage("Account creato con successo!")
                        self.openToast()
                        self.$emit("change-page", "login")
                    }
                }
            })
        },
    },
    watch: {
        name(newName) {
            if (newName === "") {
                $("label[for='inputName']").removeClass("focus-label")
            } else {
                $("label[for='inputName']").addClass("focus-label")
            }
        },
        surname(newSurname) {
            if (newSurname === "") {
                $("label[for='inputSurname']").removeClass("focus-label")
            } else {
                $("label[for='inputSurname']").addClass("focus-label")
            }
        },
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