package cc.onecampus;

import org.jivesoftware.smackx.pubsub.ItemDeleteEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;

/**
 * Created by yy on 15-8-25.
 */
public class ItemDeleteCoordinator implements ItemDeleteListener {
    @Override
    public void handleDeletedItems(ItemDeleteEvent items) {
        System.out.println("Item count: " + items.getItemIds().size());
        System.out.println(items);
    }

    @Override
    public void handlePurge() {
        System.out.println("All items have been deleted from node");
    }
}
