<template>
  <CustomToast />

  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Cerca</li>
    </ol>
  </nav>

  <div v-if="availableRepetitions !== null">
    <div class="navigation">
      <div class="row w-100">
        <div class="col text-center">
          <div class="left-arrow d-none pt-1" v-on:click="previousPage">
            <ArrowLeftThickIcon />
          </div>
        </div>
        <div class="col text-center">

          <h3>{{ formatDate(Object.keys(availableRepetitions)[tablePage]) }}</h3>
        </div>
        <div class="col text-center">
          <div class="right-arrow pt-1" v-on:click="nextPage">
            <ArrowRightThickIcon />
          </div>
        </div>
      </div>
    </div>

    <div v-for="(dateRepetition, date) in availableRepetitions" v-bind:key="date">
      <div v-if="date === Object.keys(availableRepetitions)[tablePage]">
        <RepetitionTable @reload-available-repetitions="getAvailableRepetitions" v-bind:loggedUser="loggedUser"
          v-bind:repetitions="dateRepetition" class="repetition-table" />
      </div>
      <div v-else>
        <RepetitionTable @reload-available-repetitions="getAvailableRepetitions" v-bind:loggedUser="loggedUser"
          v-bind:repetitions="dateRepetition" class="repetition-table d-none" />
      </div>
    </div>
  </div>

  <CustomCalendar />
</template>

<script>
import $ from 'jquery'
import RepetitionTable from '../components/RepetitionTable.vue'
import ArrowLeftThickIcon from 'vue-material-design-icons/ArrowLeftThick.vue'
import ArrowRightThickIcon from 'vue-material-design-icons/ArrowRightThick.vue'
import { formatDate } from '../util/DateFormatter'
import CustomToast from '../components/CustomToast.vue'
import CustomCalendar from '../components/CustomCalendar.vue'


export default {
  name: "SearchView",
  data() {
    return {
      availableRepetitions: null,
      tablePage: 0,
    }
  },
  props: ["loggedUser"],
  components: {
    RepetitionTable,
    ArrowLeftThickIcon,
    ArrowRightThickIcon,
    CustomToast,
    CustomCalendar
  },
  methods: {
    formatDate,
    getAvailableRepetitions() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
        method: "GET",
        data: {
          operation: "available"
        },
        success: (data) => {
          self.availableRepetitions = data
        }
      })
    },
    nextPage() {
      this.tablePage++
      if (this.tablePage > 0) {
        $(".left-arrow").removeClass("d-none")
      }
      if (this.tablePage === Object.keys(this.availableRepetitions).length - 1) {
        $(".right-arrow").addClass("d-none")
      }
    },
    previousPage() {
      this.tablePage--
      if (this.tablePage < Object.keys(this.availableRepetitions).length - 1) {
        $(".right-arrow").removeClass("d-none")
      }
      if (this.tablePage === 0) {
        $(".left-arrow").addClass("d-none")
      }
    },
    changeTable() {
      $(".d-none")[0].classList.remove("d-none")
      $(".repetition-table")[this.tablePage].classList.add("d-none")
    }
  },
  mounted() {
    this.getAvailableRepetitions()
  }
}
</script>