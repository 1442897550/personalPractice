package xjtu.tjc.Enum;

/**
 * 枚举类
 * 定义构造方法，定义属性
 * 枚举类中的name返回的是枚举的名称
 * 一般重写枚举类中的toString方法进行更改返回的值，如此例中的toString方法返回的为枚举名称修改的值
 * 当我们需要返回给前端枚举类的数组时，可以使用values方法获取整个枚举类的数组
 */
public enum EnumTest {
    SPRING("春天",1),
    SUMMER("夏天",2),
    AUTUMN("秋天",3),
    WINTER("冬天",4);

    private String description;
    private Integer value;

    EnumTest(String description, Integer value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
