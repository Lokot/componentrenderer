/**
 * Licensed under the Apache License,Version2.0(the"License");you may not
 * use this file except in compliance with the License.You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an"AS IS"BASIS,WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.datenhahn.vaadin.componentrenderer.client;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.shared.ui.Connect;
import com.vaadin.v7.client.connectors.AbstractGridRendererConnector;
import elemental.json.Json;
import elemental.json.JsonValue;

/**
 * The ComponentRenderer's Connector. Handles the decoding of the ComponentConnectorIds into Components.
 *
 * @author Jonas Hahn (jonas.hahn@datenhahn.de)
 * @see ComponentRenderer
 */
@Connect(de.datenhahn.vaadin.componentrenderer.ComponentRenderer.class)
public class ComponentRendererConnector extends AbstractGridRendererConnector<ComponentConnector> {

    /**
     * Retrieve the renderer and link it with its connector.
     *
     * @return the renderer
     */
    @Override
    public ComponentRenderer getRenderer() {
        return (ComponentRenderer) super.getRenderer();
    }

    /**
     * Decodes the connectorId from the JSON to the real connector.
     *
     * @param jsonConnectorId the json value to decode
     * @return the component connector to be rendered by the ComponentRenderer
     */
    @Override
    public ComponentConnector decode(JsonValue jsonConnectorId) {
        if(jsonConnectorId != Json.createNull()) {
            return (ComponentConnector) ConnectorMap.get(getConnection()).getConnector(jsonConnectorId.toString());
        } else {
            return null;
        }
    }

}
