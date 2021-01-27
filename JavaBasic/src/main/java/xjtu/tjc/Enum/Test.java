package xjtu.tjc.Enum;

public class Test {
    public static void main(String[] args) {
        //基本用法
        System.out.println(EnumTest.SPRING);
        System.out.println(EnumTest.SPRING.getDescription());
        System.out.println(EnumTest.SPRING.getValue());
        EnumTest[] values = EnumTest.values();
        for (EnumTest value : values) {
            System.out.println(value);
        }
        System.out.println(EnumTest.values());
        System.out.println(EnumTest.valueOf("SPRING"));
    }
}
