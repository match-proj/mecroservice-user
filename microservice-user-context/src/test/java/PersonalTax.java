import java.math.BigDecimal;

/**
 * @Author zhangchao
 * @Date 2019/8/15 16:44
 * @Version v1.0
 */
public class PersonalTax {


    public static void main(String[] args) {
        //薪水
        BigDecimal salary = new BigDecimal(13100);

        //五险一金总共多少钱
        BigDecimal five = new BigDecimal(3000);

        //专项扣除
        BigDecimal specialDeduction = new BigDecimal(500);

        //累计计税
        BigDecimal totalTax = new BigDecimal(0);

        for (int i = 1; i <= 12; i++) {

            BigDecimal salary_ = salary.multiply(new BigDecimal(i));
            BigDecimal five_ = five.multiply(new BigDecimal(i));
            BigDecimal specialDeduction_ = specialDeduction.multiply(new BigDecimal(i));

            //计税金额
            BigDecimal taxAmount = salary_.subtract(five_).subtract(specialDeduction_);

            //计税金额对应的等级
            Level defaultLevel = Level.getDefaultLevel(taxAmount.intValue());

            // 个税
            BigDecimal tax = taxAmount.multiply(defaultLevel.getDiscount()).subtract(totalTax);


            System.out.println(String.format("%s月 - 计税金额：%s 个人所得税：%s  级别：%s", i, taxAmount,tax ,defaultLevel.getDesc()));
            totalTax = totalTax.add(tax);
        }


    }
}


enum Level {
    _1(1, "不超多36000的部分", 0, 36000, BigDecimal.valueOf(0.03), 0),
    _2(2, "超过36000元至144000元的部分", 36000, 144000, BigDecimal.valueOf(0.10), 2520),
    _3(3, "超过144000元至300000元的部分", 144000, 300000, BigDecimal.valueOf(0.20), 16920),
    _4(4, "超过300000元至420000元的部分", 300000, 420000, BigDecimal.valueOf(0.25), 31920),
    _5(5, "超过420000元至660000元的部分", 420000, 660000, BigDecimal.valueOf(0.30), 52920),
    _6(6, "超过660000元至960000元的部分", 660000, 960000, BigDecimal.valueOf(0.35), 85920),
    _7(7, "超过960000元的部分", 960000, 100000000, BigDecimal.valueOf(0.45), 181920),
    ;

    private Integer level; //级数
    private String desc; // 累计预扣预缴应纳税所得额
    private Integer min;//最小
    private Integer max;//最大
    private BigDecimal discount;//预扣率 %
    private Integer index;  // 速算扣除数


    public static Level getDefaultLevel(Integer money) {
        for (Level value : values()) {
            if (value.getMin() <= money && money <= value.getMax()) {
                return value;
            }
        }
        return null;
    }


    Level(Integer level, String desc, Integer min, Integer max, BigDecimal discount, Integer index) {
        this.level = level;
        this.desc = desc;
        this.min = min;
        this.max = max;
        this.discount = discount;
        this.index = index;
    }

    public Integer getLevel() {
        return level;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Integer getIndex() {
        return index;
    }
}
