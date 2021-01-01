<template>
	<view>
		<u-toast ref="uToast" />

		<view class="page-section page-section-gap">
			<map style="width: 100vw; height: 100vh;" :markers="curLocation" :latitude="latitude" :longitude="longitude">
			</map>

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				longitude: 0,
				latitude: 0,
				curLocation: [{
					latitude: 0,
					longitude: 0,
					iconPath: '../../static/image/location.png',
					width: 32,
					height: 32
				}],
			}
		},

		onLoad(options) {
			this.latitude = options.lat;
			this.longitude = options.lon;
			this.curLocation[0].longitude = options.lon;
			this.curLocation[0].latitude = options.lat;

			console.log(this.curLocation);
		},

		methods: {

		}
	}
</script>

<style>

</style>
