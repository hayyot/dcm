<template>
  <div class="main-content">
    <div class="content">
      <div style="margin-top: 20px; display: flex;flex-direction: row;width: 100%;justify-content: space-around;">
        <div style="font-size: 20px;display: flex;justify-content: center;align-items: center; background-color: #fff; border: 1px solid rgb(181, 247, 242);width: 100px; height: 40px;">
          <p style="cursor:pointer;" @click="dialogVisible = true">我的配置</p>
        </div>
        <div style="font-size: 20px;display: flex;justify-content: center;align-items: center; background-color: #fff; border: 1px solid rgb(181, 247, 242);width: 100px; height: 40px;">
          <p style="cursor:pointer;">推荐配置</p>
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
        <div class="nr-bj" v-for="item in pzContent">
          <div style="font-size: 20px;">
            {{ item.hardCategory }}
          </div>
          <div style="width:40%;">
            <div style="background-color:	#fff;padding: 10px; width:100%;height: 30px;display: flex;align-items: center;font-size: 20px;text-align: left;">{{ item.hardName }}</div>
          </div>
          <div>
            <el-input-number v-model="item.num" @change="handleChange" :min="0" :max="10"></el-input-number>
          </div>
          <div style="width:5%;">
            <div style="font-size: 20px;">
              ￥{{ item.hardPrice }}
            </div>
          </div>
        </div>
      </div>
      <div style="display: flex;justify-content: flex-end;margin: 20px 0px 20px 0px;">
        <el-button style="width: 400px;height: 50px;margin-right: 14%;font-size: large;font-weight: bold;" type="primary" @click="openSuccess">生成配置</el-button>
        <div style="margin-right: 5%; font-size: 20px;font-weight: 1000;display: flex;align-items: center;">硬件总价:{{ priceNum }}</div>
      </div>
    </div>
    <el-dialog
      title="我的配置"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose">
      <div style="display:flex; flex-direction: row; flex-wrap: wrap;align-content: space-between;">
        <div style="width: 200px;height: 150px;background: #000;margin: 10px 10px 0px 0px;display: flex;flex-direction: column; justify-content: center;align-items: center;" v-for="item in 10">
          <div><img src="../../assets/imgs/carousel-1.png" width="180px" alt=""></div>
          <p style="color:#fff;font-weight: bold;">￥1499</p>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

export default {

  data() {
    return {
      dialogVisible: false,
      pzContent: [
        {
          hardCategory: 'CPU',
          hardName: 'Intel 酷睿 i5 13600KF',
          num: 0,
          hardPrice: 1499
        },
        {
          hardCategory: 'CPU',
          hardName: 'Intel 酷睿 i5 13600KF',
          num: 0,
          hardPrice: 1499
        },
        {
          hardCategory: 'CPU',
          hardName: 'Intel 酷睿 i5 13600KF',
          num: 0,
          hardPrice: 1499
        },
        {
          hardCategory: 'CPU',
          hardName: 'Intel 酷睿 i5 13600KF',
          num: 0,
          hardPrice: 1499
        },
        {
          hardCategory: 'CPU',
          hardName: 'Intel 酷睿 i5 13600KF',
          num: 0,
          hardPrice: 1499
        }
      ]
    }
  },
  mounted() {
    
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    openSuccess() {
      this.$message({
        message: '生成成功',
        type: 'success'
      });
    },
    handleChange(value) {
      console.log(value);
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    }
  },
  computed: {
    priceNum() {
      return this.pzContent.reduce((sum,item) => sum+item.num*item.hardPrice,0)
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