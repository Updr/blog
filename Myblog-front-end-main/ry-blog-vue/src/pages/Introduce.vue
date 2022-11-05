<!-- 关于我界面 -->
<template>
<div>
	<ry-nav></ry-nav>
	<div class="container">
		<el-row :gutter="30">
			<el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
				<ry-introduce></ry-introduce>
			</el-col>
			<el-col :sm="24" :md="8">
				<ry-rightlist></ry-rightlist>
			</el-col>
		</el-row>
	</div>
</div>
</template>

<script>
import header from '../components/header.vue'
import rightlist from '../components/rightlist.vue'
import introduce from '../components/introduce.vue'
export default {
  name: 'Introduce',
  data () {
    return {

    }
  },
  components: { //定义组件
    'ry-nav': header,
    'ry-introduce': introduce,
    'ry-rightlist': rightlist,
  },
  methods: { //事件处理器

  },
  mounted () {

  }
}
</script>

<style>

</style>
