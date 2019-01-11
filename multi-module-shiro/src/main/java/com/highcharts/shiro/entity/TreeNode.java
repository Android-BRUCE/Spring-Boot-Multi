package com.highcharts.shiro.entity;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>BMap/com.highcharts.bean</p>
 *
 * @author Created by BruceZheng
 * @date 2018-02-26 09:08
 **/
public class TreeNode {

/**
 * 节点编号属性
 */
private String id;

/**
 * 节点文本属性
 */
private String text;

/**
 * 节点的子节点集合属性
 */
private LinkedHashSet<TreeNode> children;

/**
 * 节点的展开/闭合状态属性
 */
private String state;

/**
 * 节点的图标属性
 */
private String iconCls;

private boolean checked = false;

        public boolean isChecked() {
                return checked;
        }

        public void setChecked(boolean checked) {
                this.checked = checked;
        }

        public String getId() {
        return id;
        }

public void setId(String id) {
        this.id = id;
        }

public String getText() {
        return text;
        }

public void setText(String text) {
        this.text = text;
        }

public Set<TreeNode>getChildren() {
        return children;
        }

public void setChildren(LinkedHashSet<TreeNode> children) {
        this.children = children;
        }

public String getState() {
        return state;
        }

public void setState(String state) {
        this.state = state;
        }

public String getIconCls() {
        return iconCls;
        }

public void setIconCls(String iconCls){
        this.iconCls = iconCls;
        }

/**
 * 带三个参数的构造方法
 *
 * @param id
 *           节点的编号
 * @param text
 *           节点的文本
 * @param children
 *           节点下的子节点
 */
public TreeNode(String id, String text, LinkedHashSet<TreeNode> children) {
        super();
        this.id = id;
        this.text = text;
        this.children = children;
        }

/**
 * 带两个参数的构造方法
 *
 * @param id
 *           节点的编号
 * @param text
 *           节点的文本
 */
public TreeNode(String id, String text) {
        super();
        this.id = id;
        this.text = text;
        }

/**
 * 带三个参数的构造方法
 *
 * @param id
 *           节点的编号
 * @param text
 *           节点的文本
 * @param iconCls
 *           节点的图标
 */
public TreeNode(String id, String text, String iconCls) {
        super();
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
        }

/**
 * 带四个参数的构造方法
 *
 * @param id
 *           节点的编号
 * @param text
 *           节点的文本
 * @param children
 *           节点下的子节点
 * @param state
 *           节点的开闭状态
 */
public TreeNode(String id, String text, LinkedHashSet<TreeNode> children,
                String state) {
        super();
        this.id = id;
        this.text = text;
        this.children = children;
        this.state = state;
        }
        }
