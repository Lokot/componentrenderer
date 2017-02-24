/*
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

/*
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

package de.datenhahn.vaadin.componentrenderer.grid;

import com.vaadin.data.ValueProvider;

/**
 * A property value generator taylored to the needs of the typed {@link ComponentGrid}.
 *
 * @author Jonas Hahn (jonas.hahn@datenhahn.de)
 */
public class ComponentValueProvider<T, Component> implements ValueProvider {

    private final ComponentGenerator<T> componentGenerator;

    /**
     * Create a new {@link ComponentValueProvider}.
     *
     * @param generator  the generator used to create the components
     */
    public ComponentValueProvider(ComponentGenerator<T> generator) {
        this.componentGenerator = generator;
    }

    @Override
    public Object apply(Object o) {
        return componentGenerator.getComponent((T) o);
    }
}
