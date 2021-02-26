<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
          <!-- <c:forEach var="subject" items="${subjectList }">
                            <a id="${subject.subjectId}" title="${subject.subjectName }" href="javascript:void(0)" onclick="submitForm(${subject.subjectId})">${subject.subjectName }</a>
          </c:forEach>-->
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="totalPage === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="i-teacher-list" v-else>
            <ul class="of">
              <li v-for="teacher in teacherList" :key="teacher.id">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href="'/teacher/'+teacher.id"
                       :title="teacher.name" target="_blank">
                      <img :src="teacher.avatar" alt>
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_blank" class="fsize18 c-666">{{teacher.name}}</a>
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">{{teacher.intro}}</span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{teacher.career}}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div v-if="teacherList.length !== 0">
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
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>
  import teacher from "../../api/teacher";
  export default {
    data(){
      return {
        currentPage: 1,
        totalPage: 0,
        limit: 8,
        teacherList: []
      }
    },
    methods: {
      getTeacherList(){
        teacher.pageTeacher(this.currentPage, this.limit)
          .then(res=>{
            this.teacherList = res.data.rows
            this.totalPage = Math.ceil(res.data.total / this.limit)
            console.log(this.teacherList)
          })
      },
      getPage(page){
        this.currentPage = page
        this.getTeacherList()
      },
      firstPage(){
        this.currentPage = 1
        this.getTeacherList()
      },
      lastPage(){
        if (this.currentPage > 1)
          this.currentPage--
        this.getTeacherList()
      },
      nextPage(){
        if (this.currentPage < this.totalPage)
          this.currentPage++
        this.getTeacherList()
      },
      endPage(){
        this.currentPage = this.totalPage
        this.getTeacherList()
      }
    },
    created() {
      this.getTeacherList()
    }
  };
</script>
