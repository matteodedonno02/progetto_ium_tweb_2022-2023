<template>
    <CustomToast ref="customToast" />

    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item active" aria-current="page">Tutte le ripetizioni</li>
        </ol>
    </nav>

    <div class="d-flex pb-3">
        <input v-model="dateFilter" type="date" class="form-control shadow-none me-4" />
        <input v-model="professorTextField" type="text" class="form-control shadow-none me-4" placeholder="Insegnante" />
        <input v-model="courseTextField" type="text" class="form-control shadow-none me-4" placeholder="Corso" />
        <input v-model="userTextField" type="text" class="form-control shadow-none" placeholder="Utente" />
    </div>

    <div class="accordion" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne"
                    aria-expanded="true" aria-controls="collapseOne">
                    Ripetizioni da effettuare
                </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <div v-if="pendingRepetitions === null">
                        <MyRepetitionCardLoading />
                        <MyRepetitionCardLoading />
                        <MyRepetitionCardLoading />
                    </div>
                    <div v-else>
                        <div v-if="pendingRepetitions.length === 0" class="opaque-text">
                            Non ci sono ripetizioni da effettuare
                        </div>
                        <div v-else>
                            <div v-for="repetition in pendingRepetitions" v-bind:key="repetition.idRepetition">
                                <div v-if="repetition.state === 0">
                                    <RepetitionCard v-bind:repetition="repetition" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    Ripetizioni confermate
                </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <div v-if="confirmedRepetitions === null">
                        <MyRepetitionCardLoading />
                        <MyRepetitionCardLoading />
                        <MyRepetitionCardLoading />
                    </div>
                    <div v-else>
                        <div v-if="confirmedRepetitions.length === 0" class="opaque-text">
                            Non ci sono ripetizioni confermate
                        </div>
                        <div v-else>
                            <div v-for="repetition in confirmedRepetitions" v-bind:key="repetition.idRepetition">
                                <div v-if="repetition.state === 1">
                                    <RepetitionCard v-bind:repetition="repetition" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                    Ripetizioni cancellate
                </button>
            </h2>
            <div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <div v-if="cancelledRepetitions === null">
                        <MyRepetitionCardLoading />
                        <MyRepetitionCardLoading />
                        <MyRepetitionCardLoading />
                    </div>
                    <div v-else>
                        <div v-if="cancelledRepetitions.length === 0" class="opaque-text">
                            Non ci sono ripetizioni cancellate
                        </div>
                        <div v-else>
                            <div v-for="repetition in cancelledRepetitions" v-bind:key="repetition.idRepetition">
                                <div v-if="repetition.state === 2">
                                    <RepetitionCard v-bind:repetition="repetition" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import RepetitionCard from '@/components/RepetitionCard.vue'
import MyRepetitionCardLoading from '@/components/MyRepetitionCardLoading.vue'
import CustomToast from '@/components/CustomToast.vue'

export default {
    name: "AdminRepetitions",
    data() {
        return {
            repetitions: null,
            pendingRepetitions: null,
            confirmedRepetitions: null,
            cancelledRepetitions: null,
            dateFilter: "",
            professorTextField: "",
            courseTextField: "",
            userTextField: ""
        }
    },
    components: {
        RepetitionCard,
        MyRepetitionCardLoading,
        CustomToast
    },
    methods: {
        getUserRepetitions() {
            let self = this
            $.ajax("RepetitionServlet", {
                method: "GET",
                data: {
                    operation: "select",
                },
                success: (data) => {
                    self.repetitions = data
                    self.loadRepetitionsByState(data)
                }
            })
        },
        loadRepetitionsByState(repetitions) {
            this.pendingRepetitions = []
            this.confirmedRepetitions = []
            this.cancelledRepetitions = []
            repetitions.forEach((repetition) => {
                switch (repetition.state) {
                    case 0:
                        this.pendingRepetitions.push(repetition)
                        break
                    case 1:
                        this.confirmedRepetitions.push(repetition)
                        break
                    case 2:
                        this.cancelledRepetitions.push(repetition)
                        break
                }
            })
        },
        showRepetitionsByFilter() {
            if (this.dateFilter === "" && this.professorTextField === "" && this.courseTextField === "" && this.userTextField === "") {
                this.loadRepetitionsByState(this.repetitions)
            }

            let filterRepetitions = this.repetitions
            if (this.dateFilter !== "") {
                filterRepetitions = filterRepetitions.filter((repetition) => {
                    return repetition.date === this.dateFilter
                })
            }

            if (this.professorTextField !== "") {
                filterRepetitions = filterRepetitions.filter((repetition) => {
                    const nameAndSurname = repetition.teaching.professor.name + repetition.teaching.professor.surname
                    const surnameAndName = repetition.teaching.professor.surname + repetition.teaching.professor.name
                    return nameAndSurname.toLowerCase().includes(this.professorTextField.toLowerCase().replace(/\s/g, ""))
                        || surnameAndName.toLowerCase().includes(this.professorTextField.toLowerCase().replace(/\s/g, ""))
                })
            }

            if (this.courseTextField !== "") {
                filterRepetitions = filterRepetitions.filter((repetition) => {
                    const courseName = repetition.teaching.course.title.replace(/\s/g, "")
                    return courseName.toLowerCase().includes(this.courseTextField.replace(/\s/g, "").toLowerCase())
                })
            }

            if (this.userTextField !== "") {
                filterRepetitions = filterRepetitions.filter((repetition) => {
                    const nameAndSurname = repetition.user.name + repetition.user.surname
                    const surnameAndName = repetition.user.surname + repetition.user.name
                    return nameAndSurname.replace(/\s/g, "").toLowerCase().includes(this.userTextField.replace(/\s/g, "").toLowerCase())
                        || surnameAndName.replace(/\s/g, "").toLowerCase().includes(this.userTextField.replace(/\s/g, "").toLowerCase())
                })
            }

            this.loadRepetitionsByState(filterRepetitions)
        }
    },
    watch: {
        dateFilter: function () {
            this.showRepetitionsByFilter()
        },
        courseTextField: function () {
            this.showRepetitionsByFilter()
        },
        professorTextField: function () {
            this.showRepetitionsByFilter()
        },
        userTextField: function () {
            this.showRepetitionsByFilter()
        }
    },
    mounted() {
        this.getUserRepetitions()
    }
}
</script>