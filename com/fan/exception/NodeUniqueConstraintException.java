package fan.exception;

/**
 * 当节点出现相同标识时出现此异常
 *
 * @description: 枝叶违反唯一约束条件异常
 * @author: fanfanlordship
 * @create: 2020-08-11 23:49
 */
public class NodeUniqueConstraintException extends RuntimeException {

    public NodeUniqueConstraintException() {
        super("枝叶违反唯一约束条件异常");
    }

}
