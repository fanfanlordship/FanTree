package fan.model;

import com.sun.istack.internal.NotNull;

/**
 * @description: 枝干（树叶）
 * @author: fanfanlordship
 * @create: 2020-08-11 23:23
 */
class FanNode<T, E> {
    /**
     * 本身标识
     */
    @NotNull
    private T id;
    /**
     * 上级
     */
    private T sup;
    /**
     * 值
     */
    private E e;

    FanNode(T id) {
        this.id = id;
    }

    FanNode(T id, T sup, E e) {
        this.id = id;
        this.sup = sup;
        this.e = e;
    }

    T getId() {
        return id;
    }

    FanNode setId(T id) {
        this.id = id;
        return this;
    }

    T getSup() {
        return sup;
    }

    FanNode setSup(T sup) {
        this.sup = sup;
        return this;
    }

    E getE() {
        return e;
    }

    FanNode setE(E e) {
        this.e = e;
        return this;
    }
}
