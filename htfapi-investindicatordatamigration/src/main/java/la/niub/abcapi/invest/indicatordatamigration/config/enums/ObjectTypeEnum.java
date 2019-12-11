package la.niub.abcapi.invest.indicatordatamigration.config.enums;

public enum ObjectTypeEnum {

    REPORT("研报"),
    NOTICE("公告"),
    NEWS("资讯"),
    TABLE("表格"),
    CHART("图表"),
    INNER_REPORT("内部研报"),
    OUTER_REPORT("外部研报");

    private String chineseName;

    ObjectTypeEnum(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
