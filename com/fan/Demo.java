package fan;

import fan.demo.Aa;
import fan.model.FanTree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @description: 测试类
 * @author: fanfanlordship
 * @create: 2020-08-11 23:32
 */
public class Demo {

    public static void main(String[] args) {
        FanTree<Integer, Aa> tree = new FanTree<>();

        tree.add(1, null, new Aa(1));
        tree.add(2, 1, new Aa(2));
        tree.add(3, 1, new Aa(3));
        tree.add(4, 2, new Aa(4));
        tree.add(5, 2, new Aa(5));
        tree.add(6, null, new Aa(6));
        tree.add(7, 5, new Aa(7));

        System.out.println("叶子：" + tree.getLeaf());
        System.out.println("节点数量：" + tree.count());
        System.out.println("根节点：" + tree.getRoot());
        System.out.println("最长链路：" + tree.maxBranch());
    }
}
