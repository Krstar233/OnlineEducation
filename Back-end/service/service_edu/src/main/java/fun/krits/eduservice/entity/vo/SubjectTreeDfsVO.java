package fun.krits.eduservice.entity.vo;

import fun.krits.eduservice.entity.EduSubject;

import java.util.*;

public class SubjectTreeDfsVO {
    private Map<String, List<EduSubject>> tree;
    private Map<String, Boolean> visit;
    private Map<String, EduSubject> subjectTable;
    private List<QuickMap> jsonMap;

    public SubjectTreeDfsVO(Map<String, List<EduSubject>> tree, Map<String, EduSubject> subjectTable) {
        this.tree = tree;
        this.visit = new HashMap<>();
        this.subjectTable = subjectTable;
        this.jsonMap = new ArrayList<>();
    }

    public List<EduSubject> getSubNodesById(String nodeId){
        return tree.get(nodeId);
    }
    public Boolean checkVisit(String nodeId){
        return visit.get(nodeId) != null && visit.get(nodeId);
    }
    public void setVisit(String nodeId, Boolean bool){
        visit.putIfAbsent(nodeId, bool);
    }
    public EduSubject getSubject(String nodeId){
        return subjectTable.get(nodeId);
    }
    public List<QuickMap> getJsonMap(){
        return jsonMap;
    }
}
