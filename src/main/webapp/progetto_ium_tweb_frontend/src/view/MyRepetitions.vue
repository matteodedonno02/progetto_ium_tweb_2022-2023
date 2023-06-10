<template>
  <CustomToast ref="customToast" />

  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">
        Le mie ripetizioni
      </li>
    </ol>
  </nav>

  <div class="d-flex pb-3">
    <input v-model="dateFilter" type="date" class="form-control shadow-none me-4" />
    <input v-model="professorTextField" type="text" class="form-control shadow-none me-4" placeholder="Insegnante" />
    <input v-model="courseTextField" type="text" class="form-control shadow-none" placeholder="Corso" />
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
              Non hai ripetizioni da effettuare
            </div>
            <div v-else>
              <div v-for="repetition in pendingRepetitions" v-bind:key="repetition.idRepetition">
                <div v-if="repetition.state === 0">
                  <MyRepetitionCard v-bind:repetition="repetition"
                    @confirm-repetition="moveRepetitionToConfirmedRepetitions"
                    @delete-repetition="moveRepetitionToDeletedRepetitions" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="accordion-item">
      <h2 class="accordion-header">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo"
          aria-expanded="false" aria-controls="collapseTwo">
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
              Non hai ripetizioni confermate
            </div>
            <div v-else>
              <div v-for="repetition in confirmedRepetitions" v-bind:key="repetition.idRepetition">
                <div v-if="repetition.state === 1">
                  <MyRepetitionCard v-bind:repetition="repetition" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="accordion-item">
      <h2 class="accordion-header">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree"
          aria-expanded="false" aria-controls="collapseThree">
          Ripetizioni cancellate
        </button>
      </h2>
      <div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
        <div class="accordion-body">
          <div v-if="deletedRepetitions === null">
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
          </div>
          <div v-else>
            <div v-if="deletedRepetitions.length === 0" class="opaque-text">
              Non hai ripetizioni cancellate
            </div>
            <div v-else>
              <div v-for="repetition in deletedRepetitions" v-bind:key="repetition.idRepetition">
                <div v-if="repetition.state === 2">
                  <MyRepetitionCard v-bind:repetition="repetition" />
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
import $ from "jquery";
import MyRepetitionCard from "@/components/MyRepetitionCard.vue";
import MyRepetitionCardLoading from "@/components/MyRepetitionCardLoading.vue";
import CustomToast from "@/components/CustomToast.vue";
import "vue-cal/dist/vuecal.css";
import {
  formatDate,
  fromDateToString,
  formatTime,
  sumHours,
} from "../util/DateFormatter";

export default {
  name: "MyRepetition",
  props: ["loggedUserEmail"],
  data() {
    return {
      loggedUserRepetitions: null,
      pendingRepetitions: null,
      confirmedRepetitions: null,
      deletedRepetitions: null,
      todayRepetitions: null,
      events: [],
      professorTextField: "",
      courseTextField: "",
      dateFilter: ""
    };
  },
  components: {
    MyRepetitionCard,
    MyRepetitionCardLoading,
    CustomToast,
  },
  methods: {
    formatDate,
    fromDateToString,
    sumHours,
    getUserRepetitions(email) {
      let self = this;
      $.ajax("RepetitionServlet", {
        method: "GET",
        data: {
          operation: "selectByEmail",
          email: email,
        },
        success: (data) => {
          self.loggedUserRepetitions = data;
          self.loadRepetitionsByState(self.loggedUserRepetitions);

          //TODO: Differenziare per stato di prenotazione
          data.forEach((repetition) => {
            self.events.push({
              start: repetition.date + " " + formatTime(repetition.time),
              end:
                repetition.date + " " + sumHours(formatTime(repetition.time)),
              title: repetition.teaching.course.title,
              content:
                repetition.teaching.professor.name +
                " " +
                repetition.teaching.professor.surname,
              class: "materia",
              course: repetition.teaching.course,
              professor: repetition.teaching.professor,
            });
          });
        },
      });
    },
    loadRepetitionsByState(repetitions) {
      this.pendingRepetitions = [];
      this.confirmedRepetitions = [];
      this.deletedRepetitions = [];
      repetitions.forEach((repetition) => {
        switch (repetition.state) {
          case 0:
            this.pendingRepetitions.push(repetition);
            break;
          case 1:
            this.confirmedRepetitions.push(repetition);
            break;
          case 2:
            this.deletedRepetitions.push(repetition);
            break;
        }
      });
    },
    moveRepetitionToDeletedRepetitions(repetition) {
      for (var i = 0; i < this.pendingRepetitions.length; i++)
        if (this.pendingRepetitions[i] == repetition)
          this.pendingRepetitions.splice(i, 1);

      repetition.state = 2;
      this.deletedRepetitions.push(repetition);
    },
    moveRepetitionToConfirmedRepetitions(repetition) {
      for (var i = 0; i < this.pendingRepetitions.length; i++)
        if (this.pendingRepetitions[i] == repetition)
          this.pendingRepetitions.splice(i, 1);

      repetition.state = 1;
      this.confirmedRepetitions.push(repetition);
    },
    showRepetitionsByFilter() {
      if (this.dateFilter === "" && this.professorTextField === "" && this.courseTextField === "") {
        this.loadRepetitionsByState(this.loggedUserRepetitions)
      }

      let filterRepetitions = this.loggedUserRepetitions
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
    }
  },
  mounted() {
    this.getUserRepetitions(this.loggedUserEmail);
  },
};
</script>
