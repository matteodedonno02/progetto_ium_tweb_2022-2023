import { createApp } from 'vue'
import App from './App.vue'
import './assets/style/style.css'
import 'v-calendar/dist/style.css'
import VCalendar from 'v-calendar'

const app = createApp(App).mount('#app')
app.use(VCalendar, {})
