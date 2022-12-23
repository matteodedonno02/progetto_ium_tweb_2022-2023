<template>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Search</li>
    </ol>
  </nav>

  <div v-if="availableRepetitions !== null">
    <div class="navigation">
      <div class="left-arrow d-none pt-1" v-on:click="previousPage">
        <ArrowLeftThickIcon />
      </div>
      <h3>{{ formatDate(Object.keys(availableRepetitions)[tablePage]) }}</h3>
      <div class="right-arrow pt-1" v-on:click="nextPage">
        <ArrowRightThickIcon />
      </div>
    </div>

    <div v-for="(dateRepetition, date) in availableRepetitions" v-bind:key="date">
      <div v-if="date === Object.keys(availableRepetitions)[tablePage]">
        <RepetitionTable v-bind:repetitions="dateRepetition" class="repetition-table" />
      </div>
      <div v-else>
        <RepetitionTable v-bind:repetitions="dateRepetition" class="repetition-table d-none" />
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import RepetitionTable from '../components/RepetitionTable.vue'
import ArrowLeftThickIcon from 'vue-material-design-icons/ArrowLeftThick.vue'
import ArrowRightThickIcon from 'vue-material-design-icons/ArrowRightThick.vue'


export default {
  name: "SearchView",
  data() {
    return {
      availableRepetitions: null,
      tablePage: 0
    }
  },
  components: {
    RepetitionTable,
    ArrowLeftThickIcon,
    ArrowRightThickIcon
  },
  methods: {
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
      $(".d-none")[0].classList.remove("d-none");
      $(".repetition-table")[this.tablePage].classList.add("d-none")
    },
    formatDate(date) {
      const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
      ];

      return date.split("-")[2] + " " + monthNames[parseInt(date.split("-")[1]) - 1] + " " + date.split("-")[0]
    }
  },
  mounted() {
    this.getAvailableRepetitions()
  }
}
</script>