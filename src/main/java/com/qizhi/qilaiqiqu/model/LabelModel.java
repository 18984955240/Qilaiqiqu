package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/5/11.
 */
public class LabelModel {


    /**
     * cretateDate : 2016-07-20 04:13:08
     * id : 1
     * labelName : 自行车
     * labels : [{"cretateDate":"2016-07-27 04:20:17","id":9,"labelName":"做什么的","labels":[],"parentId":0},{"cretateDate":"2016-07-27 04:20:30","id":10,"labelName":"打酱油","labels":[],"parentId":0}]
     * parentId : 0
     */

    private String cretateDate;
    private int id;
    private String labelName;
    private int parentId;
    /**
     * cretateDate : 2016-07-27 04:20:17
     * id : 9
     * labelName : 做什么的
     * labels : []
     * parentId : 0
     */

    private List<LabelsBean> labels;

    public String getCretateDate() {
        return cretateDate;
    }

    public void setCretateDate(String cretateDate) {
        this.cretateDate = cretateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsBean> labels) {
        this.labels = labels;
    }

    public static class LabelsBean {
        private String cretateDate;
        private int id;
        private String labelName;
        private int parentId;
        private List<?> labels;

        public String getCretateDate() {
            return cretateDate;
        }

        public void setCretateDate(String cretateDate) {
            this.cretateDate = cretateDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public List<?> getLabels() {
            return labels;
        }

        public void setLabels(List<?> labels) {
            this.labels = labels;
        }
    }
}
