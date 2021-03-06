import { config, apiRoot, webRoot } from '@/common/env.js'

export default {
  methods: {
    
  },

  computed: {
	 
    auth() {
      return { token: this.$store.state.token }
    },

    config() {
      return config
    },
	
    apiRoot() {
      return apiRoot
    },
	
	webRoot() {
	  return webRoot
	}
  }
}
