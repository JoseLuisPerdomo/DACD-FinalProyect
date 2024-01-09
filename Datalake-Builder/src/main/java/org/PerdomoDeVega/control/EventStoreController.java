package org.PerdomoDeVega.control;
import java.time.LocalDateTime;

public class EventStoreController {

    private final BuilderStore builderStore;
    private final EventReceiver eventReceiver;

    public EventStoreController(BuilderStore builderStore, EventReceiver eventReceiver) {
        this.builderStore = builderStore;
        this.eventReceiver = eventReceiver;
    }

    public void execute(){

    }

    public BuilderStore getBuilderStore() {
        return builderStore;
    }

    public EventReceiver getEventReceiver() {
        return eventReceiver;
    }
}
