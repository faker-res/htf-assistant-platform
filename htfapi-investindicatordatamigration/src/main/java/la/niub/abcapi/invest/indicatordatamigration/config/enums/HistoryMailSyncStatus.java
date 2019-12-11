package la.niub.abcapi.invest.indicatordatamigration.config.enums;

public enum HistoryMailSyncStatus {
    NOTSTARTED(1),
    SYNCING(2),
    SUCCESS(3),
    FAILED(4);

    private int value;

    HistoryMailSyncStatus(int value) {
        this.value = value;
    }

    public int codeValue() {
        return this.value;
    }
}
