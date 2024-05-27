<template>
  <div class="main-content">
    <div style="display: flex; grid-gap: 10px">

      <div style="flex: 1">
        <div class="card" style="padding: 30px; margin-bottom: 10px">
          <div style="font-weight: bold; font-size: 24px; margin-bottom: 20px">{{ blog.title }}</div>
          <div style="color: #666; margin-bottom: 20px">
            <span style="margin-right: 20px"><i class="el-icon-user"></i> 管理员</span>
            <span style="margin-right: 20px"><i class="el-icon-date"></i> {{ blog.date }}</span>
            <span>
              <el-tag v-for="item in tagsArr" :key="item" type="primary" style="margin-right:5px">{{ item }}</el-tag>
            </span>
          </div>

          <div class="w-e-text">
            <div v-html="blog.content"></div>
          </div>

        </div>

        <!--     点赞和收藏数据   -->
        <div class="card" style="text-align: center; font-size: 20px; color: #666; margin-bottom: 10px">
          <span style="margin-right: 20px; cursor: pointer;" @click="setLikes" :class="{ 'active' : blog.userLike }"><i class="el-icon-like"></i> {{ blog.likesCount }}</span>
        </div>

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
                  <div style="color: #888; font-size: 13px"><span style="margin-right: 20px">{{ item.time }}</span>
                    <span style="margin-left: 20px; cursor: pointer" @click="del(item.id)" v-if="item.userId === user.id"><i class="el-icon-delete"></i>删除</span>
                  </div>
                </div>
                <!--                这是回复-->
                <div style="display: flex;  grid-gap: 20px; margin-bottom: 20px" v-for="sub in item.children" :key="item.id">
                  <img :src="sub.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
                  <div style="flex: 1">
                    <div style="color: #666; margin-bottom: 10px">{{ sub.userName }} <span style="color: #333" v-if="sub.replyUser !== item.userName">回复  {{ sub.replyUser }}</span></div>
                    <div style="color: #444; margin-bottom: 10px">{{ sub.content }}</div>
                    <div style="color: #888; font-size: 13px"><span style="margin-right: 20px">{{ sub.time }}</span>
                      <span style="cursor: pointer"><i class="el-icon-s-comment"></i>评论</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--  评论结束  -->
      </div>
    </div>

    <Footer />
  </div>
</template>

<script>

export default {
  name: "BlogDetail",
  components: {

  },
  data() {
    return {
      blogId: this.$route.query.blogId,
      blog: {},
      tagsArr: [],
      recommendList: [],
      comment2Count: 0,
      comment2Content: '',
      comment2List: [],
      user: JSON.parse(localStorage.getItem('xm-user') || '{}')
    }
  },
  created() {
    this.load()

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
    loadComment2() {
      this.$request.get('/comment2/selectForUser', {
        params: {  fid: this.blogId, module: '博客' }
      }).then(res => {
        this.comment2List = res.data || []
      })
      this.$request.get('/comment2/selectCount',{
        params: {  fid: this.blogId, module: '博客' }
      }).then(res =>{
        this.comment2Count =res.data || 0
      })
    },
    addComment2() {
      this.$request.post('/comment2/add', { content: this.comment2Content, fid: this.blogId, module: '博客' }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.comment2Content = ''
          this.loadComment2()  // 重新加载数据
        }
      })
    },
    setLikes() {
      this.$request.post('/likes/set', {  fid: this.blogId, module: '博客' }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')

          this.load()  // 重新加载数据
        }
      })
    },
    setCollect() {
      this.$request.post('/collect/set', {  fid: this.blogId, module: '博客' }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')

          this.load()  // 重新加载数据
        }
      })
    },
    load() {
      this.$request.get('/blog/selectById/' + this.blogId).then(res => {
        this.blog = res.data || {}

        this.tagsArr = JSON.parse(this.blog.tags || '[]')
      })

    }
  }
}
</script>

<style>
/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
pre code {
  display: block;
}
p {
  line-height: 30px
}

.active {
  color: orange !important;
}
.recommend-title {
  margin-bottom: 5px;
}
.recommend-title:hover {
  color: #2a60c9;
}
.main-content{
  width: 60%;
  margin: 5px auto;
}
</style>