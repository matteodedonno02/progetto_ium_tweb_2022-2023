<template>
    <div class="w-100 card-row d-flex align-items-center mb-3">
        <div class="card my-repetition-card me-5">
            <div class="card-body">
                <h5 class="card-title">{{ repetition.teaching.course.title }}</h5>
                <div class="col">
                    <div class="row">
                        <div class="col text-start">
                            <p class="card-text">
                                Ripetizione dell'utente <span class="fw-bold">{{ repetition.user.name }}
                                    {{ repetition.user.surname }}</span> con {{ repetition.teaching.professor.name }}
                                {{ repetition.teaching.professor.surname }}
                                il giorno {{ formatDate(repetition.date) }}
                                alle {{ formatTime(repetition.time) }}
                            </p>
                        </div>
                        <div class="col text-end">
                            <div v-if="repetition.state === 0">
                                <span class="fw-bold">Prenotata</span>
                            </div>
                            <div v-else-if="repetition.state === 1">
                                <span class="fw-bold">Effettuata</span>
                            </div>
                            <div v-else-if="repetition.state === 2">
                                <span class="fw-bold">Cancellata</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// import $ from 'jquery'
import { formatDate, formatTime } from '../util/DateFormatter'

export default {
    name: "RepetitionCard",
    props: ["repetition"],
    methods: {
        formatDate,
        formatTime,
    }
}
</script>