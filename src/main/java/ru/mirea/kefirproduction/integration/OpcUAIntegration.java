package ru.mirea.kefirproduction.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.subscriptions.OpcUaSubscriptionManager;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.stereotype.Component;
import ru.mirea.kefirproduction.service.OpcDataService;

import java.util.List;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpcUAIntegration {
    private final OpcDataService opcDataService;

    public void subscribeToOpcUa(OpcUaClient client, String nodeIdStr) throws Exception {
        NodeId nodeId = NodeId.parse(nodeIdStr);

        OpcUaSubscriptionManager manager = client.getSubscriptionManager();
        UaSubscription subscription = manager.createSubscription(1000).get();
        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE);
        UInteger clientHandle = subscription.nextClientHandle();

        MonitoringParameters parameters = new MonitoringParameters(
                clientHandle,
                1000.0,     // sampling interval
                null,       // filter, null means use default
                uint(10),   // queue size
                true        // discard oldest
        );

        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId,
                MonitoringMode.Reporting,
                parameters
        );

        UaSubscription.ItemCreationCallback onItemCreated =
                (item, id) -> item.setValueConsumer(this::onSubscriptionValue);

        List<UaMonitoredItem> items = subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                List.of(request),
                onItemCreated
        ).get();

        for (UaMonitoredItem item : items) {
            if (item.getStatusCode().isGood()) {
                System.out.printf("item created for nodeId={%s}", item.getReadValueId().getNodeId());
            } else {
                System.out.printf("failed to create item for nodeId={%s} (status={%s})", item.getReadValueId().getNodeId(), item.getStatusCode());
            }
        }
        Thread.sleep(10000);
    }

    private void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        System.out.printf("subscription value received: item={%s}, value={%s}", item.getReadValueId().getNodeId(), value.getValue());
        opcDataService.saveOpcData(item.getReadValueId().getNodeId().toString(), value.getValue().getValue());
    }
}
