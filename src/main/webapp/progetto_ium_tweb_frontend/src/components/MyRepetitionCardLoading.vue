<template>
    <div class="card my-repetition-card mb-3 is-loading">
        <div class="card-body row">
            <div class="col text-start">
                <h5 class="card-title"></h5>
            </div>
            <div class="col text-start">
                <p class="card-text">
                </p>
            </div>
        </div>
    </div>
</template>

<script>
import { formatDate, formatTime } from '../util/DateFormatter'

export default {
    name: "MyRepetitionCardLoading",
    props: ["title", "professor", "date", "time"],
    methods: {
        formatDate,
        formatTime
    }
}
</script>