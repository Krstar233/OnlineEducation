<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="getAllCourse"
                     :class="{current: query.subjectParentId==='' || query.subjectParentId===null}">全部</a>
                </li>
                <li v-for="item in subjectTree" :key="item.id" :class="{current:query.subjectParentId===item.id}">
                  <a :title="item.label" href="#" @click="getSubSubjects(item)">{{item.label}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="item in subSubjects" :key="item.id" :class="{current:query.subjectId===item.id}">
                  <a :title="item.label" href="#" @click="setSubSubjectQuery(item.id)">{{item.label}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current':query.sortBy === 'view_count', 'bg-orange':query.sortBy === 'view_count'}">
                <a title="关注度" href="#" @click="setSortBy('view_count')">关注度</a>
              </li>
              <li :class="{'current':query.sortBy === 'gmt_modified', 'bg-orange':query.sortBy === 'gmt_modified'}">
                <a title="最新" href="#" @click="setSortBy('gmt_modified')">最新</a>
              </li>
              <li :class="{'current':query.sortBy === 'price', 'bg-orange':query.sortBy === 'price'}">
                <a title="价格" href="#" @click="setSortBy('price')">价格&nbsp;
                  <span>↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="courseList.length === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-else>
            <ul class="of" id="bna">
              <li v-for="course in courseList" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" :alt="course.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA" v-if="course.price === 0">免费</i>
                      <i class="c-fff fsize12 f-fA" v-else>{{course.price}}</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{ course.buyCount }}人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div v-if="courseList.length !== 0">
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a href="#" title="首页" @click="firstPage">首</a>
            <a href="#" title="前一页" v-if="currentPage != 1" @click="lastPage">&lt;</a>
            <a v-for="page in totalPage" :key="page"
               href="#"
               :title="'第'+page+'页'"
               :class="{current: currentPage == page, undisable: currentPage== page}"
               @click="getPage(page)">{{page}}</a>
            <a href="#" title="后一页" v-if="currentPage != totalPage" @click="nextPage">&gt;</a>
            <a href="#" title="末页" @click="endPage">末</a>
            <div class="clear"></div>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
  import course from "../../api/course";
  export default {
    data(){
      return {
        subjectTree: [],
        subSubjects: [],
        currentPage: 1,
        totalPage: 0,
        limit: 8,
        query: {
          subjectId: null,
          subjectParentId: null,
          sortBy: "price"
        },
        courseList: []
      }
    },
    methods: {
      getCourseList(){
        course.getCourseList(this.query, this.currentPage, this.limit)
          .then(res=>{
            this.totalPage = Math.ceil(res.data.total / this.limit)
            this.courseList = res.data.courseList
          })
      },
      setSortBy(label){
        this.query.sortBy = label
        this.getCourseList()
      },
      setSubSubjectQuery(id){
        this.query.subjectId = id
        this.getCourseList()
      },
      getAllCourse(){
        this.subSubjects = []
        this.query.subjectParentId = null
        this.query.subjectId = null
        this.getCourseList()
      },
      getSubSubjects(parentSubjectNode){
        this.subSubjects = parentSubjectNode.children
        this.query.subjectParentId = parentSubjectNode.id
        this.query.subjectId = null
        this.getCourseList()
      },
      getPage(page){
        this.currentPage = page
        this.getCourseList()
      },
      firstPage(){
        this.currentPage = 1
        this.getCourseList()
      },
      lastPage(){
        if (this.currentPage > 1)
          this.currentPage--
        this.getCourseList()
      },
      nextPage(){
        if (this.currentPage < this.totalPage)
          this.currentPage++
        this.getCourseList()
      },
      endPage(){
        this.currentPage = this.totalPage
        this.getCourseList()
      }
    },
    created() {
      course.getSubjectTree()
        .then(res=>{
          this.subjectTree = res.data.subjectTree
        })
      this.getCourseList()
    }
  };
</script>
