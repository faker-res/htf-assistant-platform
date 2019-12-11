package la.niub.abcapi.invest.indicatordatamigration.config.enums;

/**
 * @author jrxia
 * 2/6/18
 */
public enum MailSyncStatus {
    NOTSTARTED(1),
    SYNCING(2),
    SUCCESS(3),
    FAILED(4);

    private int value;
    MailSyncStatus(int value) {
        this.value = value;
    }

    public int codeValue() {
        return this.value;
    }
}
