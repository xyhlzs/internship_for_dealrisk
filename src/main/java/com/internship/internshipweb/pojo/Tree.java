package com.internship.internshipweb.pojo;

public class Tree {
    private int treeId;
    private String treeName;

    public int getTreeId() {
        return treeId;
    }

    public Tree() {
    }

    public Tree(int treeId, String treeName) {
        this.treeId = treeId;
        this.treeName = treeName;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "treeId=" + treeId +
                ", treeName='" + treeName + '\'' +
                '}';
    }
}