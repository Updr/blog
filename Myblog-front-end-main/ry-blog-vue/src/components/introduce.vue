<template>
  <el-card class="box-card">
    <p style="font-size: x-large;">关于博主</p>
    <el-divider></el-divider>
    <div class="text item"><p>博主只是一个代码小白，借鉴和学习了很多优秀博客，自己也是边学习边开发出来的这个博客网站，开发此博客的目的主要也是为了学习技术知识，由于博主技术有限，如果有任何问题，请多多担待。</p></div>
    <p style="font-size: x-large;">关于博客</p>
    <el-divider></el-divider>
    <div class="text item"><p>后续博客功能完善后会进行开源，以下是博客开发所用到的技术</p></div>
    <el-table :data="tableData" border style="width: 100%" :show-header="false">
      <el-table-column prop="technology" align="center" width="200px">
      </el-table-column>
      <el-table-column prop="langunge" align="center" width="558px"></el-table-column>
    </el-table>
  </el-card>


</template>

<script>
import Aos, { AOS } from 'aos';
  export default {
    data() {
      //选项 / 数据
      return {
        tableData:[ // 博客使用技术
          {
            "technology":"前端",
            "langunge":"vue+elementUI"
          },
          {
            "technology":"后端",
            "langunge":"SpringBoot,MybatisPlus,SpringSecurity,EasyExcel,Swagger2,Redis"
          },
          {
            "technology":"数据库",
            "langunge":"使用MySQL数据库 MySQL版本为8.0.25 使用七牛云OSS存储"
          },
          {
            "technology":"部署",
            "langunge":"阿里云服务器,Nginx"
          }
        ]
      };
    },
    mounted() {
      // 你也可以在这里设置全局配置
        Aos.init({
          offset: 200,
          duration: 600,
          easing: 'ease-in-sine',
          delay: 100,
          once: false
       })
        },
  }
</script>

<style>
.text {
  font-size: 16px;
  font-family: 'Courier New', Courier, monospace;
  text-align: left;
  line-height: 30px;
}

.item {
  padding: 5px 0;
  margin: 20px;
}

.box-card {
  width: 800px;
  height: 600px;
}
</style>
