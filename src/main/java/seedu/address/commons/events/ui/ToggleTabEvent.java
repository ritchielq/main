package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Toggle Tabs after command is executed.
 */
public class ToggleTabEvent extends BaseEvent {

    public final int leftTabIndex;

    public ToggleTabEvent(int leftTabIndex) {
        this.leftTabIndex = leftTabIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
