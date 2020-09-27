package ar.com.mercadoenvios.myapp.domain.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Status enumeration.
 */
public enum Status {
    HANDLING(true, SubStatus.MANUFACTURING),
    READY_TO_SHIP(false, SubStatus.READY_TO_PRINT, SubStatus.PRINTED),
    SHIPPED(true, SubStatus.SOON_DELIVER, SubStatus.WAITING_FOR_WITHDRAWAL),
    DELIVERED(true),
    NOT_DELIVERED(false, SubStatus.LOST, SubStatus.STOLEN);

    List<SubStatus> validsSubstatus = new ArrayList<>();
    boolean canBeNull;

    Status(boolean canBeNull, SubStatus... args) {
        validsSubstatus = Arrays.asList(args);
        this.canBeNull = canBeNull;
    }

    public List<SubStatus> getValidSubstatus() {
        return validsSubstatus;
    }

    public boolean canBeTrue() {
        return canBeNull;
    }
}
