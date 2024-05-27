<template>
  <!--  评论开始  -->
  <div class="card">
    <h2 style="margin-bottom: 20px">评论 {{ comment2Count }}</h2>

    <div style="margin-bottom: 20px">
      <el-input type="textarea" placeholder="请输入评论内容" v-model="comment2Content"></el-input>
      <div style="text-align: right; margin-top: 5px">
        <el-button type="primary" @click="addComment2">评 论</el-button>
      </div>
    </div>

    <div>
      <div style="display: flex; grid-gap: 20px; margin-bottom: 20px" v-for="item in comment2List" :key="item.id">
        <img :src="item.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
        <div style="flex: 1">
          <!--                这是第一级评论-->
          <div style="margin-bottom: 10px">
            <div style="color: #666; margin-bottom: 10px">{{ item.userName }}</div>
            <div style="color: #444; margin-bottom: 10px">{{ item.content }}</div>
            <div style="color: #888; font-size: 13px; margin-bottom: 10px"><span style="margin-right: 20px">{{ item.time }}</span>
              <span style="cursor: pointer;" :class="{ 'comment2-active' : item.showReplyInput }" @click="handleShowReplyInput(item)"><i class="el-icon-s-comment"></i>评论</span>
              <span style="margin-left: 20px; cursor: pointer" @click="del(item.id)" v-if="item.userId === user.id"><i class="el-icon-delete"></i>删除</span>
            </div>
            <div v-if="item.showReplyInput">
              <el-input type="textarea" placeholder="请输入回复内容" v-model="item.replyContent"></el-input>
              <div style="text-align: right; margin-top: 5px">
                <el-button type="primary" @click="addReplay(item)">回 复</el-button>
              </div>
            </div>
          </div>
          <!--                这是回复-->
          <div style="display: flex;  grid-gap: 20px; margin-bottom: 20px" v-for="sub in item.children" :key="sub.id">
            <img :src="sub.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
            <div style="flex: 1">
              <div style="color: #666; margin-bottom: 10px">{{ sub.userName }} <span style="color: #333" v-if="sub.replyUser !== item.userName">回复  {{ sub.replyUser }}</span></div>
              <div style="color: #444; margin-bottom: 10px">{{ sub.content }}</div>
              <div style="color: #888; font-size: 13px; margin-bottom: 10px"><span style="margin-right: 20px">{{ sub.time }}</span>
                <span style="cursor: pointer;" :class="{ 'comment2-active' : sub.showReplyInput }" @click="handleShowReplyInput(sub)"><i class="el-icon-s-comment"></i>评论</span>
                <span style="margin-left: 20px; cursor: pointer" @click="del(sub.id)" v-if="sub.userId === user.id"><i class="el-icon-delete"></i>删除</span>
              </div>
              <div v-if="sub.showReplyInput">
                <el-input type="textarea" placeholder="请输入回复内容" v-model="sub.replyContent"></el-input>
                <div style="text-align: right; margin-top: 5px">
                  <el-button type="primary" @click="addReplay(sub)">回 复</el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--  评论结束  -->

</template>

<script>
export default {
  name: "Comment2Component",
  props: {
    fid: null,
    module: null
  },
  data() {
    return {
      comment2Count: 0,
      comment2Content: '',
      comment2List: [],
      user: JSON.parse(localStorage.getItem('xm-user') || '{}')
    }
  },
  created() {
    this.loadComment2()
  },
  methods: {
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/comment2/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.loadComment2()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleShowReplyInput(item) {
      this.$set(item, 'showReplyInput', !item.showReplyInput)
    },
    addReplay(item) {
      this.$request.post('/comment2/add', { pid: item.id, rootId: item.rootId, content: item.replyContent, fid: this.fid, module: this.module }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          item.replyContent = ''
          this.loadComment2()  // 重新加载数据
        }
      })
    },
    loadComment2() {
      this.$request.get('/comment2/selectForUser', {
        params: {  fid: this.fid, module: this.module }
      }).then(res => {
        this.comment2List = res.data || []
      })

      this.$request.get('/comment2/selectCount', {
        params: { fid: this.fid, module: this.module }
      }).then(res => {
        this.comment2Count = res.data || 0
      })
    },
    addComment2() {
      this.$request.post('/comment2/add', { content: this.comment2Content, fid: this.fid, module: this.module }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.comment2Content = ''
          this.loadComment2()  // 重新加载数据
        }
      })
    },
  }
}
</script>

<style scoped>

</style>