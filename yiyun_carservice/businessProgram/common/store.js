import Vue from 'vue'
import Vuex, { Store } from 'vuex'

Vue.use(Vuex)

export default new Store({
  state: {
    username: null,
    password: null,
    token: null,
  },

  mutations: {
    logout(state) {
      state.username = null,
	  state.password = null,
      state.token = null
    },

    username(state, val) { state.username = val },
    password(state, val) { state.password = val },
    token(state, val) { state.token = val },
  },

  actions: {}
})
