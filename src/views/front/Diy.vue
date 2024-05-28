<template>
  <div class="main-content">
    <div class="content">
      <div style="margin-top: 20px; display: flex;flex-direction: row;width: 100%;justify-content: space-around;">
        <div style="font-size: 20px;display: flex;justify-content: center;align-items: center; background-color: #fff; border: 1px solid rgb(181, 247, 242);width: 100px; height: 40px;">
          <p>我的配置</p>
        </div>
        <div style="font-size: 20px;display: flex;justify-content: center;align-items: center; background-color: #fff; border: 1px solid rgb(181, 247, 242);width: 100px; height: 40px;">
          <p>推荐配置</p>
        </div>
      </div>
      <div class="content-nr">
        <div class="nr-bj" style="font-size: 20px;color:#53868B;font-weight: 1000;">
          <div>
            硬件种类
          </div>
          <div style="width:40%;">
            硬件名称
          </div>
          <div>
            数量
          </div>
          <div style="width:10%;">
            硬件价格
          </div>
        </div>
        <div class="nr-bj" v-for="item in 10">
          <div style="font-size: 20px;">
            CPU
          </div>
          <div style="width:40%;">
            <div style="background-color:	#fff;padding: 10px; width:100%;height: 30px;display: flex;align-items: center;font-size: 20px;text-align: left;">Intel 酷睿 i5 13600KF</div>
          </div>
          <div>
            <el-input-number v-model="num" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
          </div>
          <div style="width:5%;">
            <div style="font-size: 20px;">
              ￥1499
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    return {
      num: 1
    }
  },
  mounted() {
    
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    handleChange(value) {
      console.log(value);
    }
  }
}
</script>
<style scoped>
.main-content {
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-color: rgb(247, 121, 215); */
}
.content {
  margin-top: 40px;
  width: 80%;
  min-height: 600px;
  background-color: rgb(247, 181, 230);
  border-radius: 10px;
  box-shadow:black 0px 0px 10px;
}
.content-nr {
  width: 100%;
  /* background-color: rgb(146, 136, 144); */
}
.nr-bj {
  margin-top: 10px;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  height: 50px;
}
.nr-bj > div {
  margin: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>