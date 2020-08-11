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
     * 第一层节点（基础）
     */
    private List<FanNode> first = new ArrayList<>();

    /**
     * 添加枝干（叶子）
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
        FanNode<T, E> eFanNode;
        //没有上级，则作为基础节点
        if (sup == null) {
            eFanNode = new FanNode<>(key);
            eFanNode.setE(e);
            first.add(eFanNode);
        } else {
            //有上级则添加
            eFanNode = new FanNode<>(key, sup, e);
        }
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
        FanNode fanNode = nodeMap.get(key);
        return fanNode == null ? null : (E) fanNode.getE();
    }

    /**
     * 获取所有的叶子
     */
    public List<E> getLeaf() {
        Collection<FanNode> values = nodeMap.values();
        //获取所有的上级节点
        Set<T> collect = values.stream().filter(v -> Objects.nonNull(v.getSup())).map(v -> (T) v.getSup()).collect(Collectors.toSet());

        List<E> leaf = new ArrayList<>();
        for (Map.Entry<T, FanNode> entry : nodeMap.entrySet()) {
            //如果为上级节点，则不返回
            if (!collect.contains(entry.getKey())) {
                FanNode value = entry.getValue();
                leaf.add((E) value.getE());
            }
        }
        return leaf;
    }
}
