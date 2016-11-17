package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/8/1.
 */
public class RidingLabelModel {

    private String cretateDate;
    private int id;
    private String labelName;
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

    public List<LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsBean> labels) {
        this.labels = labels;
    }

    public class LabelsBean {
        private String cretateDate;
        private int id;
        private String labelName;
        private Object labels;

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

        public Object getLabels() {
            return labels;
        }

        public void setLabels(Object labels) {
            this.labels = labels;
        }
    }

}
