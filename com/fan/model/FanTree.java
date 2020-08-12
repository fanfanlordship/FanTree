package fan.model;

import com.sun.istack.internal.NotNull;
import fan.exception.NodeUniqueConstraintException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * T：上级标识
 *
 * @description: 主体树
 * @author: fanfanlordship
 * @create: 2020-08-11 23:23
 */
public class FanTree<T, E> {
    /**
     * 所有枝干（叶子）
     */
    private Map<T, FanNode> nodeMap = new HashMap<>();

    /**
     * 添加枝干（叶子）
     * <br/>
     * 存在bug:
     * <p>1、可能出现头尾相连情况</p>
     *
     * @param key 自身标识（必须唯一）
     * @param sup 上级标识
     * @param e   数据
     */
    public void add(@NotNull T key, T sup, E e) {
        //判断是否存在主键
        if (hasNode(key)) {
            throw new NodeUniqueConstraintException();
        }
        FanNode<T, E> eFanNode = new FanNode<>(key, sup, e);
        nodeMap.put(key, eFanNode);
    }

    /**
     * 是否存在节点
     *
     * @param key
     * @return
     */
    public boolean hasNode(@NotNull T key) {
        return nodeMap.keySet().contains(key);
    }

    /**
     * 获取节点
     *
     * @param key 节点标识
     * @return
     */
    public E getNode(T key) {
        return getNodeE(nodeMap.get(key));
    }

    /**
     * 获取所有的叶子
     * <br/>
     * 没有上级节点的为树叶（也有可能为根）
     */
    public List<E> getLeaf() {
        List<FanNode> leafNode = getLeafNode();
        if (leafNode.isEmpty()) {
            return Collections.emptyList();
        }
        return leafNode.stream().map(o -> getNodeE(o)).collect(Collectors.toList());
    }

    /**
     * 获取所有叶子
     *
     * @return
     */
    private List<FanNode> getLeafNode() {
        Collection<FanNode> values = nodeMap.values();
        //获取所有的上级节点
        Set<T> collect = values.stream().filter(v -> Objects.nonNull(v.getSup())).map(v -> (T) v.getSup()).collect(Collectors.toSet());

        List<FanNode> leaf = new ArrayList<>();
        for (Map.Entry<T, FanNode> entry : nodeMap.entrySet()) {
            //如果为上级节点，则不返回
            if (!collect.contains(entry.getKey())) {
                leaf.add(entry.getValue());
            }
        }
        return leaf;
    }

    /**
     * 获取节点的数量
     *
     * @return
     */
    public int count() {
        return nodeMap.size();
    }

    /**
     * 获取树根
     *
     * @return
     */
    public List<E> getRoot() {
        List<E> root = new ArrayList<>();
        Collection<FanNode> values = nodeMap.values();
        Set<T> ts = nodeMap.keySet();
        for (FanNode value : values) {
            Object sup = value.getSup();
            if (sup == null) {
                root.add(getNodeE(value));
            } else {
                if (!ts.contains((T) sup)) {
                    root.add(getNodeE(value));
                }
            }
        }
        return root;
    }

    /**
     * 获取最长分支
     * 可能存在相同长度的分支，全部返回
     * 此方法目前可能会因为存在循环依赖的情况造成死循环
     *
     * @return
     */
    public List<LinkedList<E>> maxBranch() {
        //获取全部的树叶
        List<FanNode> leafNode = getLeafNode();
        List<LinkedList<E>> list = new ArrayList<>();
        for (FanNode fanNode : leafNode) {
            LinkedList<E> objects = new LinkedList<>();
            objects.add(getNodeE(fanNode));
            FanNode temp = fanNode;
            //不断往上寻找
            while (temp != null && temp.getSup() != null) {
                temp = getSupNode((T) temp.getSup());
                if (temp != null) {
                    objects.addFirst(getNodeE(temp));
                }
            }
            //如果结果集为空的，则直接放入
            if (list.isEmpty()) {
                list.add(objects);
            } else {
                //结果集中的长度应完全相同，所以取第一个即可
                LinkedList<E> es = list.get(0);
                //若结果集中的节点数小于当前叶子的节点数，则说明当前叶子的深度目前最大，清空当前结果，将本次的链路放入
                if (es.size() < objects.size()) {
                    list.clear();
                    list.add(objects);
                } else if (es.size() == objects.size()) {
                    //若长度相同，则添加如结果集
                    list.add(objects);
                }
            }
        }
        return list;
    }

    /**
     * 获取上级节点
     *
     * @param sup
     * @return
     */
    private FanNode getSupNode(T sup) {
        return nodeMap.get(sup);
    }

    /**
     * 获取上级叶子节点
     *
     * @param sup
     * @return
     */
    public E getSuper(T sup) {
        return getNodeE(getSupNode(sup));
    }

    private E getNodeE(FanNode fanNode) {
        return fanNode == null || fanNode.getE() == null ? null : (E) fanNode.getE();
    }
}
