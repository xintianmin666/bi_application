import Vue from 'vue';
import App from './App';
import $http from '@/common/yyRequest/httpRequest.js'
import store from '@/common/store.js'
import mixins from '@/common/mixins.js'

// Vue.config.productionTip = false;
Vue.config.productionTip = process.env.NODE_ENV === 'development'

App.mpType = 'app';

// 引入自定义配置文件
Vue.mixin(mixins)
Vue.config.productionTip = process.env.NODE_ENV === 'development'
Vue.prototype.$store = store;

// 此处为演示Vue.prototype使用，非uView的功能部分
// Vue.prototype.vuePrototype = '枣红';

// 引入colorUi 自定义导航栏
// import cuCustom from './colorui/components/cu-custom.vue'
// Vue.component('cu-custom',cuCustom)

// 引入全局uView
import uView from 'uview-ui';
Vue.use(uView);

// 此处为演示vuex使用，非uView的功能部分
// import store from '@/store';

// 引入uView提供的对vuex的简写法文件
let vuexStore = require('@/store/$u.mixin.js');
Vue.mixin(vuexStore);

// 引入uView对小程序分享的mixin封装
let mpShare = require('uview-ui/libs/mixin/mpShare.js');
Vue.mixin(mpShare);

// i18n部分的配置
// 引入语言包，注意路径
import Chinese from '@/common/locales/zh.js';
import English from '@/common/locales/en.js';

// VueI18n
import VueI18n from '@/common/vue-i18n.min.js';

// VueI18n
Vue.use(VueI18n);

const i18n = new VueI18n({
	// 默认语言
	locale: 'zh',
	// 引入语言文件
	messages: {
		'zh': Chinese,
		'en': English,
	}
});

// 由于微信小程序的运行机制问题，需声明如下一行，H5和APP非必填
Vue.prototype._i18n = i18n;

const app = new Vue({
	i18n,
	...App,
	store
});

// http拦截器，将此部分放在new Vue()和app.$mount()之间，才能App.vue中正常使用
import httpInterceptor from '@/common/http.interceptor.js';
Vue.use(httpInterceptor, app);

// http接口API抽离，免于写url或者一些固定的参数
import httpApi from '@/common/http.api.js';
Vue.use(httpApi, app);
Vue.prototype.$http = $http

app.$mount();
