import Vue from 'vue'
import Vuex, { Store } from 'vuex'

Vue.use(Vuex)

export default new Store({
  state: {
    user: null,
    guid: null,
    token: null,
  },

  mutations: {
    logout(state) {
      state.user = null
      state.token = null
    },

    user(state, val) { state.user = val },
    guid(state, val) { state.guid = val },
    token(state, val) { state.token = val },
  },

  actions: {}
})
