<template>
    <div class="table-responsive">
        <table class="table">
            <thead>
                <tr>
                    <th>Teacher</th>
                    <th>Course</th>
                    <th>Time</th>
                    <th>Prenota</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(repetition, index) in repetitions" v-bind:key="index">
                    <td>{{ repetition.teaching.professor.name }} {{ repetition.teaching.professor.surname }}</td>
                    <td>{{ repetition.teaching.course.title }}</td>
                    <td>{{ formatTime(repetition.time) }}</td>
                    <td>
                        <BookPlusIcon class="pointer" data-bs-toggle="modal"
                            v-bind:data-bs-target="'#newRepetitionModel' + index + repetition.date" />
                    </td>
                </tr>
            </tbody>
        </table>

        <div v-for="(repetition, index) in repetitions" v-bind:key="index">
            <NewRepetitionModel title="Desideri prenotarti per la seguente ripetizione?" v-bind:loggedUser="loggedUser"
                v-bind:modalId="'newRepetitionModel' + index + repetition.date" v-bind:repetition="repetition" />
        </div>
    </div>
</template>

<script>
import BookPlusIcon from 'vue-material-design-icons/BookPlus.vue';
import NewRepetitionModel from './NewRepetitionModal.vue';
import { formatTime } from '../util/DateFormatter.js'

export default {
    name: "RepetitionTable",
    props: ["repetitions", "loggedUser"],
    components: {
        BookPlusIcon,
        NewRepetitionModel
    },
    methods: {
        formatTime
    }
}
</script>