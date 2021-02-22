<template>
  <div>
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="filterText">
    </el-input>
    <el-tree  class="filter-tree" default-expand-all ref="tree" :data="subjectTree" :props="defaultProps" @node-click="handleNodeClick" :filter-node-method="filterNode"
    ></el-tree>
  </div>
</template>

<script>
  import subject from '@/api/edu/subject'
    export default {
        name: "list",
      watch: {
        filterText(val) {
          this.$refs.tree.filter(val);
        }
      },
      data() {
        return {
          filterText: '' ,
          subjectTree: [],
          defaultProps: {
            children: 'children',
            label: 'label'
          }
        };
      },
      created() {
        this.getList();
      },
      methods: {
        handleNodeClick(data) {
          console.log(data);
        },
        getList(){
          subject.getSubjectList()
            .then(res=>{
              console.log(res)
              this.subjectTree = res.data.subjectTree;
            });
        },
        filterNode(value, data) {
          if (!value) return true;
          return data.label.toLowerCase().indexOf(value.toLowerCase()) !== -1;
        }
      }
    }
</script>

<style scoped>

</style>
