package la.niub.abcapi.invest.indicatordatamigration.config.enums;

public enum StatusEnum {

    //正常
    ENABLE("1"),
    //禁用
    DISABLE("2"),;

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
