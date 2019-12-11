package la.niub.abcapi.invest.indicatordatamigration.config.enums;

import java.util.Calendar;

public enum FundPeriodEnum {

    D1(Calendar.DAY_OF_YEAR,-1),
    W1(Calendar.WEEK_OF_YEAR,-1),
    M1(Calendar.MONTH,-1),
    M3(Calendar.MONTH,-3),
    M6(Calendar.MONTH,-6),
    Y1(Calendar.YEAR,-1),
    ALL(Calendar.YEAR,-100),
    ;

    private int field;

    private int amount;

    FundPeriodEnum(int field, int amount) {
        this.field = field;
        this.amount = amount;
    }

    public int getField() {
        return field;
    }

    public int getAmount() {
        return amount;
    }
}
