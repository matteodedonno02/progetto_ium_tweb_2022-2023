<template>
    <div class="table-responsive">
        <table class="table">
            <thead>
                <tr>
                    <th>Teacher</th>
                    <th>Course</th>
                    <th>Time</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="repetition in repetitions" v-bind:key="repetition.idRepetition">
                    <td>{{ repetition.teaching.professor.name }} {{ repetition.teaching.professor.surname }}</td>
                    <td>{{ repetition.teaching.course.title }}</td>
                    <td>{{ repetition.time }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: "RepetitionTable",
    props: ["repetitions"]
}
</script>